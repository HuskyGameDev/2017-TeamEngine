package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.graphics.FrameBuffer;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.model.Mesh;
import oasis.graphics.sprite.SpriteBatch;
import oasis.math.FastMath;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private Mesh heightmap, water; 
    private FrameBuffer fbo; 
    private Texture2D screenTex, depthTex; 
    private SpriteBatch sb; 
    
    private float angle = 0.0f; 
    private int time = 0; 
    
    private float freq = 1 / 13.0f; 
    private float pers = 0.4f; 
    private int octs = 10; 
    private long res = 2; 
    private long maxRes = 512; 
    
    private float height = 7f; 
    
    private SampleHeightmap htmap; 
    
    private String vSource = ""
    + "#version 120\n "
    + ""
    + "attribute vec3 aPosition; "
    + "attribute vec3 aNormal; "
    + "attribute vec4 aColor; "
    + ""
    + "uniform mat4 uModel; "
    + "uniform mat4 uView; "
    + "uniform mat4 uProjection; "
    + ""
    + "varying vec4 vColor; "
    + "varying vec3 vNormal; "
    + "varying vec3 vModelPos; "
    + "" 
    + "void main() "
    + "{ "
    + "  vColor = aColor; "
    + "  vNormal = normalize((uModel * vec4(aNormal, 0)).xyz); "
    + "  gl_Position = uProjection * uView * uModel * vec4(aPosition, 1.0); "
    + "  vec4 tmp = uView * uModel * vec4(aPosition, 1.0); "
    + "  vModelPos = tmp.xyz / tmp.w; "
    + "}";
    private String fSource = ""
    + "#version 120\n "
    + ""
    + "varying vec4 vColor; "
    + "varying vec3 vNormal; "
    + "varying vec3 vModelPos; "
    + ""
    + "uniform vec3 uLightDirection; "
    + "uniform vec3 uViewPos; "
    + "uniform float uShininess; "
    + "uniform float uBrightness; "
    + ""
    + "void main() "
    + "{ "
    + "  vec3 normal = normalize(vNormal); "
    + "  vec3 lightDir = normalize(-uLightDirection); "
    + "  float diffuse = max(dot(lightDir, normal), 0.0); "
    + "  "
    + "  vec3 viewDir = normalize(uViewPos - vModelPos); "
    + "  vec3 halfDir = normalize(lightDir + viewDir); "
    + "  float specAngle = max(dot(halfDir, normal), 0.0); "
    + "  float specular = uBrightness * pow(specAngle, uShininess); "
    + "  "
    + "  gl_FragColor = vec4(vec3(0.2 + diffuse + specular), 1.0) * vColor;\n "
    + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        sb = new SpriteBatch(graphics); 
        
        shader = graphics.createShader(vSource, fSource);  
        
        htmap = new SampleHeightmap();
        htmap.setFlat(true, 0.65f);
        water = new Mesh(graphics); 
        htmap.genMesh(water, new Vector3(-10, 0, -10), new Vector3(10, height, 10), 1, 1, octs, freq, pers);
        
        heightmap = new Mesh(graphics); 
        htmap.setFlat(false, 0);
        htmap.genMesh(heightmap, new Vector3(-10, 0, -10), new Vector3(10, height, 10), (int) res, (int) res, octs, freq, pers);
    
        fbo = graphics.createFrameBuffer(2048, 1024); 
        screenTex = graphics.createTexture2D(TextureFormat.RGBA8, fbo.getWidth(), fbo.getHeight()); 
        screenTex.setFilters(MinFilter.LINEAR_MIPMAP_LINEAR, MagFilter.LINEAR);
        screenTex.setMipmaps(1);
        depthTex = graphics.createTexture2D(TextureFormat.DEPTH24, fbo.getWidth(), fbo.getHeight()); 
        
        fbo.setColorTexture(0, screenTex);
        fbo.setDepthTexture(depthTex);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }

        angle += 2f / 60.0f; 
        time++; 
        
        if (time % (60 / 6) == 0) { 
            res <<= 1; 
            
            if (res > (maxRes << 30)) { 
                htmap = new SampleHeightmap();
                res = 1; 
            }
            else if (res > maxRes) {} 
            else { 
//                System.out.println("Resolution: " + res + ", Octaves: " + octs + ", Frequency: " + freq + ", Persistance: " + pers);
                htmap.genMesh(heightmap, new Vector3(-10, 0, -10), new Vector3(10, height, 10), (int) res, (int) res, octs, freq, pers);
            }
        }
    }

    @Override
    public void onRender() {
    	graphics.setFrameBuffer(fbo);
    	
        graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        graphics.setShader(shader);
        
        Vector3 pos = new Vector3();
        float scale = 8f; //18.0f;
        float time = 5.0f * angle;
        pos.setX(scale * FastMath.cos(time) * FastMath.cos(time) * FastMath.cos(time));
        pos.setY(10.0f);
        pos.setZ(scale * FastMath.sin(time) * FastMath.sin(time) * FastMath.sin(time));
//        System.out.println(pos);
        // view
        Matrix4 m;
        m = Matrix4.createLookAt(pos, new Vector3(0, 6.0f, 0), new Vector3(0, 1, 0));
        shader.setMatrix4("uView", m);
        shader.setVector3("uViewPos", pos);
        
        // light direction
        time = -20.0f * angle;
        scale = 2.0f;
//        pos.setX(10);
//        pos.setY(-10);
//        pos.setZ(-10);
        pos = new Vector3(); 
        pos.setX(scale * FastMath.cos(time));
        pos.setZ(1.0f);
        pos.setY(-Math.abs(scale * FastMath.sin(time)));
        
        shader.setVector3("uLightDirection", pos); 
        
        // projection 
        m = Matrix4.createPerspective(60.0f, (float) display.getAspectRatio(), 0.1f, 1000.0f);
        shader.setMatrix4("uProjection", m);
        
        // model
        m = Matrix4.createIdentity();
//        m.multiplySelf(Matrix4.createRotationY(angle * 5.0f)); 
        shader.setMatrix4("uModel", m);
        shader.setFloat("uShininess", 200.0f);
        shader.setFloat("uBrightness", 0.0f);
        heightmap.draw();
        shader.setFloat("uShininess", 300.0f);
        shader.setFloat("uBrightness", 1.0f);
        water.draw(); 
        
        graphics.setFrameBuffer(null);
        graphics.clearScreen(new ColorRgba(0.0f, 0.8f, 1.0f, 1.0f));
        
        sb.begin(); 
        sb.draw(screenTex, 20, 20, graphics.getWidth() - 40, graphics.getHeight() - 40);
        sb.end(); 
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
