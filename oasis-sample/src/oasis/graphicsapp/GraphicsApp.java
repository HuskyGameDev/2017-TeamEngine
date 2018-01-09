package oasis.graphicsapp;

import oasis.core.Application;
import oasis.core.BackendType;
import oasis.core.Config;
import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.file.GlslParser;
import oasis.file.ObjImporter;
import oasis.graphics.Camera;
import oasis.graphics.DirectionalLight;
import oasis.graphics.Graphics;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsState;
import oasis.graphics.GraphicsState.FrontFace;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Mathf;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Transform;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class GraphicsApp implements Application {

    private static final Logger log = new Logger(GraphicsApp.class); 
    
    private int ticks = 0; 
    
    private Mesh mesh; 
    private Mesh mesh2; 
    private Mesh mesh3; 
    private Material material; 
    private Material grassMat; 
    private Shader shader; 
    private GraphicsState state; 
    private Camera camera; 
    
    private float yaw, pitch; 
    
    @Override
    public void init() {
        log.debug("init"); 
        
        Oasis.getDisplay().setResizable(true); 
        
        state = new GraphicsState(); 
//        state.setFrontFace(FrontFace.BOTH);
        
        mesh = ObjImporter.load("dragon.obj");
        mesh2 = ObjImporter.load("bunny.obj"); 
        mesh3 = ObjImporter.load("terrain4.obj"); 
        
        String vs = GlslParser.getVertexSource("test.glsl"); 
        String fs = GlslParser.getFragmentSource("test.glsl"); 
        shader = Oasis.getGraphicsDevice().createShader(vs, fs); 
        
        material = new Material(); 
        material.setShader(shader); 
        material.setDiffuse(new Vector3(0.3f, 0.3f, 0.3f)); 
        material.setSpecular(new Vector3(1)); 
        
        grassMat = new Material(); 
        grassMat.setShader(shader); 
        grassMat.setDiffuse(new Vector3(0.3f, 0.6f, 0.3f)); 
        grassMat.setSpecular(new Vector3(0)); 
        
        camera = new Camera(); 
        camera.setPosition(new Vector3(0, 0, 10));
        
        Oasis.getMouse().setCursorVisible(false); 
        Oasis.getMouse().center(); 
    }

    @Override
    public void update(float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        if (keys.isKeyDown(Keyboard.KEY_Q)) {
            Oasis.stop();
        }
        
        ticks++; 
        
        Vector3 camPos = camera.getPosition(); 
        Quaternion camRot = camera.getRotation(); 
        
        Vector3 dir = new Vector3(); 
        
        if (keys.isKeyDown(Keyboard.KEY_I)) {
            dir.addSelf(new Vector3(0, 0, -1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_K)) {
            dir.addSelf(new Vector3(0, 0, 1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_J)) {
            dir.addSelf(new Vector3(-1, 0, 0)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_L)) {
            dir.addSelf(new Vector3(1, 0, 0)); 
        }
        
        dir.rotateSelf(camRot).setY(0); 
        
        if (keys.isKeyDown(Keyboard.KEY_SPACE)) {
            dir.addSelf(new Vector3(0, 1, 0)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_SHIFT)) {
            dir.addSelf(new Vector3(0, -1, 0)); 
        }
        
        dir.normalizeSelf().multiplySelf(30 * dt); 
        camPos.addSelf(dir); 
        
        camera.setPosition(camPos); 
        
        Mouse mouse = Oasis.getMouse(); 
        
        yaw += -mouse.getDx() * 0.001f; 
        pitch += -mouse.getDy() * 0.001f; 
        camera.setRotation(yaw, pitch);
        
        mouse.center(); 
    }
    
    public static float randomize(float num) {
        return num + (float) Math.random() * 0.3f - 0.15f; 
    }

    @Override
    public void render() {
        GraphicsDevice gd = Oasis.getGraphicsDevice(); 
        Graphics g = Oasis.getGraphics(); 
        
        Vector3 sky = new Vector3(0.6f, 0.7f, 0.9f); 
        
        gd.clearBuffers(new Vector4(sky, 1.0f), true); 
        gd.setState(state); 
        
        Transform t = new Transform(); 
        t.setPosition(new Vector3(10, -4, 10)); 
        t.setRotation(Quaternion.axisAngle(new Vector3(0, 1, 0), Mathf.toRadians(ticks * 1f))); 
        t.setScale(new Vector3(60)); 
        
        g.begin(); 
        g.addAmbient(sky.multiply(0.6f)); 
        
        DirectionalLight light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f)); 
        light.setDirection(new Vector3(-1, -1, 0).normalize()); 
        
        g.addLight(light);
        g.setCamera(camera); 
        g.drawMesh(mesh, 0, material, Matrix4.identity()); 
        g.drawMesh(mesh2, 0, material, t.getMatrix()); 
        g.drawMesh(mesh3, 0, grassMat, Matrix4.identity()); 
        g.finish(); 
    }

    @Override
    public void exit() {
        log.debug("exit"); 
    }
    
    @Override
    public boolean closeAttempt() {
        return true; 
    }
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        conf.width = 800; 
        conf.height = 600; 
        conf.fps = 60.001f; 
        conf.ups = 60; 
        Oasis.start(conf, new GraphicsApp()); 
    }

}
