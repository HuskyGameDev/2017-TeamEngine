package oasis.sample3;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.TextureLoader;
import oasis.graphics.WrapMode;
import oasis.graphics.sprite.SpriteBatch;

public class Sample3App extends Application {

    private static final GameLogger log = new GameLogger(Sample3App.class); 
    
    private static final int MAX_SPRITES = 10; 
    
    private SpriteBatch sb; 
    private TextureLoader textureLoader; 
    
    private Texture2D[] textures; 
    private float[][] args; 
    private int[] inds; 
    
    @Override
    public void onInit() {
        sb = new SpriteBatch(graphics); 
        textureLoader = new TextureLoader(graphics); 
        
        textures = new Texture2D[2]; 
        int[] pixels = new int[16 * 16]; 
        args = new float[MAX_SPRITES][4]; 
        inds = new int[MAX_SPRITES]; 
        
        for (int i = 0; i < textures.length; i++) {
            for (int j = 0; j < pixels.length; j++) { 
                pixels[j] = (int)(Math.random() * 0x1000000) << 8 | 0xFF; // RGB | A 
            }
            
            textures[i] = graphics.createTexture2D(TextureFormat.RGBA8, 16, 16); 
            textures[i].setWrap(WrapMode.REPEAT, WrapMode.REPEAT);
            textures[i].setFilter(MinFilter.NEAREST, MagFilter.NEAREST);
            textures[i].setIntPixels(pixels);
        }
        
        textures[0].dispose(); 
        textures[0] = textureLoader.get("assets/textures/test.png"); 
        textures[0].setFilter(MinFilter.LINEAR_MIPMAP_LINEAR, MagFilter.LINEAR);
        textures[0].setWrap(WrapMode.REPEAT, WrapMode.REPEAT);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) engine.stop(); 
        
        for (int i = 0; i < args.length; i++) {
            args[i][0] = (float)(Math.random() * graphics.getWidth()); 
            args[i][1] = (float)(Math.random() * graphics.getHeight()); 
            args[i][2] = (float)(Math.random() * (graphics.getWidth() - args[i][0])); 
            args[i][3] = (float)(Math.random() * (graphics.getHeight() - args[i][1])); 
            inds[i] = (int)(Math.random() * textures.length); 
        }
    }

    @Override
    public void onRender() {
        sb.begin();
        for (int i = 0; i < MAX_SPRITES; i++) {
            sb.draw(textures[inds[i]], args[i][0], args[i][1], args[i][2], args[i][3]);
        }
        sb.end(); 
    }

    @Override
    public void onExit() {}
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());

        Config cfg = new Config();
        cfg.engine = Jogl3Engine.class;
        cfg.fps = 60.0f;
        cfg.ups = 1.0f;

        Application app = new Sample3App();
        app.start(cfg);
    }

}
