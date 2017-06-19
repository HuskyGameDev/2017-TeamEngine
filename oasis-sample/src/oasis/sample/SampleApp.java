package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.JoglEngine;
import oasis.graphics.ColorRgba;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.jogl.JoglShader;
import oasis.graphics.vertex.BasicVertex;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexLayout;
import oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private VertexBuffer heightMapBuffer, waterBuffer; 
    private VertexArray heightMapVao, waterVao; 
    
  private String vSource = ""
  + "#version 120\n "
  + "attribute vec3 inPosition0; "
  + "attribute vec3 inNormal0; "
  + "attribute vec4 inColor0; "
  + "uniform mat4 Model; "
  + "uniform mat4 View; "
  + "uniform mat4 Projection; "
  + "varying vec4 fragColor; "
  + "varying vec3 fragNormal; " 
  + "void main() "
  + "{ "
  + "  fragColor = inColor0; "
  + "  fragNormal = normalize((Model * vec4(inNormal0, 0)).xyz); "
  + "  gl_Position = Projection * View * vec4(inPosition0, 1.0); "
  + "}";
private String fSource = ""
  + "#version 120\n "
  + "varying vec4 fragColor; "
  + "varying vec3 fragNormal; "
//  + "uniform vec3 LightDirection; "
  + "void main() "
  + "{ "
//  + "  float scale = 0.8 + 1.0 * clamp(dot(-normalize(LightDirection), normalize(fragNormal)), 0.0, 1.0); "
  + "  gl_FragColor = vec4(vec3(1.0), 1.0) * fragColor;\n "
  + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
        
        shader = new Shader(graphics, vSource, fSource); 
        
        HeightMap htmap = new HeightMap(256, 256);
        
        heightMapBuffer = new VertexBuffer(graphics, VertexLayout.LAYOUT_POSITION_0_3_NORMAL_0_3_COLOR_0_4); 
        BasicVertex[] verts = htmap.genVertices(new Vector3(-10, 0, -10), new Vector3(10, 4, 10)); 
        System.out.println(verts);
        heightMapBuffer.setVertices(verts);
        
        htmap = new HeightMap(256, 256);
        htmap.setFlat(true, 0.5f);
        
        waterBuffer = new VertexBuffer(graphics, VertexLayout.LAYOUT_POSITION_0_3_NORMAL_0_3_COLOR_0_4); 
        waterBuffer.setVertices(htmap.genVertices(new Vector3(-10, 0, -10), new Vector3(10, 4, 10)));
        
        heightMapVao = new VertexArray(graphics); 
        heightMapVao.setVertexBuffer(heightMapBuffer);
        
        waterVao = new VertexArray(graphics); 
        waterVao.setVertexBuffer(waterBuffer);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }

        JoglShader.angle += 1f / 60.0f; 
    }

    @Override
    public void onRender() {
        JoglShader.ratio = (float) display.getAspectRatio(); 
        
        graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        graphics.setShader(shader);
        
        // TODO position setup
        graphics.setVertexArray(heightMapVao);
        graphics.drawArrays(Primitive.TRIANGLE_LIST, 0, heightMapVao.getVertexBuffer(0).getVertices().length / 3);
        
        // TODO position setup
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
