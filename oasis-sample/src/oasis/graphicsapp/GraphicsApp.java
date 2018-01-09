package oasis.graphicsapp;

import java.util.Random;

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
    private Material material2; 
    private Material grassMat; 
    private Shader shader; 
    private GraphicsState state; 
    private Camera camera; 
    
    private float yaw, pitch; 
    
    private boolean[] lights = new boolean[4]; 
    
    @Override
    public void init() {
        log.debug("init"); 
        
        Oasis.getDisplay().setResizable(true); 
        
        state = new GraphicsState(); 
//        state.setFrontFace(FrontFace.BOTH);
        
        mesh = ObjImporter.load("sphere.obj");
//        mesh2 = ObjImporter.load("bunny.obj"); 
//        mesh3 = ObjImporter.load("terrain4.obj"); 
        
        log.debug(mesh.getGeometry(0).getVertexBuffer(0).getVertexCount()); 
        
        String vs = GlslParser.getVertexSource("test.glsl"); 
        String fs = GlslParser.getFragmentSource("test.glsl"); 
        shader = Oasis.getGraphicsDevice().createShader(vs, fs); 
        
        material = new Material(); 
        material.setShader(shader); 
        material.setDiffuseColor(new Vector3(0.4f, 0.5f, 0.7f)); 
        material.setSpecularColor(new Vector3(0.5f)); 
        material.setSpecularPower(100);
        
        material2 = new Material(); 
        material2.setShader(shader); 
        material2.setDiffuseColor(new Vector3(0.3f, 0.3f, 0.3f)); 
        material2.setSpecularColor(new Vector3(1)); 
        material2.setSpecularPower(10);
        
        grassMat = new Material(); 
        grassMat.setShader(shader); 
        grassMat.setDiffuseColor(new Vector3(0.3f, 0.6f, 0.3f)); 
        grassMat.setSpecularColor(new Vector3(0)); 
        
        camera = new Camera(); 
        camera.setPosition(new Vector3(0, 0, 10));
        
        Oasis.getMouse().setCursorVisible(false); 
        Oasis.getMouse().center(); 
    }

    @Override
    public void update(float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        if (keys.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Oasis.stop();
        }
        
        ticks++; 
        
        Vector3 camPos = camera.getPosition(); 
        Quaternion camRot = camera.getRotation(); 
        
        Vector3 dir = new Vector3(); 
        
        if (keys.isKeyJustDown(Keyboard.KEY_Q)) lights[0] = !lights[0]; 
        if (keys.isKeyJustDown(Keyboard.KEY_W)) lights[1] = !lights[1]; 
        if (keys.isKeyJustDown(Keyboard.KEY_E)) lights[2] = !lights[2]; 
        if (keys.isKeyJustDown(Keyboard.KEY_R)) lights[3] = !lights[3]; 
        
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
        
        g.begin(); 
        g.addAmbient(sky.multiply(0.6f)); 
        
        DirectionalLight light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0, 0)); 
        light.setDirection(new Vector3(-1, -1, -1).normalize()); 
        if (lights[0]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0, 0, 0.6f)); 
        light.setDirection(new Vector3(-1, -1, 1).normalize()); 
        if (lights[1]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(1, -1, -1).normalize()); 
        if (lights[2]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0, 0.6f, 0)); 
        light.setDirection(new Vector3(1, -1, 1).normalize()); 
        if (lights[3]) g.addLight(light);
        
        g.setCamera(camera); 
        
        Transform t = new Transform(); 
        t.setRotation(Quaternion.axisAngle(new Vector3(0, 1, 0), Mathf.toRadians(-ticks * 0.1f))); 
//        g.drawMesh(mesh, 0, material, t.getMatrix()); 
        
        t = new Transform(); 
        t.setPosition(new Vector3(10, -4, 10)); 
        t.setRotation(Quaternion.axisAngle(new Vector3(0, 1, 0), Mathf.toRadians(ticks * 1f))); 
        t.setScale(new Vector3(1)); 
//        g.drawMesh(mesh, 0, material2, t.getMatrix()); 
        
        Material[] mats = new Material[] { material, material2, grassMat }; 
        
        int count = 0; 
        Random r = new Random(1); 
        for (int i = -20; i < 20; i++) {
            for (int j = -20; j < 20; j++) {
                for (int k = -0; k < 1; k++) {
                    Material mat = new Material(); 
                    mat.setShader(shader); 
                    
                    mat.setDiffuseColor(new Vector3(r.nextFloat(), r.nextFloat(), r.nextFloat())); 
                    mat.setSpecularColor(mat.getDiffuseColor().multiply(r.nextFloat()* 1)); 
                    mat.setSpecularPower((float) Math.pow(2, r.nextFloat() * 4));
                    
                    g.drawMesh(mesh, 0, mat, Matrix4.translation(new Vector3(i * 2, k * 2, j * 2)));
                    count++; 
                }
            }
        }
        
//        log.debug("Drawing " + count + " objects"); 
        
//        g.drawMesh(mesh, 0, material, Matrix4.translation(new Vector3(4, 0, 0))); 
//        g.drawMesh(mesh, 0, material2, Matrix4.translation(new Vector3(7, 0, 0))); 
//        g.drawMesh(mesh, 0, grassMat, Matrix4.translation(new Vector3(10, 0, 0))); 
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
        conf.fps = 59.97f; 
        conf.ups = 60f; 
        Oasis.start(conf, new GraphicsApp()); 
    }

}
