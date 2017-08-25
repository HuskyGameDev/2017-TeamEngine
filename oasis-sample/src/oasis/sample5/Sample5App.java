package oasis.sample5;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.core.jogl.Jogl3Engine;
import oasis.graphics.BlendMode;
import oasis.graphics.ColorRgba;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.RenderTarget;
import oasis.graphics.Texture;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureLoader;
import oasis.graphics.sprite.SpriteBatch;

public class Sample5App extends Application {

    private static final GameLogger log = new GameLogger(Sample5App.class); 
    
    private SpriteBatch sb; 
    private TextureLoader textureLoader; 
    
    private Texture2D testTexture; 
    private RenderTarget rt; 
    
    private float camX = 0, camY = 0; 
    private float angle = 0; 
    
    @Override
    public void onInit() {
        display.setResizable(true);
        
        sb = new SpriteBatch(graphics); 
        textureLoader = new TextureLoader(graphics); 
        
        // load texture
        testTexture = textureLoader.get("assets/textures/test.png"); 
        testTexture.setMipmaps(4);
        testTexture.setFilters(MinFilter.NEAREST_MIPMAP_NEAREST, MagFilter.NEAREST);
        
        Texture2D color, depth; 
        color = graphics.createTexture2D(Texture.Format.RGBA32F, 600, 480); 
        depth = graphics.createTexture2D(Texture.Format.DEPTH32, 600, 480); 
        color.setFilters(MinFilter.LINEAR_MIPMAP_LINEAR, MagFilter.LINEAR);
        depth.setFilters(MinFilter.NEAREST, MagFilter.NEAREST);
        
        rt = graphics.createRenderTarget(600, 480); 
        rt.setColorTexture(0, color);
        rt.setDepthTexture(depth);
    }

    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) engine.stop(); 
        
        angle += 0.1f; 
    }

    @Override
    public void onRender() {
        graphics.setRenderTarget(rt);
        
        graphics.clearScreen(new ColorRgba(0.7f, 0.9f, 1.0f, 1.0f));
        graphics.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        
        sb.begin(); 
        
        sb.setTint(new ColorRgba(1.0f, 1.0f, 1.0f, 0.3f));
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 10; y++) {
                sb.draw(testTexture, (x - camX) * 32, (y - camY) * 32, 32, 32, 16, 16, 2, 1, angle, false, false);
            }
        }
        
        sb.end(); 
        
        graphics.setRenderTarget(null);
        
        graphics.clearScreen(new ColorRgba(0.7f, 0.9f, 1.0f, 1.0f));
        graphics.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        
        sb.begin(); 
        
        sb.setTint(new ColorRgba(1, 1, 1, 1));
        sb.draw((Texture2D) rt.getColorTexture(0), 0, 0, graphics.getScreenWidth(), graphics.getScreenHeight()); 
        
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

        Application app = new Sample5App();
        app.start(cfg);
    }

}
