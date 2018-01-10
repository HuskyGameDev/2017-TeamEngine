package oasis.graphicsapp;

import java.util.Arrays;

import oasis.core.Application;
import oasis.core.BackendType;
import oasis.core.Config;
import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.file.GlslParser;
import oasis.file.ObjImporter;
import oasis.file.TextureLoader;
import oasis.graphics.Camera;
import oasis.graphics.DirectionalLight;
import oasis.graphics.FrontFace;
import oasis.graphics.Graphics;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsState;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class GraphicsApp implements Application {

    private static final Logger log = new Logger(GraphicsApp.class); 
    
    private int ticks = 0; 
    
    private Mesh mesh; 
    private Mesh mesh2; 
    private Mesh mesh3; 
    private Mesh quad; 
    private Material material; 
    private Material material2; 
    private Material grassMat; 
    private Material quadMat; 
    private Shader shader; 
    private Shader quadShader; 
    private GraphicsState state; 
    private Camera camera; 
    
    private Texture2D diffuseTex; 
    private Texture2D diffuseTex2; 
    
    private float yaw, pitch; 
    
    private boolean[] lights = new boolean[4]; 
    
    @Override
    public void init() {
        log.debug("init"); 
        
        lights[2] = true; 
        
        Oasis.getDisplay().setResizable(true); 
        
        state = new GraphicsState(); 
//        state.setFrontFace(FrontFace.BOTH);
        
        mesh = ObjImporter.load("texture-sphere.obj");
//        mesh2 = ObjImporter.load("bunny.obj"); 
        mesh3 = ObjImporter.load("texture-terrain.obj"); 
        
        quad = ObjImporter.load("texture-sphere.obj"); 
//        quad = new Mesh(); 
//        quad.setPositions(new Vector3[] {
//                new Vector3(-1, -1, 0), 
//                new Vector3( 1, -1, 0), 
//                new Vector3( 1,  1, 0), 
//                new Vector3(-1,  1, 0) 
//        });
//        quad.setTexCoords(new Vector2[] {
//                new Vector2(0, 0), 
//                new Vector2(1,0), 
//                new Vector2(1, 1), 
//                new Vector2(0, 1) 
//        });
//        quad.setIndices(0, new short[] {
//                0, 1, 2, 
//                0, 2, 3
//        });
//        quad.upload(); 
        
        String vs = GlslParser.getVertexSource("test.glsl"); 
        String fs = GlslParser.getFragmentSource("test.glsl"); 
        shader = new Shader(vs, fs); 
        
        vs = GlslParser.getVertexSource("texture.glsl"); 
        fs = GlslParser.getFragmentSource("texture.glsl"); 
        quadShader = new Shader(vs, fs); 
        
        diffuseTex = TextureLoader.get("diffuse-and-normals/160.JPG"); 
        
        diffuseTex2 = TextureLoader.get("grass.jpg"); 
        
        quadMat = new Material(); 
        quadMat.setShader(quadShader); 
        quadMat.setFrontFace(FrontFace.BOTH);
        quadMat.setDiffuseTexture(diffuseTex); 
        
        material = new Material(); 
        material.setShader(shader); 
        material.setDiffuseColor(new Vector3(0.8f, 0.8f, 0.8f)); 
        material.setSpecularColor(new Vector3(1f)); 
        material.setSpecularPower(5);
        material.setDiffuseTexture(diffuseTex); 
        
        material2 = new Material(); 
        material2.setShader(shader); 
        material2.setDiffuseColor(new Vector3(1.0f, 1.0f, 1.0f).multiply(0.7f)); 
        material2.setSpecularColor(new Vector3(0.0f)); 
        material2.setSpecularPower(50);
        material2.setDiffuseTexture(diffuseTex2); 
        
        grassMat = new Material(); 
        grassMat.setShader(shader); 
        grassMat.setDiffuseColor(new Vector3(0.3f, 0.6f, 0.3f)); 
        grassMat.setSpecularColor(new Vector3(0)); 
        
        camera = new Camera(); 
        camera.setPosition(new Vector3(0, 3, 0));
        
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
        light.setDirection(new Vector3(1, -1, 1).normalize().rotate(Quaternion.axisAngle(new Vector3(1, 0, 0), ticks * 0.01f))); 
        if (lights[2]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0, 0.6f, 0)); 
        light.setDirection(new Vector3(1, -1, 1).normalize()); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(0, -1, 0).normalize().rotate(Quaternion.axisAngle(new Vector3(1, 0, 0), ticks * 1f))); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.0f, 0.6f)); 
        light.setDirection(new Vector3(0, 1, 0).normalize()); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(0, 0, 1).normalize()); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(1, 0, 0).normalize()); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(0, 0, -1).normalize()); 
        if (lights[3]) g.addLight(light);
        
        light = new DirectionalLight(); 
        light.setColor(new Vector3(0.6f, 0.6f, 0.6f)); 
        light.setDirection(new Vector3(-1, 0, 0).normalize()); 
        if (lights[3]) g.addLight(light);
        
        g.setCamera(camera); 
        
        g.drawMesh(mesh3, 0, material2, Matrix4.translation(new Vector3(0, -1, 0))); 
        
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                for (int k = -0; k < 1; k++) {
                    g.drawMesh(mesh, 0, material, Matrix4.translation(new Vector3(i * 2, k * 2, j * 2)));
                }
            }
        }
        
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
