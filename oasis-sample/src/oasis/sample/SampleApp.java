package oasis.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oasis.audio.AudioListener;
import oasis.audio.Sound;
import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.file.ObjImporter;
import oasis.graphics.ColorRgba;
import oasis.graphics.FillMode;
import oasis.graphics.FrontFace;
import oasis.graphics.light.PointLight;
import oasis.graphics.model.Material;
import oasis.graphics.model.Mesh;
import oasis.graphics.model.Model;
import oasis.graphics.model.ModelRenderer;
import oasis.graphics.model.PerspectiveCamera;
import oasis.graphics.model.RenderQueue;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Mathf;
import oasis.math.Quaternion;
import oasis.math.Transform;
import oasis.math.Vector3;
import oasis.math.Vector4;

/**
 * displays basic terrain and cubes
 *  
 * @author Nicholas Hamilton 
 *
 */
public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    // camera and model renderer 
    private PerspectiveCamera camera; 
    private float camPitch, camYaw; 
    private ModelRenderer renderer; 
    
    // meshes and materials 
    private Model terrainModel, treeModel, sunModel, lightModel1, lightModel2, lightModel3, lightModel4, dragonModel; 
    
    // sounds 
    private Sound sound, music; 
    private AudioListener listener; 
    
    // current angle 
    private float angle = 0.0f; 
    
    // positions of the cubes 
    private Transform[] treeTransforms; 
    
    private Transform dragonTransform; 

    @Override
    public void onInit() {
        Oasis.display.setResizable(true);
        Oasis.mouse.setCursorVisible(false); 
        
        renderer = new ModelRenderer(); 
        camera = new PerspectiveCamera(800, 600, 70.0f, 0.1f, 1000.0f); 
        camera.setPosition(new Vector3(0, 5, 0));
        
        // load audio 
        listener = Oasis.audio.createListener(); 
        Oasis.audio.setListener(listener); 
        
        sound = new Sound("theme_song.wav"); 
        sound.setLooping(true); 
        sound.setMinDistance(10.0f); 
        sound.setMaxDistance(100.0f); 
        sound.play(); 
        
        music = new Sound("overworld.wav"); 
        music.setLooping(true); 
        music.setGain(0.05f);
        music.play(); 
        
        // load meshes 
        
        Mesh water = ObjImporter.load("plane.obj"); 
        Mesh heightmap = ObjImporter.load("terrain4.obj"); 
        Mesh dragon = ObjImporter.load("monkey.obj"); 
        Mesh treeTrunk = ObjImporter.load("tree-trunk.obj"); 
        Mesh treeLeaves = ObjImporter.load("tree-leaves.obj"); 
        Mesh sphere = ObjImporter.load("sphere.obj"); 
        
        // load materials 
        
        Material waterMaterial = new Material(); 
        waterMaterial.renderQueue = RenderQueue.TRANSLUCENT; 
        waterMaterial.diffuseColor = new Vector4(0.45f, 0.55f, 0.85f, 0.7f); 
        waterMaterial.specularPower = 100.0f; 
        waterMaterial.specularColor = new Vector4(1); 
        waterMaterial.shader = null; 
        waterMaterial.frontFace = FrontFace.CCW; 
        
        Material heightmapMaterial = new Material(); 
        heightmapMaterial.renderQueue = RenderQueue.OPAQUE;
        heightmapMaterial.diffuseColor = new Vector4(0.6f, 0.8f, 0.6f, 1.0f); 
        heightmapMaterial.specularPower = 200.0f; 
        heightmapMaterial.specularColor = new Vector4(0); 
        heightmapMaterial.shader = null; 
        heightmapMaterial.frontFace = FrontFace.CCW; 
        
        Material woodMaterial = new Material(); 
        woodMaterial.diffuseColor = new Vector4(0.6f, 0.4f, 0.3f, 1.0f); 
        woodMaterial.specularColor = new Vector4(0); 
        woodMaterial.specularPower = 20.0f; 
        woodMaterial.frontFace = FrontFace.CCW; 
        
        Material leafMaterial = new Material(); 
        leafMaterial.diffuseColor = new Vector4(0.4f, 0.6f, 0.4f, 1.0f); 
        leafMaterial.specularColor = new Vector4(0); 
        leafMaterial.specularPower = 20.0f; 
        leafMaterial.frontFace = FrontFace.CCW; 
        
        Material sunMaterial = new Material(); 
        sunMaterial.diffuseColor = new Vector4(0, 1); 
        sunMaterial.emissiveColor = new Vector4(1, 1, 0.8f, 1); 
        sunMaterial.frontFace = FrontFace.CCW; 
        
        Material lightMaterial1 = new Material(); 
        lightMaterial1.diffuseColor = new Vector4(0.2f, 1); 
        lightMaterial1.emissiveColor = new Vector4(1, 0.75f, 0.75f, 1); 
        lightMaterial1.frontFace = FrontFace.CCW; 
        
        Material lightMaterial2 = new Material(); 
        lightMaterial2.diffuseColor = new Vector4(0.2f, 1); 
        lightMaterial2.emissiveColor = new Vector4(0.75f, 0.75f, 1, 1); 
        lightMaterial2.frontFace = FrontFace.CCW; 
        
        Material lightMaterial3 = new Material(); 
        lightMaterial3.diffuseColor = new Vector4(0.2f, 1); 
        lightMaterial3.emissiveColor = new Vector4(0.75f, 1, 0.75f, 1); 
        lightMaterial3.frontFace = FrontFace.CCW; 
        
        Material lightMaterial4 = new Material(); 
        lightMaterial4.diffuseColor = new Vector4(0.2f, 1); 
        lightMaterial4.emissiveColor = new Vector4(1, 1, 0.75f, 1); 
        lightMaterial4.frontFace = FrontFace.CCW; 

        Material metal = new Material(); 
        metal.diffuseColor = new Vector4(0.6f, 0.6f, 0.6f, 1.0f); 
        metal.specularColor = new Vector4(1); 
        metal.specularPower = 20.0f; 
        metal.frontFace = FrontFace.CCW; 
        
        // model creation 
        
        terrainModel = new Model(); 
        terrainModel.add(water, waterMaterial);
        terrainModel.add(heightmap, heightmapMaterial);
        
        dragonModel = new Model(); 
        dragonModel.add(dragon, metal); 
        
        dragonTransform = new Transform(); 
        dragonTransform.setPosition(new Vector3(0, 6, 0));
        dragonTransform.setRotation(Quaternion.axisAngle(new Vector3(1, 0, 0), Mathf.toRadians(180)).multiply(Quaternion.axisAngle(new Vector3(0, 1, 1), Mathf.toRadians(130))));
        dragonTransform.setScale(new Vector3(8, 6, 4)); 
        
        treeModel = new Model(); 
        treeModel.add(treeTrunk, woodMaterial);
        treeModel.add(treeLeaves, leafMaterial);
        
        sunModel = new Model(); 
        sunModel.add(sphere, sunMaterial);
        
        lightModel1 = new Model(); 
        lightModel1.add(sphere, lightMaterial1);
        
        lightModel2 = new Model(); 
        lightModel2.add(sphere, lightMaterial2);
        
        lightModel3 = new Model(); 
        lightModel3.add(sphere, lightMaterial3);
        
        lightModel4 = new Model(); 
        lightModel4.add(sphere, lightMaterial4);
        
        // create cube data 
        generateTreePositions(); 
    }
    
    // generate cube data 
    private void generateTreePositions() {
        List<Transform> posList = new ArrayList<>(); 
        Random rand = new Random(); 
        for (int i = 0; i < 64; i++) {
            Transform t = new Transform(); 
            Vector3 v = new Vector3(
                    rand.nextInt(300) - 150,
                    0,
                    rand.nextInt(300) - 150); 
            
            t.setPosition(new Vector3(v).setY(1.0f)); 
            t.setRotation(Quaternion.axisAngle(new Vector3(0, 1, 0), rand.nextFloat() * 2 * Mathf.PI));
            
            posList.add(t); 
        }
        
        treeTransforms = new Transform[posList.size()]; 
        posList.toArray(treeTransforms); 
    }

    @Override
    public void onUpdate(float dt) {
        if (Oasis.display.shouldClose()) {
            stop();
        }

        angle += 2f / 60.0f; 
        
        dragonTransform.setRotation(Quaternion.axisAngle(new Vector3(Mathf.sin(angle * 0.3f), 1, Mathf.cos(angle * 0.5f)), 2 * dt).multiply(dragonTransform.getRotation()));
        
        if (Oasis.mouse.getScroll() != Mouse.ScrollDirection.NONE) { 
            System.out.println(Oasis.mouse.getScroll());
        }
        
        Vector3 move = new Vector3(); 
        Vector3 vertMove = new Vector3(); 
        float speed = 20; 
        
        if (Oasis.keyboard.isKeyJustDown(Keyboard.KEY_W)) {
            FillMode fill = null; 
            switch (Oasis.graphics.getFillMode()) {
            case FILL: 
                fill = FillMode.LINE; 
                break; 
            case LINE: 
                fill = FillMode.POINT; 
                break; 
            case POINT: 
                fill = FillMode.FILL; 
                break; 
            }
            
            Oasis.graphics.setFillMode(fill); 
        }
        
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_I)) {
            move.addSelf(new Vector3(0, 0, -1).rotateSelf(camera.getRotation())); 
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_K)) {
            move.addSelf(new Vector3(0, 0, 1).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_J)) {
            move.addSelf(new Vector3(-1, 0, 0).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_L)) {
            move.addSelf(new Vector3(1, 0, 0).rotateSelf(camera.getRotation()));
        }
        
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            vertMove.addSelf(new Vector3(0, 1, 0).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            vertMove.addSelf(new Vector3(0, -1, 0).rotateSelf(camera.getRotation()));
        }
        
        vertMove.x = vertMove.z = 0; 
        
        move.y = vertMove.normalizeSelf().y; 
        move.normalizeSelf().multiplySelf(speed * dt);  
        
        camYaw += Oasis.mouse.getDx() * -0.05f * dt; 
        camPitch += Oasis.mouse.getDy() * -0.05f * dt; 
        Oasis.mouse.center(); 
        
        camYaw %= Mathf.toRadians(360); 
        if (camPitch < Mathf.toRadians(-89.999f)) {
            camPitch = Mathf.toRadians(-89.999f); 
        }
        if (camPitch > Mathf.toRadians(89.999f)) {
            camPitch = Mathf.toRadians(89.999f); 
        }
        camera.setRotation(camYaw, camPitch);
        camera.setPosition(camera.getPosition().add(move));
        
        listener.setPosition(camera.getPosition());
        listener.setOrientation(camera.getRotation()); 
    }

    @Override
    public void onRender() {
        Vector3 sunPos = new Vector3();
        sunPos.x = Mathf.cos(angle * 0.02f);
        sunPos.y = Mathf.sin(angle * 0.02f); 
        sunPos.z = 0; 
        sunPos.multiplySelf(200); 
        
        Oasis.graphics.clearScreen(new ColorRgba(new Vector4(0.8f, 0.9f, 1.0f, 1.0f).multiply(new Vector3(0, 1, 0).dot(sunPos.normalize())))); 
        
        // render scene 
        renderer.begin(camera);
        
        Vector3 lightPos = new Vector3(); 
        lightPos.x = Mathf.cos(angle * 0.2f);
        lightPos.z = Mathf.sin(angle * 0.2f); 
        lightPos.multiplySelf(80); 
        lightPos.y = 5; 
        
        renderer.addLight(new PointLight(new Vector3(0.8f, 0.4f, 0.4f), lightPos, 180));
        renderer.draw(lightModel1, lightPos, new Quaternion());
        
        float tmp = lightPos.x; 
        lightPos.x = -lightPos.z; 
        lightPos.z = tmp; 
        
        renderer.addLight(new PointLight(new Vector3(0.4f, 0.4f, 0.8f), lightPos, 180));
        renderer.draw(lightModel2, lightPos, new Quaternion());
        
        lightPos.x *= -1; 
        lightPos.z *= -1; 
        
        renderer.addLight(new PointLight(new Vector3(0.4f, 0.8f, 0.4f), lightPos, 180));
        renderer.draw(lightModel3, lightPos, new Quaternion());
        
        tmp = lightPos.x; 
        lightPos.x = lightPos.z; 
        lightPos.z = -tmp; 
        
        renderer.addLight(new PointLight(new Vector3(0.8f, 0.8f, 0.4f), lightPos, 180));
        renderer.draw(lightModel4, lightPos, new Quaternion());
        
        if (sunPos.y >= 0) renderer.addLight(new PointLight(new Vector3(0.8f, 0.8f, 0.7f), sunPos, 600));
        
        Transform sunTfm = new Transform(); 
        sunTfm.setPosition(sunPos);
        sunTfm.setScale(new Vector3(10));
        
        renderer.draw(sunModel, sunTfm);
        
        Transform tfm = new Transform(); 
        tfm.setPosition(dragonTransform.getPosition().add(new Vector3(16, 0, 0)));
        
        sound.setPosition(dragonTransform.getPosition()); 
        renderer.draw(dragonModel, dragonTransform);
        renderer.draw(dragonModel, tfm);
        renderer.draw(terrainModel, new Vector3(0, 0, 0), new Quaternion());
        for (int i = 1; i < treeTransforms.length + 1; i++) {
            renderer.draw(treeModel, treeTransforms[i - 1]); 
        }
        renderer.end(); 
    }
    
    @Override
    public void onExit() {
        
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        Config cfg = new Config();
        cfg.engine = Jogl3Engine.class;
        cfg.fps = 60.0f; //Config.UNLIMITED_FPS;
        cfg.ups = 60.0f;
        cfg.width = 800; 
        cfg.height = 600; 
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
