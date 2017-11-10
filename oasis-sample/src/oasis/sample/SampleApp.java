package oasis.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import oasis.math.Quaternionf;
import oasis.math.Vector3f;
import oasis.math.Vector4f;

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
    private Model terrainModel, cubeModel, sunModel, lightModel1, lightModel2, lightModel3, lightModel4, dragonModel; 
    
    // current angle 
    private float angle = 0.0f; 
    
    // positions of the cubes 
    private Vector3f[] treePositions; 

    @Override
    public void onInit() {
        Oasis.display.setResizable(true);
        Oasis.mouse.setCursorVisible(false); 
        
        renderer = new ModelRenderer(); 
        camera = new PerspectiveCamera(800, 600, 70.0f, 0.1f, 1000.0f); 
        camera.setPosition(new Vector3f(0, 5, 0));
        
        // load meshes 
        
        Mesh water = ObjImporter.load("plane.obj"); 
        Mesh heightmap = ObjImporter.load("terrain3.obj"); 
        Mesh dragon = ObjImporter.load("dragon.obj"); 
        Mesh tree = ObjImporter.load("fir.obj"); 
        Mesh sphere = ObjImporter.load("sphere.obj"); 
        
        // load materials 
        
        Material waterMaterial = new Material(); 
        waterMaterial.renderQueue = RenderQueue.TRANSLUCENT; 
        waterMaterial.diffuseColor = new Vector4f(0.45f, 0.55f, 0.85f, 0.7f); 
        waterMaterial.specularPower = 100.0f; 
        waterMaterial.specularColor = new Vector4f(1); 
        waterMaterial.shader = null; 
        waterMaterial.frontFace = FrontFace.CCW; 
        
        Material heightmapMaterial = new Material(); 
        heightmapMaterial.renderQueue = RenderQueue.OPAQUE;
        heightmapMaterial.diffuseColor = new Vector4f(0.6f, 0.8f, 0.6f, 1.0f); 
        heightmapMaterial.specularPower = 200.0f; 
        heightmapMaterial.specularColor = new Vector4f(0); 
        heightmapMaterial.shader = null; 
        heightmapMaterial.frontFace = FrontFace.CCW; 
        
        Material treeMaterial = new Material(); 
        treeMaterial.diffuseColor = new Vector4f(0.4f, 0.6f, 0.4f, 1.0f); 
        treeMaterial.specularColor = new Vector4f(0); 
        treeMaterial.specularPower = 20.0f; 
        treeMaterial.frontFace = FrontFace.CCW; 
        
        Material sunMaterial = new Material(); 
        sunMaterial.diffuseColor = new Vector4f(0, 1); 
        sunMaterial.emissiveColor = new Vector4f(1, 1, 0.8f, 1); 
        sunMaterial.frontFace = FrontFace.CCW; 
        
        Material lightMaterial1 = new Material(); 
        lightMaterial1.diffuseColor = new Vector4f(0.2f, 1); 
        lightMaterial1.emissiveColor = new Vector4f(1, 0.75f, 0.75f, 1); 
        lightMaterial1.frontFace = FrontFace.CCW; 
        
        Material lightMaterial2 = new Material(); 
        lightMaterial2.diffuseColor = new Vector4f(0.2f, 1); 
        lightMaterial2.emissiveColor = new Vector4f(0.75f, 0.75f, 1, 1); 
        lightMaterial2.frontFace = FrontFace.CCW; 
        
        Material lightMaterial3 = new Material(); 
        lightMaterial3.diffuseColor = new Vector4f(0.2f, 1); 
        lightMaterial3.emissiveColor = new Vector4f(0.75f, 1, 0.75f, 1); 
        lightMaterial3.frontFace = FrontFace.CCW; 
        
        Material lightMaterial4 = new Material(); 
        lightMaterial4.diffuseColor = new Vector4f(0.2f, 1); 
        lightMaterial4.emissiveColor = new Vector4f(1, 1, 0.75f, 1); 
        lightMaterial4.frontFace = FrontFace.CCW; 

        Material metal = new Material(); 
        metal.diffuseColor = new Vector4f(0.6f, 0.6f, 0.6f, 1.0f); 
        metal.specularColor = new Vector4f(1); 
        metal.specularPower = 20.0f; 
        metal.frontFace = FrontFace.CCW; 
        
        // model creation 
        
        terrainModel = new Model(); 
        terrainModel.add(water, waterMaterial);
        terrainModel.add(heightmap, heightmapMaterial);
        
        dragonModel = new Model(); 
        dragonModel.add(dragon, metal); 
        
        cubeModel = new Model(); 
        cubeModel.add(tree, treeMaterial);
        
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
        List<Vector3f> posList = new ArrayList<>(); 
        Random rand = new Random(); 
        for (int i = 0; i < 64; i++) {
            Vector3f v = new Vector3f(
                    rand.nextInt(300) - 150,
                    0,
                    rand.nextInt(300) - 150); 
            
            posList.add(new Vector3f(v).setY(1.0f)); 
        }
        
        treePositions = new Vector3f[posList.size()]; 
        posList.toArray(treePositions); 
    }

    @Override
    public void onUpdate(float dt) {
        if (Oasis.display.shouldClose()) {
            stop();
        }

        angle += 2f / 60.0f; 
        
        if (Oasis.mouse.getScroll() != Mouse.ScrollDirection.NONE) { 
            System.out.println(Oasis.mouse.getScroll());
        }
        
        Vector3f move = new Vector3f(); 
        Vector3f vertMove = new Vector3f(); 
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
            move.addSelf(new Vector3f(0, 0, -1).rotateSelf(camera.getRotation())); 
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_K)) {
            move.addSelf(new Vector3f(0, 0, 1).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_J)) {
            move.addSelf(new Vector3f(-1, 0, 0).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_L)) {
            move.addSelf(new Vector3f(1, 0, 0).rotateSelf(camera.getRotation()));
        }
        
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            vertMove.addSelf(new Vector3f(0, 1, 0).rotateSelf(camera.getRotation()));
        }
        if (Oasis.keyboard.isKeyDown(Keyboard.KEY_SHIFT)) {
            vertMove.addSelf(new Vector3f(0, -1, 0).rotateSelf(camera.getRotation()));
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
    }

    @Override
    public void onRender() {
        Vector3f sunPos = new Vector3f();
        sunPos.x = Mathf.cos(angle * 0.02f);
        sunPos.y = Mathf.sin(angle * 0.02f); 
        sunPos.z = 0; 
        sunPos.multiplySelf(200); 
        
        Oasis.graphics.clearScreen(new ColorRgba(new Vector4f(0.8f, 0.9f, 1.0f, 1.0f).multiply(new Vector3f(0, 1, 0).dot(sunPos.normalize())))); 
        
        // render scene 
        renderer.begin(camera);
        
        Vector3f lightPos = new Vector3f(); 
        lightPos.x = Mathf.cos(angle * 0.2f);
        lightPos.z = Mathf.sin(angle * 0.2f); 
        lightPos.multiplySelf(80); 
        lightPos.y = 5; 
        
        renderer.addLight(new PointLight(new Vector3f(0.8f, 0.4f, 0.4f), lightPos, 80));
        renderer.draw(lightModel1, lightPos, new Quaternionf());
        
        float tmp = lightPos.x; 
        lightPos.x = -lightPos.z; 
        lightPos.z = tmp; 
        
        renderer.addLight(new PointLight(new Vector3f(0.4f, 0.4f, 0.8f), lightPos, 80));
        renderer.draw(lightModel2, lightPos, new Quaternionf());
        
        lightPos.x *= -1; 
        lightPos.z *= -1; 
        
        renderer.addLight(new PointLight(new Vector3f(0.4f, 0.8f, 0.4f), lightPos, 80));
        renderer.draw(lightModel3, lightPos, new Quaternionf());
        
        tmp = lightPos.x; 
        lightPos.x = lightPos.z; 
        lightPos.z = -tmp; 
        
        renderer.addLight(new PointLight(new Vector3f(0.8f, 0.8f, 0.4f), lightPos, 80));
        renderer.draw(lightModel4, lightPos, new Quaternionf());
        
        renderer.addLight(new PointLight(new Vector3f(0.8f, 0.8f, 0.7f), sunPos, 600));
        renderer.draw(sunModel, sunPos, new Quaternionf());
        
        renderer.draw(dragonModel, new Vector3f(0, 2, 0), new Quaternionf());
        renderer.draw(terrainModel, new Vector3f(0, 0, 0), new Quaternionf());
        for (int i = 1; i < treePositions.length + 1; i++) {
            renderer.draw(cubeModel, treePositions[i - 1], new Quaternionf()); 
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
        cfg.fps = Config.UNLIMITED_FPS;
        cfg.ups = 60.0f;
        cfg.width = 800; 
        cfg.height = 600; 
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
