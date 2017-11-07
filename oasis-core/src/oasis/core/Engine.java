package oasis.core;

import oasis.graphics.Display;
import oasis.graphics.GraphicsDevice;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.util.Timer;

/**
 * Runs the game loop and supplies interfaces for graphics, audio, input, etc. 
 * 
 * @author Nicholas Hamilton
 *
 */
public abstract class Engine {

    private static final GameLogger log = new GameLogger(Engine.class);
    
    /**
     * Starting updates per second
     */
    public static final float DEFAULT_UPDATE_RATE = 60.0f;
    /**
     * Starting frames per second
     */
    public static final float DEFAULT_FRAME_RATE = 60.0f;
    
    protected Application listener;
    
    protected volatile boolean running = false;
    protected Thread thread = null;
    protected boolean resetLoop;
    protected float targetFps = DEFAULT_FRAME_RATE;
    protected float targetUps = DEFAULT_UPDATE_RATE;
    
    /**
     * Starts the engine. The engine cannot start if it is already running
     */
    public synchronized void start() {
        if (running) {
            log.warning("Engine is already running, cannot start another instance");
            return;
        }
        running = true;
        thread = new Thread(new Runnable() {
            public void run() {
                gameLoop();
            }
        }, "Oasis Engine");
        thread.start();
    }

    /**
     * Stops the engine. The engine cannot stop if it is not running 
     */
    public synchronized void stop() {
        if (!running) {
            log.warning("Engine is already stopped, cannot stop");
            return;
        }
        running = false;
    }
    
    /**
     * Get the application
     */
    public Application getApplication() {
        return listener; 
    }
    
    /**
     * Get the display 
     * 
     * @return display  
     */
    public abstract Display getDisplay();
    
    /**
     * Get the keyboard 
     * 
     * @return keyboard 
     */
    public abstract Keyboard getKeyboard(); 
    
    /**
     * Get the mouse 
     * 
     * @return mouse 
     */
    public abstract Mouse getMouse(); 
    
    /**
     * Get the graphics device 
     * 
     * @return graphics device 
     */
    public abstract GraphicsDevice getGraphicsDevice();
    
    /**
     * Initialize engine resources 
     */
    protected abstract void initEngine();
    
    /**
     * Initialize the engine listener (the game) 
     */
    protected abstract void init();
    
    /**
     * Update the engine listener
     * 
     * @param dt Amount of time since the last update 
     */
    protected abstract void update(float dt);
    
    /**
     * Render the engine listener
     */
    protected abstract void render();
    
    /**
     * Exit the engine listener 
     */
    protected abstract void exit();
    
    /**
     * Sets the engine listener 
     * 
     * @param listener The engine listener (the game) 
     */
    public void setApplication(Application listener) {
        this.listener = listener;
    }
    
    /**
     * Set the target frames per second 
     * 
     * @param fps frames per second 
     */
    public void setFrameRate(float fps) {
        targetFps = fps;
        resetLoop = true;
    }

    /**
     * Set the target updates per second 
     * 
     * @param ups updates per second 
     */
    public void setUpdateRate(float ups) {
        targetUps = ups;
        resetLoop = true;
    }
    
    /**
     * Main game loop. This is called when the engine is running 
     */
    protected void gameLoop() {
        initEngine();
        Oasis.setEngine(this);
        init();

        Timer secondTimer = new Timer();
        Timer time = new Timer();
        
        double tickTimer = 0.0;
        double skipTicks = 1.0 / targetUps;
        int ticks = 0;
        
        double frameTimer = 0.0;
        double skipFrames = 1.0 / targetFps;
        int frames = 0;
        
        while (running) {
            // reset the loop if target UPS or FPS has
            // been changed 
            if (resetLoop) {
                resetLoop = false;
                
                tickTimer = time.getTime();
                skipTicks = 1.0 / targetUps;
                
                frameTimer = time.getTime();
                skipFrames = 1.0 / targetFps;
            }
            
            // ask the engine listener what to do 
            // when the window is trying to close 
            if (getDisplay().shouldClose()) {
                if (listener.onCloseAttempt()) {
                    getDisplay().hide(); 
                    exit(); 
                    System.exit(0); 
                }
            }
            
            // keep updates up to date 
            int loops = 0;
            while (running && tickTimer < time.getTime() && loops++ < 10) {
                update(0.0f);
                ticks++;
                tickTimer += skipTicks;
            }
            
            // keep frames up to date 
            if (running && frameTimer < time.getTime()) {
                render();
                frames++;
                frameTimer += skipFrames;
            }
            
            // print FPS every second 
            if (secondTimer.getTime() >= 1.0) {
                secondTimer.reset();
                log.debug("Time: " + String.format("%1.0f", time.getTime()) + "s, Frames: " + frames + ", Ticks: " + ticks);
                frames = ticks = 0;
            }
        }

        exit();
        System.exit(0);

        try {
            thread.join();
        } catch (InterruptedException e) {
            log.warning("Interrupted exception joining Engine thread");
        }
    }
    
}
