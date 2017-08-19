package oasis.sample2;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.BufferUsage;
import oasis.graphics.ColorRgba;
import oasis.graphics.IndexBuffer;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;
import oasis.graphics.WrapMode;

public class Sample2App extends Application {

    private static final GameLogger log = new GameLogger(Sample2App.class); 
    
    private String vSource = ""
        + "#version 120\n "
        + "attribute vec2 aPosition; "
        + "attribute vec3 aColor; "
        + "attribute vec2 aTexCoord; "
        + "varying vec3 vColor; "
        + "varying vec2 vTexCoord; "
        + "void main() { "
        + "  vColor = aColor; "
        + "  vTexCoord = aTexCoord; "
        + "  gl_Position = vec4(aPosition, 0.0, 1.0); "
        + "}";
    private String fSource = ""
        + "#version 120\n "
        + "varying vec3 vColor; "
        + "varying vec2 vTexCoord; "
        + "uniform float uLight = 1.0; "
        + "uniform sampler2D uTexture; "
        + "void main() { "
        + "  gl_FragColor = uLight * vec4(vColor, 1.0) * vec4(texture2D(uTexture, vTexCoord.xy).xyz, 1.0);\n "
        + "}";
    
    private VertexArray boxVao; 
    private Shader shader; 
    private Texture2D texture; 
    
    @Override
    public void onInit() {
        boxVao = graphics.createVertexArray(); 
        shader = graphics.createShader(vSource, fSource); 
        
        VertexBuffer vbo = graphics.createVertexBuffer(VertexFormat.POSITION_3_COLOR_4_TEXTURE_2, BufferUsage.STATIC); 
        IndexBuffer ibo = graphics.createIndexBuffer(BufferUsage.STATIC); 
        
        vbo.setData(new float[] {
                -0.5f, -0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,   0.0f, 0.0f, 
                 0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,   1.0f, 0.0f, 
                 0.5f,  0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1.0f,   1.0f, 1.0f, 
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 1.0f, 1.0f,   0.0f, 1.0f, 
        });
        
        ibo.setData(new int[] {
                0, 1, 2,  
                0, 2, 3, 
        });
        
        boxVao.setVertexBuffer(vbo);
        boxVao.setIndexBuffer(ibo);
        
        texture = graphics.createTexture2D(TextureFormat.RGBA8, 16, 16); 
        texture.setWrapModes(WrapMode.REPEAT, WrapMode.REPEAT);
        texture.setFilters(MinFilter.LINEAR, MagFilter.LINEAR);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) engine.stop(); 
        
        boxVao.getVertexBuffer(0).setData(new float[] {
                -(float)Math.random(), -(float)Math.random(), 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,   0.0f, 0.0f, 
                (float)Math.random(), -(float)Math.random(), 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,   1.0f, 0.0f, 
                (float)Math.random(), (float)Math.random(), 0.0f,   0.0f, 0.0f, 1.0f, 1.0f,   1.0f, 1.0f, 
                -(float)Math.random(),  (float)Math.random(), 0.0f,   1.0f, 1.0f, 1.0f, 1.0f,   0.0f, 1.0f, 
        });
        
        int[] pixels = new int[16 * 16]; 
        for (int i = 0; i < pixels.length; i++) { 
            pixels[i] = (int)(Math.random() * 0x1000000) << 8 | 0xFF; // RGB | A 
        }
        
        texture.setPixelsRgba(pixels);
    }

    @Override
    public void onRender() {
        graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        
        shader.setFloat("uLight", 0.5f);
        shader.setInt("uTexture", 1);
        
        graphics.setShader(shader);
        graphics.setTexture(1, texture);
        graphics.setVertexArray(boxVao);
        graphics.drawElements(Primitive.TRIANGLE_LIST, 0, boxVao.getIndexBuffer().getIndexCount());
    }

    @Override
    public void onExit() {}

    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());

        Config cfg = new Config();
        cfg.engine = Jogl3Engine.class;
        cfg.fps = 60.0f;
        cfg.ups = 2.0f;

        Application app = new Sample2App();
        app.start(cfg);
    }

}
