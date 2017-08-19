package oasis.sample4;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureLoader;
import oasis.graphics.sprite.SpriteBatch;

public class Sample4App extends Application {

    private static final GameLogger log = new GameLogger(Sample4App.class); 
    
    private SpriteBatch sb; 
    private TextureLoader textureLoader; 
    
    private Texture2D testTexture; 
    
    private float size = 1; 
    private float change = 1.01f; 
    
    @Override
    public void onInit() {
        sb = new SpriteBatch(graphics); 
        textureLoader = new TextureLoader(graphics); 
        
        // load texture
        testTexture = textureLoader.get("assets/textures/test.png"); 
        testTexture.setMipmaps(4);
        testTexture.setFilters(MinFilter.LINEAR_MIPMAP_LINEAR, MagFilter.LINEAR);
        
        display.setSize(900, 900);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) engine.stop(); 
        
        size *= change; 
        
        if (size > display.getWidth()) {
            change = 1.0f / change; 
        }
    }

    @Override
    public void onRender() {
        graphics.clearScreen(new ColorRgba(0.7f, 0.9f, 1.0f, 1.0f));
        
        float w = size; 
        float h = size / display.getAspectRatio(); 
        
        sb.begin();
        sb.draw(testTexture, display.getWidth() / 2 - w / 2, display.getHeight() / 2 - h / 2, w, h);
        sb.end(); 
    }

    @Override
    public void onExit() {}
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());

        Config cfg = new Config();
        cfg.engine = Jogl3Engine.class;
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;

        Application app = new Sample4App();
        app.start(cfg);
    }

}
