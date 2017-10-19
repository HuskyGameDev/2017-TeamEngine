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
    private ModelRenderer renderer; 
    
    // meshes and materials 
    private Mesh heightmap, water, cube; 
    private Material heightmapMaterial, waterMaterial, cubeMaterial; 
    private Model terrainModel, cubeModel; 
    
    // current angle 
    private float angle = 0.0f; 
    
    // heightmap settings 
    private float freq = 1 / 8.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long res = 512; 
    
    // height of water 
    private float height = 4.5f; 
    
    // positions of the cubes 
    private Vector3f[] cubePositions; 

    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        renderer = new ModelRenderer(graphics); 
        camera = new PerspectiveCamera(800, 600, 70.0f, 0.1f, 1000.0f); 
        
        // generate water and terrain
        float offset = 0.015f; 
        Heightmap htmap = new Heightmap();
        // water
        htmap.setFlat(true);
        water = new Mesh(graphics); 
        water.setFrontFace(FrontFace.CCW); 
        htmap.genMeshData(new Vector3f(-10, height * 0.65f - offset, -10), new Vector3f(10, height * 0.65f + offset, 10), (int) res, (int) res, 5, freq * 10, 0.9f).apply(water);
        // terrain 
        heightmap = new Mesh(graphics); 
        heightmap.setFrontFace(FrontFace.CCW);
        htmap.setFlat(false);
        htmap.genMeshData(new Vector3f(-10, 0, -10), new Vector3f(10, height, 10), (int) res, (int) res, octs, freq, pers).apply(heightmap);
        
        // water material, translucent and shiny
        waterMaterial = new Material(); 
        waterMaterial.renderQueue = RenderQueue.TRANSLUCENT; 
        waterMaterial.diffuseColor = new Vector4f(1); 
        waterMaterial.specularPower = 100.0f; 
        waterMaterial.specularColor = new Vector4f(1); 
        waterMaterial.shader = null; 
        
        // terrain material, opaque and no shine 
        heightmapMaterial = new Material(); 
        heightmapMaterial.renderQueue = RenderQueue.OPAQUE;
        heightmapMaterial.diffuseColor = new Vector4f(1); 
        heightmapMaterial.specularPower = 200.0f; 
        heightmapMaterial.specularColor = new Vector4f(0); 
        heightmapMaterial.shader = null; 
        
        // create a model that contains both meshes
        terrainModel = new Model(); 
        terrainModel.add(water, waterMaterial);
        terrainModel.add(heightmap, heightmapMaterial);
        
        // create cube data 
        generateCube(); 
    }
    
    // generate cube data 
    private void generateCube() {
        // make 32 different cube positions 
        cubePositions = new Vector3f[32]; 
        Random rand = new Random(); 
        for (int i = 0; i < cubePositions.length; i++) {
            cubePositions[i] = new Vector3f(
                    rand.nextFloat() * 20 - 10,
                    rand.nextFloat() * 4 + 2,
                    rand.nextFloat() * 20 - 10); 
        }
        
        // cube model positions 
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
        
        // cube model normals 
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
        
        // cube is gray 
        Vector4f color = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f); 
        Vector4f[] colors = new Vector4f[] {
                color, color, color, color,
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
                color, color, color, color, 
        };
        
        // indices of cube model 
        int[] indices = new int[] {
                0, 1, 2, 0, 2, 3, 
                4, 5, 6, 4, 6, 7, 
                8, 9, 10, 8, 10, 11, 
                12, 13, 14, 12, 14, 15, 
                16, 17, 18, 16, 18, 19, 
                20, 21, 22, 20, 22, 23, 
        }; 
        
        // create actual mesh 
        cube = new Mesh(graphics);
        cube.setFrontFace(FrontFace.BOTH); // some faces are not CCW and I don't have time to fix it right now 
        cube.setColors(colors);
        cube.setPositions(positions);
        cube.setNormals(normals);
        cube.setIndices(indices);
        
        // shiny metallic cube material 
        cubeMaterial = new Material(); 
        cubeMaterial.diffuseColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f); 
        cubeMaterial.specularColor = new Vector4f(1, 1, 1, 1); 
        cubeMaterial.specularPower = 20.0f; 
        
        // put mesh into a model 
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
        
        // set camera position
        camera.setPosition(new Vector3f(-10, 6, 0).rotate(Quaternionf.axisAngle(new Vector3f(0, 1, 0), -Mathf.PI * 0.25f + angle * 0.1f)));
        camera.setRotation(-Mathf.PI * 0.75f + angle * 0.1f, -Mathf.toRadians(20));
        
        // render scene 
        renderer.begin(camera);
        renderer.draw(terrainModel, new Vector3f(0, 0, 0), new Quaternionf());
        for (int i = 1; i < cubePositions.length + 1; i++) {
            // rotate each cube slightly differently
            renderer.draw(cubeModel, cubePositions[i - 1], 
                    Quaternionf.axisAngle(new Vector3f(0, 1, 0), angle * 0.4f * i / 10f).multiply(
                            Quaternionf.axisAngle(new Vector3f(1, 0, 0), angle * 0.7f * i / 10f).multiply(
                                    Quaternionf.axisAngle(new Vector3f(0, 0, 1), angle * 0.9f * i / 10f)))); 
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
