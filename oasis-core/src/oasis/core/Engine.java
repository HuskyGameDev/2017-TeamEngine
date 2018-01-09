package oasis.core;

import oasis.util.Timer;

/**
 * Runs the game loop and supplies interfaces for graphics, audio, input, etc. 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Engine {

    private static final Logger log = new Logger(Engine.class);
    
    /**
     * Starting updates per second
     */
    public static final float DEFAULT_UPDATE_RATE = 60.0f;
    /**
     * Starting frames per second
     */
    public static final float DEFAULT_FRAME_RATE = 60.0f;
    
    private Application app;
    
    private volatile boolean running = false;
    private Thread thread = null;
    private boolean resetLoop;
    private float targetFps = DEFAULT_FRAME_RATE;
    private float targetUps = DEFAULT_UPDATE_RATE;
    private Backend backend; 
    
    public Engine(Config config, Backend backend, Application app) {
        this.backend = backend; 
        this.app = app; 
        backend.applyConfig(config); 
        setConfig(config); 
    }
    
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
    
    private void setConfig(Config conf) {
        targetFps = conf.fps; 
        targetUps = conf.ups; 
    }
    
    /**
     * Initialize the engine listener (the game) 
     */
    private void init() {
        backend.preInit(); 
        backend.runOnMainThread(new Runnable() {
            public void run() {
                app.init(); 
            }
        }); 
        backend.postInit(); 
    }
    
    /**
     * Update the engine listener
     * 
     * @param dt Amount of time since the last update 
     */
    private void update(float dt) {
        backend.preUpdate(dt); 
        backend.runOnMainThread(new Runnable() {
            public void run() {
                app.update(dt); 
            }
        });
        backend.postUpdate(dt); 
    }
    
    /**
     * Render the engine listener
     */
    private void render() {
        backend.preRender(); 
        backend.runOnMainThread(new Runnable() {
            public void run() {
                backend.getGraphicsDevice().preRender(); 
                app.render(); 
                backend.getGraphicsDevice().postRender(); 
                backend.getDisplay().swapBuffers(); 
            }
        });
        backend.postRender(); 
    }
    
    /**
     * Exit the engine listener 
     */
    private void exit() {
        backend.preExit(); 
        backend.runOnMainThread(new Runnable() {
            public void run() {
                app.exit(); 
            }
        });
        backend.postExit(); 
        System.exit(0); 
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
            if (backend.getDisplay().shouldClose()) {
                if (app.closeAttempt()) {
                    exit(); 
                    backend.getDisplay().hide(); 
                }
            }
            
            // keep updates up to date 
            int loops = 0;
            while (running && tickTimer < time.getTime() && loops++ < 10) {
                update(1.0f / targetUps);
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

        try {
            thread.join();
        } catch (InterruptedException e) {
            log.warning("Interrupted exception joining Engine thread");
        }
    }
    
}
