package oasis.sample2;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.graphics.BufferUsage;
import oasis.graphics.ColorRgba;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.Texture;
import oasis.graphics.Texture.MagFilter;
import oasis.graphics.Texture.MinFilter;
import oasis.graphics.Texture.WrapMode;
import oasis.graphics.Texture2D;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;

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
        boxVao = Oasis.graphics.createVertexArray(); 
        shader = Oasis.graphics.createShader(vSource, fSource); 
        
        VertexBuffer vbo = Oasis.graphics.createVertexBuffer(VertexFormat.POSITION_3_COLOR_4_TEXTURE_2, BufferUsage.STATIC); 
        IndexBuffer ibo = Oasis.graphics.createIndexBuffer(BufferUsage.STATIC); 
        
        vbo.setVertices(new float[] {
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
        
        texture = Oasis.graphics.createTexture2D(Texture.Format.RGBA8, 16, 16); 
        texture.setWrapModes(WrapMode.REPEAT, WrapMode.REPEAT);
        texture.setFilters(MinFilter.LINEAR, MagFilter.LINEAR);
    }

    @Override
    public void onUpdate(float dt) {
        if (Oasis.display.shouldClose()) Oasis.engine.stop(); 
        
        boxVao.getVertexBuffer(0).setVertices(new float[] {
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
        Oasis.graphics.clearScreen(new ColorRgba(0.6f, 0.8f, 1.0f, 1.0f));
        
        shader.setFloat("uLight", 0.5f);
        shader.setInt("uTexture", 1);
        
        Oasis.graphics.setShader(shader);
        Oasis.graphics.setTexture(1, texture);
        Oasis.graphics.setVertexArray(boxVao);
        Oasis.graphics.drawElements(Primitive.TRIANGLE_LIST, 0, boxVao.getIndexBuffer().getIndexCount());
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
