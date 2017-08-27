package oasis.sample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.BlendMode;
import oasis.graphics.ColorRgba;
import oasis.graphics.CullMode;
import oasis.graphics.Shader;
import oasis.graphics.model.Mesh;
import oasis.graphics.model.MeshData;
import oasis.math.MathUtil;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private Mesh heightmap, water; 
    
    private float angle = 0.0f; 
    private int time = 0; 
    
    private float freq = 1 / 6.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long maxRes = 128; 
    private long res = 128; 
    
    private float height = 3f; 
    
    private Heightmap htmap; 
    private Future<MeshData> meshData; 
    private ExecutorService executor = Executors.newCachedThreadPool(); 
    
    private String vSource = ""
    + "#version 120\n "
    + ""
    + "attribute vec3 aPosition; "
    + "attribute vec3 aNormal; "
    + "attribute vec4 aColor; "
    + ""
    + "uniform mat4 Model; "
    + "uniform mat4 View; "
    + "uniform mat4 Projection; "
    + ""
    + "varying vec4 vColor; "
    + "varying vec3 vNormal; "
    + "varying vec3 vModelPos; "
    + "" 
    + "void main() "
    + "{ "
    + "  vColor = aColor; "
    + "  vNormal = normalize(Model * vec4(aNormal, 0)).xyz; "
    + "  gl_Position = Projection * View * Model * vec4(aPosition, 1.0); "
    + "  vec4 tmp = View * Model * vec4(aPosition, 1.0); "
    + "  vModelPos = tmp.xyz / tmp.w; "
    + "}";
    private String fSource = ""
    + "#version 120\n "
    + ""
    + "varying vec4 vColor; "
    + "varying vec3 vNormal; "
    + "varying vec3 vModelPos; "
    + ""
    + "uniform vec3 LightDirection; "
    + "uniform vec3 ViewPos; "
    + "uniform float Shininess; "
    + "uniform float Brightness; "
    + ""
    + "void main() "
    + "{ "
    + "  vec3 normal = normalize(vNormal); "
    + "  vec3 lightDir = normalize(-LightDirection); "
    + "  float diffuse = max(dot(lightDir, normal), 0.0); "
    + "  "
    + "  vec3 viewDir = normalize(ViewPos - vModelPos); "
    + "  vec3 halfDir = normalize(lightDir + viewDir); "
    + "  float specAngle = max(dot(halfDir, normal), 0.0); "
    + "  float specular = Brightness * pow(specAngle, Shininess); "
    + "  "
    + "  gl_FragColor = vec4(vec3(0.2 + diffuse + specular), 1.0) * vColor;\n "
    + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        shader = graphics.createShader(vSource, fSource);  
        
        htmap = new Heightmap();
        htmap.setFlat(true, 0.65f);
        water = new Mesh(graphics); 
        htmap.genMeshData(new Vector3(-10, 0, -10), new Vector3(10, height, 10), 1, 1, octs, freq, pers).apply(water);
        
        heightmap = new Mesh(graphics); 
        htmap.setFlat(false, 0);
        htmap.genMeshData(new Vector3(-10, 0, -10), new Vector3(10, height, 10), (int) res, (int) res, octs, freq, pers).apply(heightmap);
        
        res = maxRes; 
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }

        angle += 2f / 60.0f; 
        time++; 
        
        if (meshData == null && time % (60 * 10) == 0) {
            System.out.println("New task");
            meshData = executor.submit(new Callable<MeshData>() {
                @Override
                public MeshData call() throws Exception {
                    return new Heightmap().genMeshData(new Vector3(-10, 0, -10), new Vector3(10, height, 10), (int) res, (int) res, octs, freq, pers); 
                }
            }); 
        }
        
        if (meshData != null && meshData.isDone()) {
            System.out.println("Done!"); 
            try {
                meshData.get().apply(heightmap);
                meshData = null; 
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRender() {
        graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        graphics.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        graphics.setShader(shader);
        graphics.setCullMode(CullMode.NONE);
        
        Vector3 pos = new Vector3();
        float scale = 8f; //18.0f;
        float time = 5.0f * angle;
        pos.setX(scale * MathUtil.cos(time) * MathUtil.cos(time) * MathUtil.cos(time));
        pos.setY(2.6f);
        pos.setZ(scale * MathUtil.sin(time) * MathUtil.sin(time) * MathUtil.sin(time));
//        System.out.println(pos);
        // view
        Matrix4 m;
        m = Matrix4.createLookAt(pos, new Vector3(0, 2.5f, 0), new Vector3(0, 1, 0));
        shader.setMatrix4("View", m);
        shader.setVector3("ViewPos", pos);
        
        // light direction
        time = -20.0f * angle;
        scale = 2.0f;
//        pos.setX(10);
//        pos.setY(-10);
//        pos.setZ(-10);
        pos = new Vector3(); 
        pos.setX(scale * MathUtil.cos(time));
        pos.setZ(1.0f);
        pos.setY(-Math.abs(scale * MathUtil.sin(time)));
        
        shader.setVector3("LightDirection", pos); 
        
        // projection 
        m = Matrix4.createPerspective(60.0f, (float) display.getAspectRatio(), 0.1f, 1000.0f);
        shader.setMatrix4("Projection", m);
        
        // model
        m = Matrix4.createIdentity();
//        m.multiplySelf(Matrix4.createRotationY(angle * 5.0f)); 
        shader.setMatrix4("Model", m);
        shader.setFloat("Shininess", 200.0f);
        shader.setFloat("Brightness", 0.05f);
        heightmap.draw();
        shader.setFloat("Shininess", 300.0f);
        shader.setFloat("Brightness", 1.0f);
        water.draw(); 
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
