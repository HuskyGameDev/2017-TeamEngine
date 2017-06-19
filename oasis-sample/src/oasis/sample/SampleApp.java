package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.JoglEngine;
import oasis.graphics.ColorRgba;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.vertex.BufferUsage;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexLayout;
import oasis.math.FastMath;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private VertexBuffer heightMapBuffer, waterBuffer; 
    private VertexArray heightMapVao, waterVao; 
    
    private float angle = 0.0f; 
    
  private String vSource = ""
  + "#version 120\n "
  + "attribute vec3 aPosition; "
  + "attribute vec3 aNormal; "
  + "attribute vec4 aColor; "
  + "uniform mat4 uModel; "
  + "uniform mat4 uView; "
  + "uniform mat4 uProjection; "
  + "varying vec4 vColor; "
  + "varying vec3 vNormal; " 
  + "void main() "
  + "{ "
  + "  vColor = aColor; "
  + "  vNormal = normalize((uModel * vec4(aNormal, 0)).xyz); "
  + "  gl_Position = uProjection * uView * uModel * vec4(aPosition, 1.0); "
  + "}";
private String fSource = ""
  + "#version 120\n "
  + "varying vec4 vColor; "
  + "varying vec3 vNormal; "
  + "uniform vec3 uLightDirection; "
  + "void main() "
  + "{ "
  + "  float scale = 0.4 + 0.6 * clamp(dot(-normalize(uLightDirection), normalize(vNormal)), 0.0, 1.0); "
  + "  gl_FragColor = vec4(vec3(scale), 1.0) * vColor;\n "
  + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        shader = graphics.getResourceFactory().createShaderFromSource(vSource, fSource); 
        
        HeightMap htmap = new HeightMap(256, 256);
        
        heightMapBuffer = graphics.getResourceFactory().createVertexBuffer(VertexLayout.LAYOUT_POSITION_3_NORMAL_3_COLOR_4); 
        heightMapBuffer.setUsage(BufferUsage.STATIC);
        heightMapBuffer.setVertices(htmap.genVertices(new Vector3(-10, 0, -10), new Vector3(10, 8, 10)));
        
        htmap = new HeightMap(1, 1);
        htmap.setFlat(true, 0.5f);
        
        waterBuffer = graphics.getResourceFactory().createVertexBuffer(VertexLayout.LAYOUT_POSITION_3_NORMAL_3_COLOR_4); 
        waterBuffer.setUsage(BufferUsage.STATIC);
        waterBuffer.setVertices(htmap.genVertices(new Vector3(-10, 0, -10), new Vector3(10, 8, 10)));
        
        heightMapVao = graphics.getResourceFactory().createVertexArray(new VertexBuffer[] { heightMapBuffer }); 
        
        waterVao = graphics.getResourceFactory().createVertexArray(new VertexBuffer[] { waterBuffer });  
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }

        angle += 1f / 60.0f; 
    }

    @Override
    public void onRender() {
        graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        graphics.setShader(shader);
        
        Vector3 pos = new Vector3();
        float scale = 12.0f;
        float time = angle * 5.0f;
        pos.setX(scale * FastMath.cos(time));
        pos.setY(10.2f);
        pos.setZ(scale * FastMath.sin(time));
        
        // view
        Matrix4 m;
        m = Matrix4.createLookAt(pos, new Vector3(0, 0.0f, 0), new Vector3(0, 1, 0));
        shader.setMatrix4("uView", m);
        
        // light direction
        time = angle * 80.0f;
        pos.setX(scale * FastMath.cos(time));
        pos.setY(0f);
        pos.setZ(scale * FastMath.sin(time));
        shader.setVector3("uLightDirection", pos); 
        
        // projection 
        m = Matrix4.createPerspective(60.0f, (float) display.getAspectRatio(), 0.1f, 1000.0f);
        shader.setMatrix4("uProjection", m);
        
        // model
        m = Matrix4.createIdentity();
        shader.setMatrix4("uModel", m);
        graphics.setVertexArray(heightMapVao);
        graphics.drawArrays(Primitive.TRIANGLE_LIST, 0, heightMapVao.getVertexBuffer(0).getVertices().length / 3);
        
        m = Matrix4.createIdentity();
        shader.setMatrix4("uModel", m);
        graphics.setVertexArray(waterVao);
        graphics.drawArrays(Primitive.TRIANGLE_LIST, 0, waterVao.getVertexBuffer(0).getVertices().length / 3);
    }
    
    @Override
    public void onExit() {
        
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        Config cfg = new Config();
        cfg.engine = JoglEngine.class;
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
