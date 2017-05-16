package nhamil.oasis.core;

import nhamil.oasis.graphics.Display;
import nhamil.oasis.graphics.GraphicsContext;
import nhamil.oasis.input.Keyboard;
import nhamil.oasis.input.Mouse;

public abstract class Application implements EngineListener {

    private static final GameLogger log = new GameLogger(Application.class);
    
    protected GraphicsContext graphics;
    protected Display display;
    protected Keyboard keyboard;
    protected Mouse mouse;
    
    protected Engine engine;
    
    public abstract void onInit();
    
    public abstract void onUpdate(float dt);
    
    public abstract void onRender();
    
    public abstract void onExit();
    
    public final synchronized void start(Config config) {
        initEngine(config);
        engine.start();
    }
    
    public final synchronized void stop() {
        engine.stop();
    }
    
    private void initEngine(Config config) {
        try {
            engine = config.engine.newInstance();
        } catch (InstantiationException e) {
            log.fatal("Could not instantiate " + config.engine);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.fatal("Could not access " + config.engine);
            e.printStackTrace();
        }
        engine.setFrameRate(config.fps);
        engine.setUpdateRate(config.ups);
        engine.setEngineListener(this);
        
        graphics = engine.getGraphics();
        display = engine.getDisplay();
        
        display.setResizable(false);
        display.setSize(800, 600);
    }
    
}
