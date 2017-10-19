package oasis.sample;

import java.util.Random;

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
    
    private Mesh heightmap, water, cube; 
    private Material heightmapMaterial, waterMaterial, cubeMaterial; 
    
    private Model terrainModel, cubeModel; 
    
    private float angle = 0.0f; 
    
    private float freq = 1 / 8.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long res = 512; 
    
    private float height = 4.5f; 
    
    private Heightmap htmap; 
    
    private Vector3f[] cubePositions; 

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
        
        generateCube(); 
    }
    
    private void generateCube() {
        cubePositions = new Vector3f[32]; 
        Random rand = new Random(); 
        for (int i = 0; i < cubePositions.length; i++) {
            cubePositions[i] = new Vector3f(
                    rand.nextFloat() * 20 - 10,
                    rand.nextFloat() * 4 + 2,
                    rand.nextFloat() * 20 - 10); 
        }
        
        float s = 0.25f; 
        Vector3f[] positions = new Vector3f[] {
                // front
                new Vector3f(-s, -s, -s), 
                new Vector3f( s, -s, -s),
                new Vector3f( s,  s, -s),
                new Vector3f(-s,  s, -s),
                // back
                new Vector3f( s, -s,  s),
                new Vector3f(-s, -s,  s),
                new Vector3f(-s,  s,  s),
                new Vector3f( s,  s,  s),
                // left
                new Vector3f(-s, -s, -s),
                new Vector3f(-s, -s,  s),
                new Vector3f(-s,  s,  s),
                new Vector3f(-s,  s, -s),
                // right 
                new Vector3f( s, -s,  s),
                new Vector3f( s, -s, -s),
                new Vector3f( s,  s, -s),
                new Vector3f( s,  s,  s),
                // top 
                new Vector3f(-s,  s,  s),
                new Vector3f( s,  s,  s),
                new Vector3f( s,  s, -s),
                new Vector3f(-s,  s, -s),
                // bottom
                new Vector3f(-s, -s, -s),
                new Vector3f( s, -s, -s),
                new Vector3f( s, -s,  s),
                new Vector3f(-s, -s,  s),
        }; 
        
        Vector3f[] normals = new Vector3f[] {
                // front
                new Vector3f(0, 0, -1), 
                new Vector3f(0, 0, -1), 
                new Vector3f(0, 0, -1), 
                new Vector3f(0, 0, -1),
                // back
                new Vector3f(0, 0, 1), 
                new Vector3f(0, 0, 1), 
                new Vector3f(0, 0, 1), 
                new Vector3f(0, 0, 1), 
                // left
                new Vector3f(-1, 0, 0), 
                new Vector3f(-1, 0, 0), 
                new Vector3f(-1, 0, 0), 
                new Vector3f(-1, 0, 0), 
                // right
                new Vector3f(1, 0, 0), 
                new Vector3f(1, 0, 0), 
                new Vector3f(1, 0, 0), 
                new Vector3f(1, 0, 0), 
                // top
                new Vector3f(0, 1, 0), 
                new Vector3f(0, 1, 0), 
                new Vector3f(0, 1, 0), 
                new Vector3f(0, 1, 0), 
                // bottom
                new Vector3f(0,-1, 0), 
                new Vector3f(0,-1, 0), 
                new Vector3f(0,-1, 0), 
                new Vector3f(0,-1, 0), 
        };
        
        Vector4f color = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f); 
        Vector4f[] colors = new Vector4f[] {
                color, color, color, color,
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
        };
        
        int[] indices = new int[] {
                0, 1, 2, 0, 2, 3, 
                4, 5, 6, 4, 6, 7, 
                8, 9, 10, 8, 10, 11, 
                12, 13, 14, 12, 14, 15, 
                16, 17, 18, 16, 18, 19, 
                20, 21, 22, 20, 22, 23, 
        }; 
        
        cube = new Mesh(graphics);
        cube.setFrontFace(FrontFace.BOTH);
        cube.setColors(colors);
        cube.setPositions(positions);
        cube.setNormals(normals);
        cube.setIndices(indices);
        
        cubeMaterial = new Material(); 
        cubeMaterial.diffuseColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f); 
        cubeMaterial.specularColor = new Vector4f(1, 1, 1, 1); 
        cubeMaterial.specularPower = 20.0f; 
        
        cubeModel = new Model(); 
        cubeModel.add(cube, cubeMaterial);
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
        
        camera.setPosition(new Vector3f(-10, 6, 0).rotate(Quaternionf.axisAngle(new Vector3f(0, 1, 0), angle * 0.1f)));
        camera.setRotation(-Mathf.PI * 0.5f + angle * 0.1f, -Mathf.toRadians(20));
        
        renderer.begin(camera);
        renderer.draw(terrainModel, new Vector3f(0, 0, 0), new Quaternionf());
        for (int i = 0; i < cubePositions.length; i++) {
            renderer.draw(cubeModel, cubePositions[i], 
                    Quaternionf.axisAngle(new Vector3f(0, 1, 0), angle * 0.4f * i / 10).multiply(
                            Quaternionf.axisAngle(new Vector3f(1, 0, 0), angle * 0.7f * i / 10).multiply(
                                    Quaternionf.axisAngle(new Vector3f(0, 0, 1), angle * 0.9f * i / 10)))); 
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
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
