package oasis.core;

import oasis.graphics.Display;
import oasis.graphics.GraphicsDevice;

/**
 * Basic class to make a game from 
 * 
 * @author Nicholas Hamilton
 *
 */
public abstract class Application implements EngineListener {

    private static final GameLogger log = new GameLogger(Application.class);
    
    /**
     * Shortcut to the graphics device 
     */
    protected GraphicsDevice graphics;
    
    /**
     * Shortcut to the display
     */
    protected Display display;
    
    /** 
     * Reference to the engine 
     */
    protected Engine engine;
    
    /**
     * Called when application is initializing 
     */
    public abstract void onInit();
    
    /**
     * Called when application is updating 
     * 
     * @param dt Time since last update 
     */
    public abstract void onUpdate(float dt);
    
    /**
     * Called when application is rendering 
     */
    public abstract void onRender();
    
    /**
     * Called when application is exiting 
     */
    public abstract void onExit();
    
    /**
     * Called when window is trying to close 
     * 
     * @return Whether window should close 
     */
    public boolean onCloseAttempt() {
        return true; 
    }
    
    /**
     * Start the application 
     * 
     * @param config Engine configuration 
     */
    public final synchronized void start(Config config) {
        initEngine(config);
        engine.start();
    }
    
    /** 
     * Stop the application 
     */
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
        
        graphics = engine.getGraphicsDevice();
        display = engine.getDisplay();
        
        display.setResizable(false);
        display.setSize(800, 600);
    }
    
}
