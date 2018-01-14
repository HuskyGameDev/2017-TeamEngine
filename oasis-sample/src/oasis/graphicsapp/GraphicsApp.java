package oasis.graphicsapp;

import oasis.core.Application;
import oasis.core.BackendType;
import oasis.core.Config;
import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.ResourceManager;
import oasis.graphics.Camera;
import oasis.graphics.DirectionalLight;
import oasis.graphics.Graphics;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.RenderTarget;
import oasis.graphics.RenderTexture;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Mathf;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class GraphicsApp implements Application {

    private static final Logger log = new Logger(GraphicsApp.class); 
    
    private int ticks = 0; 
    
    private RenderTarget[] rTargets; 
    private RenderTexture[] rts; 
    private int drawRt = 0; 
    
    private Camera camera; 
    private float yaw, pitch; 
    
    private Mesh sphereMesh; 
    private Mesh terrainMesh; 
    private Material stoneMat; 
    private Material grassMat; 
    private Material goldMat; 
    private Material silverMat; 
    private Material platinumMat; 
    private Material bluePlasticMat; 
    private Material pinkRubberMat; 
    private Material emeraldMat; 
    
    private Vector3 skyColor; 
    private Vector3 ambientColor; 
    private DirectionalLight sunLight; 
    
    @Override
    public void init() {
        log.debug("init"); 
        
        Oasis.getDisplay().setResizable(true); 
        
        sphereMesh = ResourceManager.loadMesh("texture-sphere.obj");
        terrainMesh = ResourceManager.loadMesh("texture-terrain.obj"); 
        
        rts = new RenderTexture[] {
                RenderTexture.create(TextureFormat.RGBA8, 800, 600), 
                RenderTexture.create(TextureFormat.RGBA8, 800, 600)
        }; 
        
        rTargets = new RenderTarget[] {
                RenderTarget.create(RenderTexture.create(TextureFormat.DEPTH24, 1024, 1024), rts[0]),
                RenderTarget.create(RenderTexture.create(TextureFormat.DEPTH24, 1024, 1024), rts[1]) 
        }; 
        
        Shader bbpShader = ResourceManager.loadShader("blinn-phong.glsl"); 
        
        Texture2D normal = Texture2D.create(TextureFormat.RGBA8, 1, 1); 
        normal.setPixels(new int[] { 0x7F7FFFFF });
        
        stoneMat = new Material(); 
        stoneMat.setShader(bbpShader); 
        stoneMat.setDiffuseColor(new Vector3(0.5f, 0.5f, 0.5f));
        stoneMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/164_norm.JPG")); 
        stoneMat.setSpecularColor(new Vector3(0.1f)); 
        stoneMat.setSpecularPower(20); 
        
        goldMat = new Material(); 
        goldMat.setShader(bbpShader); 
        goldMat.setDiffuseColor(new Vector3(0.752f, 0.606f, 0.226f));
        goldMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/160_norm.JPG")); 
        goldMat.setSpecularColor(new Vector3(0.628f, 0.556f, 0.366f)); 
        goldMat.setSpecularPower(128 * 0.4f); 
        
        platinumMat = new Material(); 
        platinumMat.setShader(bbpShader); 
        platinumMat.setDiffuseColor(new Vector3(0.7f, 0.7f, 0.8f));
        platinumMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/163_norm.JPG")); 
        platinumMat.setSpecularColor(new Vector3(0.7f)); 
        platinumMat.setSpecularPower(10); 
        
        silverMat = new Material(); 
        silverMat.setShader(bbpShader); 
        silverMat.setDiffuseColor(new Vector3(0.608f, 0.608f, 0.608f));
        silverMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/170_norm.JPG")); 
        silverMat.setSpecularColor(new Vector3(0.508f, 0.508f, 0.508f)); 
        silverMat.setSpecularPower(128 * 0.4f); 
        
        bluePlasticMat = new Material(); 
        bluePlasticMat.setShader(bbpShader); 
//        bluePlasticMat.setDiffuseColor(new Vector3(0.0f, 0.510f, 0.510f));
        bluePlasticMat.setSpecularColor(new Vector3(0.502f));
        bluePlasticMat.setSpecularPower(128 * 0.25f);
        
        pinkRubberMat = new Material(); 
        pinkRubberMat.setShader(bbpShader); 
        pinkRubberMat.setDiffuseColor(new Vector3(0.6f, 0.4f, 0.4f));
        pinkRubberMat.setSpecularColor(new Vector3(0.4f, 0.04f, 0.04f));
        pinkRubberMat.setSpecularPower(128 * 0.078f);
        
        emeraldMat = new Material(); 
        emeraldMat.setShader(bbpShader); 
        emeraldMat.setDiffuseColor(new Vector3(0.076f, 0.614f, 0.076f)); 
        emeraldMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/161_norm.JPG")); 
        emeraldMat.setSpecularColor(new Vector3(0.633f, 0.728f, 0.633f));
        emeraldMat.setSpecularPower(128 * 0.6f);
        
        grassMat = new Material(); 
        grassMat.setShader(bbpShader); 
        grassMat.setDiffuseMap(ResourceManager.loadTexture2D("grass.jpg")); 
        
        camera = new Camera(); 
        camera.setPosition(new Vector3(0, 3, 10));
        camera.setFov(Mathf.toRadians(70.0f)); 
        
        skyColor = new Vector3(0.6f, 0.7f, 0.9f); 
        ambientColor = new Vector3(0.2f); 
        
        sunLight = new DirectionalLight(); 
        sunLight.setColor(new Vector3(0.8f)); 
        sunLight.setDirection(new Vector3(0, -1, -1));
        
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
        drawRt = (drawRt + 1) % 2; 
        
        GraphicsDevice gd = Oasis.getGraphicsDevice(); 
        Graphics g = Oasis.getGraphics(); 
        
        gd.clearBuffers(new Vector4(skyColor, 1.0f), true); 
        
        camera.setRenderTarget(rTargets[drawRt]); 
        bluePlasticMat.setDiffuseMap(rts[(drawRt + 1) % 2]);
        
        g.begin(); 
        g.addAmbient(ambientColor); 
        g.addLight(sunLight); 
        
        g.setCamera(camera); 
        
        Material[] mats = new Material[] { grassMat, bluePlasticMat, pinkRubberMat, stoneMat, silverMat, goldMat, platinumMat, emeraldMat }; 
        for (int k = 0; k < 2; k++) {
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < mats.length; i++) {
                    g.drawMesh(sphereMesh, 0, mats[(k + j + i) % mats.length], Matrix4.translation(new Vector3(i * 2.2f - 10, 1 + k * 2.2f, j * 2.2f)).multiply(
                            Matrix4.rotation(Quaternion.axisAngle(new Vector3(0, 1, 0).normalize(), ticks * 0.003f))));
                }
            }
        }
        
        g.drawMesh(terrainMesh, 0, grassMat, Matrix4.translation(new Vector3(0, 0, 0))); 
        
        g.finish(); 
        
        ////////////////////////////////////////////////////////
        
        g.blit(camera.getRenderTarget().getColorBuffer(0), null); 
        
//        camera.setRenderTarget(null); 
//        
//        g.begin(); 
//        g.addAmbient(ambientColor); 
//        g.addLight(sunLight); 
//        
//        g.setCamera(camera); 
//        
//        g.drawMesh(terrainMesh, 0, grassMat, Matrix4.translation(new Vector3(0, 0, 0))); 
//        
//        for (int i = 0; i < mats.length; i++) {
//            g.drawMesh(sphereMesh, 0, mats[i], Matrix4.translation(new Vector3(i * 2.2f - 10, 1, 0)).multiply(
//                    Matrix4.rotation(Quaternion.axisAngle(new Vector3(0, 1, 0).normalize(), ticks * 0.003f))));
//        }
//        
//        g.finish(); 
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
