package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.graphics.FrontFace;
import oasis.graphics.model.Material;
import oasis.graphics.model.Mesh;
import oasis.graphics.model.Model;
import oasis.graphics.model.ModelRenderer;
import oasis.graphics.model.PerspectiveCamera;
import oasis.graphics.model.RenderQueue;
import oasis.math.Mathf;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;
import oasis.math.Vector4f;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private PerspectiveCamera camera; 
    private ModelRenderer renderer; 
    
    private Mesh heightmap, water; 
    private Material heightmapMaterial, waterMaterial; 
    
    private Model terrainModel; 
    
    private float angle = 0.0f; 
    
    private float freq = 1 / 8.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long res = 512; 
    
    private float height = 4.5f; 
    
    private Heightmap htmap; 

    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        renderer = new ModelRenderer(graphics); 
        camera = new PerspectiveCamera(800, 600, 70.0f, 0.1f, 1000.0f); 
        
        float offset = 0.015f; 
        
        htmap = new Heightmap();
        htmap.setFlat(true);
        water = new Mesh(graphics); 
        water.setFrontFace(FrontFace.CCW); 
        htmap.genMeshData(new Vector3f(-10, height * 0.65f - offset, -10), new Vector3f(10, height * 0.65f + offset, 10), (int) res, (int) res, 5, freq * 10, 0.9f).apply(water);
        
        heightmap = new Mesh(graphics); 
        heightmap.setFrontFace(FrontFace.CCW);
        htmap.setFlat(false);
        htmap.genMeshData(new Vector3f(-10, 0, -10), new Vector3f(10, height, 10), (int) res, (int) res, octs, freq, pers).apply(heightmap);
        
        waterMaterial = new Material(); 
        waterMaterial.renderQueue = RenderQueue.TRANSLUCENT; 
        waterMaterial.specularPower = 100.0f; 
        waterMaterial.specularColor = new Vector4f(1); 
        waterMaterial.shader = null; 
        
        heightmapMaterial = new Material(); 
        heightmapMaterial.renderQueue = RenderQueue.OPAQUE;
        heightmapMaterial.specularPower = 200.0f; 
        heightmapMaterial.specularColor = new Vector4f(0); 
        heightmapMaterial.shader = null; 
        
        terrainModel = new Model(); 
        terrainModel.add(water, waterMaterial);
        terrainModel.add(heightmap, heightmapMaterial);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }

        angle += 2f / 60.0f; 
    }

    @Override
    public void onRender() {
        graphics.clearScreen(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f));
        
        camera.setPosition(new Vector3f(-10, 6, 0));
        camera.setRotation(-Mathf.PI * 0.5f, -Mathf.toRadians(20));
        
        renderer.begin(camera);
        renderer.draw(terrainModel, new Vector3f(0, 0, 0), Quaternionf.axisAngle(new Vector3f(0, 1, 0), angle * 0.1f));
        renderer.end(); 
    }
    
    @Override
    public void onExit() {
        
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        Config cfg = new Config();
        cfg.engine = Jogl3Engine.class;
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
