package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.BlendMode;
import oasis.graphics.ColorRgba;
import oasis.graphics.FrontFace;
import oasis.graphics.Shader;
import oasis.graphics.model.Mesh;
import oasis.math.Mathf;
import oasis.math.Matrix4f;
import oasis.math.Vector3f;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private Mesh heightmap, water; 
    
    private float angle = 0.0f; 
    
    private float freq = 1 / 6.0f; 
    private float pers = 0.45f; 
    private int octs = 10; 
    private long res = 128; 
    
    private float height = 3f; 
    
    private Heightmap htmap; 
    
    private String vSource = ""
    + "#version 120\n "
    + ""
    + "attribute vec3 aPosition; "
    + "attribute vec3 aNormal; "
    + "attribute vec4 aColor; "
    + ""
    + "uniform mat4 Model; // no scaling in this example, so no need for a normal matrix \n"
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
    + "  vNormal = normalize(mat3(Model) * aNormal); "
    + "  gl_Position = Projection * View * Model * vec4(aPosition, 1.0); "
    + "  "
    + "  vec4 tmp = Model * vec4(aPosition, 1.0); "
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
    + "float Diffuse(vec3 normal, vec3 lightDir) "
    + "{"
    + "  normal = normalize(normal); "
    + "  lightDir = normalize(lightDir); "
    + "  "
    + "  float diffuse = clamp(dot(lightDir, normal), 0.0, 1.0); "
    + "  return diffuse; "
    + "}"
    + ""
    + "float Specular(vec3 normal, vec3 lightDir, vec3 camDir, float power) "
    + "{"
    + "  normal = normalize(normal); "
    + "  lightDir = normalize(lightDir); "
    + "  camDir = normalize(camDir); "
    + "  vec3 halfVec = normalize(camDir + lightDir); "
    + "  "
    + "  float specular = pow(abs(dot(-halfVec, normal)), power); "
    + "  return specular; "
    + "}"
    + ""
    + "void main() "
    + "{ "
    + "  vec3 camDir = normalize(vModelPos - ViewPos); "
    + "  vec3 normal = normalize(vNormal); "
    + "  vec3 lightDir = normalize(LightDirection); "
    + "  "
    + "  float diffuse = Diffuse(normal, lightDir); "
    + "  float specular = Brightness * Specular(normal, lightDir, camDir, Shininess); "
    + "  "
    + "  gl_FragColor = vec4(vec3(0.2 + 0.8 * diffuse + specular), 1.0) * vColor;\n "
    + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        shader = graphics.createShader(vSource, fSource);  
        
        float offset = 0.015f; 
        
        htmap = new Heightmap();
        htmap.setFlat(true);
        water = new Mesh(graphics); 
        htmap.genMeshData(new Vector3f(-10, height * 0.65f - offset, 10), new Vector3f(10, height * 0.65f + offset, -10), (int) res, (int) res, 5, freq * 10, 0.9f).apply(water);
        
        heightmap = new Mesh(graphics); 
        htmap.setFlat(false);
        htmap.genMeshData(new Vector3f(-10, 0, 10), new Vector3f(10, height, -10), (int) res, (int) res, octs, freq, pers).apply(heightmap);
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
        graphics.clearScreen(new ColorRgba(0.9f, 0.9f, 0.9f, 1.0f));
        graphics.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        graphics.setShader(shader);
        graphics.setFrontFace(FrontFace.CCW);
        
        Vector3f pos = new Vector3f(0.1f, 20, 0.1f);
        float scale = 10f; //18.0f;
        float time = 1.0f * angle;
        pos.setX(scale * Mathf.cos(Mathf.toRadians(time)) * Mathf.cos(Mathf.toRadians(time)) * Mathf.cos(Mathf.toRadians(time)));
        pos.setY(4.6f);
        pos.setZ(scale * Mathf.sin(Mathf.toRadians(time)) * Mathf.sin(Mathf.toRadians(time)) * Mathf.sin(Mathf.toRadians(time)));
        
        // view
        Matrix4f m;
        m = Matrix4f.lookAt(pos, new Vector3f(0, 2.5f, 0), new Vector3f(0, 1, 0));
        shader.setMatrix4f("View", m);
        shader.setVector3f("ViewPos", pos);
        
        // light direction
        setSunlight(); 
        
        // projection 
        m = Matrix4f.perspective(Mathf.toRadians(60.0f), (float) display.getAspectRatio(), 0.1f, 1000.0f);
        shader.setMatrix4f("Projection", m);
        
        // model
        m = Matrix4f.identity();
        shader.setMatrix4f("Model", m);
        
        // draw terrain 
        shader.setFloat("Shininess", 200.0f);
        shader.setFloat("Brightness", 0.0f);
        heightmap.draw();
        // draw water 
        shader.setFloat("Shininess", 100.0f);
        shader.setFloat("Brightness", 1.0f);
        water.draw(); 
    }
    
    private void setSunlight() {
    	Vector3f position = new Vector3f(); 
    	position.x = 10 * Mathf.cos(0.1f * angle); 
    	position.y = 10 * Mathf.sin(0.1f * angle); 
    	position.z = 10; 
    	
    	shader.setVector3f("LightDirection", position.multiplySelf(-1).normalizeSelf());
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
