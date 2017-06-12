package oasis.sample;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.JoglEngine;
import oasis.graphics.ColorRgba;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Shader;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Shader shader;
    private VertexBuffer htmapVerts, waterVerts; 
    private IndexBuffer htmapInds, waterInds; 
    private VertexArray htmapVao, waterVao; 
    
    private String vSource = ""
            + "#version 120\n "
            + "attribute vec3 Position; "
            + "attribute vec4 Color; "
            + "uniform mat4 Model; "
            + "uniform mat4 View; "
            + "uniform mat4 Projection; "
            + "varying vec4 FragColor; "
            + "void main() "
            + "{ "
            + "  FragColor = Color; "
            + "  gl_Position = Projection * View * Model * vec4(Position, 1.0); "
            + "}";
    private String fSource = ""
            + "#version 120\n "
            + "varying vec4 FragColor; "
            + "void main() "
            + "{ "
            + "  gl_FragColor = FragColor; "
            + "}";
    
    @Override
    public void onInit() {
        display.setResizable(true);
        display.setSize(800, 400);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }
    }
    
    @Override
    public void onRender() {
        graphics.setClearColor(new ColorRgba(0.4f, 0.7f, 1.0f, 1.0f));
        graphics.clearScreen();
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
