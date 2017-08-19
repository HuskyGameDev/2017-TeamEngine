package oasis.sample4;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.graphics.FrameBuffer;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.TextureLoader;
import oasis.graphics.sprite.SpriteBatch;

public class Sample4App extends Application {

    private static final GameLogger log = new GameLogger(Sample4App.class); 
    
    private SpriteBatch sb; 
    private TextureLoader textureLoader; 
    
    private Texture2D testTexture; 
    private Texture2D screenTexture; 
    
    private FrameBuffer screenBuffer; 
    
    @Override
    public void onInit() {
        sb = new SpriteBatch(graphics); 
        textureLoader = new TextureLoader(graphics); 
        
        // load texture
        testTexture = textureLoader.get("assets/textures/test.png"); 
        
        // create frame buffer
        screenBuffer = graphics.createFrameBuffer(graphics.getWidth(), graphics.getHeight()); 
        
        // set color texture 
        screenTexture = graphics.createTexture2D(TextureFormat.RGBA8, graphics.getWidth(), graphics.getHeight());
        screenBuffer.setColorTexture(0, screenTexture);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) engine.stop(); 
    }

    @Override
    public void onRender() {
    	graphics.setFrameBuffer(screenBuffer);
        graphics.clearScreen(new ColorRgba(0.7f, 0.9f, 1.0f, 1.0f));
        
        sb.begin();
        sb.draw(testTexture, 100, 200, 350, 350);
        sb.end(); 
        
        graphics.setFrameBuffer(null);
        graphics.clearScreen(new ColorRgba(0.0f, 0.9f, 1.0f, 1.0f));

        sb.begin();
        sb.draw(screenTexture, 100, 200, 300, 370);
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
