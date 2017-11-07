package oasis.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.graphics.ColorRgba;
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
    private Mesh heightmap, water, cube; 
    private Material heightmapMaterial, waterMaterial, cubeMaterial, sunMaterial, lightMaterial1, lightMaterial2; 
    private Model terrainModel, cubeModel, sunModel, lightModel1, lightModel2; 
    
    // current angle 
    private float angle = 0.0f; 
    
    // heightmap settings 
    private float freq = 1 / 16.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long res = 64; 
    
    // height of water 
    private float height = 4.5f; 
    
    // positions of the cubes 
    private Vector3f[] cubePositions; 

    @Override
    public void onInit() {
        Oasis.display.setResizable(true);
        Oasis.display.setSize(800, 400);
        Oasis.mouse.setCursorVisible(false); 
        
        renderer = new ModelRenderer(); 
        camera = new PerspectiveCamera(800, 600, 70.0f, 0.1f, 1000.0f); 
        camera.setPosition(new Vector3f(0, 5, 0));
        
        // generate water and terrain
        float offset = 0.015f; 
        Heightmap htmap = new Heightmap();
        // water
        htmap.setFlat(true);
        water = new Mesh(); 
        water.setFrontFace(FrontFace.CCW); 
        htmap.genMeshData(new Vector3f(-10, height * 0.65f - offset, -10), new Vector3f(10, height * 0.65f + offset, 10), (int) res, (int) res, 5, freq * 10, 0.9f).apply(water);
        // terrain 
        heightmap = new Mesh(); 
        heightmap.setFrontFace(FrontFace.CCW);
        htmap.setFlat(false);
        htmap.genMeshData(new Vector3f(-10, 0, -10), new Vector3f(10, height, 10), (int) res, (int) res, octs, freq, pers).apply(heightmap);
        
        // water material, translucent and shiny
        waterMaterial = new Material(); 
        waterMaterial.renderQueue = RenderQueue.TRANSLUCENT; 
        waterMaterial.diffuseColor = new Vector4f(0.45f, 0.55f, 0.85f, 0.7f); 
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
        List<Vector3f> posList = new ArrayList<>(); 
        Random rand = new Random(); 
        for (int i = 0; i < 10; i++) {
            Vector3f v = new Vector3f(
                    rand.nextInt(20) - 10,
                    0,
                    rand.nextInt(20) - 10); 
            
            int height = rand.nextInt(10) + 10; 
            
            for (int j = 0; j < height; j++) {
                posList.add(new Vector3f(v).setY(j * 0.5f)); 
            }
        }
        
        cubePositions = new Vector3f[posList.size()]; 
        posList.toArray(cubePositions); 
        
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
        Vector4f color = new Vector4f(1.0f, 1.0f); 
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
        cube = new Mesh();
        cube.setFrontFace(FrontFace.BOTH); // some faces are not CCW and I don't have time to fix it right now 
        cube.setColors(colors);
        cube.setPositions(positions);
        cube.setNormals(normals);
        cube.setIndices(indices);
        
        // shiny metallic cube material 
        cubeMaterial = new Material(); 
        cubeMaterial.diffuseColor = new Vector4f(0.5f, 1.0f); 
        cubeMaterial.specularColor = new Vector4f(1, 1, 1, 1); 
//        cubeMaterial.emissiveColor = new Vector4f(0.5f, 0.5f, 1, 1); 
        cubeMaterial.specularPower = 20.0f; 
        
        // sun material 
        sunMaterial = new Material(); 
        sunMaterial.diffuseColor = new Vector4f(0, 1); 
        sunMaterial.emissiveColor = new Vector4f(1, 1, 1, 1); 
        
        lightMaterial1 = new Material(); 
        lightMaterial1.diffuseColor = new Vector4f(0, 1); 
        lightMaterial1.emissiveColor = new Vector4f(1, 0.5f, 0.5f, 1); 
        
        lightMaterial2 = new Material(); 
        lightMaterial2.diffuseColor = new Vector4f(0, 1); 
        lightMaterial2.emissiveColor = new Vector4f(0.5f, 0.5f, 1, 1); 
        
        // put mesh into a model 
        cubeModel = new Model(); 
        cubeModel.add(cube, cubeMaterial);
        
        sunModel = new Model(); 
        sunModel.add(cube, sunMaterial);
        
        lightModel1 = new Model(); 
        lightModel1.add(cube, lightMaterial1);
        
        lightModel2 = new Model(); 
        lightModel2.add(cube, lightMaterial2);
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
        float speed = 4; 
        
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
        sunPos.multiplySelf(20); 
        
        Oasis.graphics.clearScreen(new ColorRgba(new Vector4f(0.8f, 0.9f, 1.0f, 1.0f).multiply(new Vector3f(0, 1, 0).dot(sunPos.normalize())))); 
        
        // render scene 
        renderer.begin(camera);
        
        Vector3f lightPos = new Vector3f(); 
        lightPos.x = Mathf.cos(angle * 0.2f);
        lightPos.z = Mathf.sin(angle * 0.2f); 
        lightPos.multiplySelf(8); 
        lightPos.y = 5; 
        
        renderer.addLight(new PointLight(new Vector3f(0.8f, 0.4f, 0.4f), lightPos, 10));
        renderer.draw(lightModel1, lightPos, new Quaternionf());
        
        lightPos.x *= -1; 
        lightPos.z *= -1; 
        
        renderer.addLight(new PointLight(new Vector3f(0.4f, 0.4f, 0.8f), lightPos, 10));
        renderer.draw(lightModel2, lightPos, new Quaternionf());
        
        renderer.addLight(new PointLight(new Vector3f(0.8f, 0.8f, 0.8f), sunPos, 80));
        renderer.draw(sunModel, sunPos, new Quaternionf());
        
        renderer.draw(terrainModel, new Vector3f(0, 0, 0), new Quaternionf());
        for (int i = 1; i < cubePositions.length + 1; i++) {
            // rotate each cube slightly differently
            renderer.draw(cubeModel, cubePositions[i - 1], new Quaternionf()); 
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
