package oasis.core;

import oasis.graphics.Display;
import oasis.graphics.GraphicsDevice;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.util.Timer;

public abstract class Engine {

    private static final GameLogger log = new GameLogger(Engine.class);
    
    public static final float DEFAULT_UPDATE_RATE = 60.0f;
    public static final float DEFAULT_FRAME_RATE = 60.0f;
    
    protected EngineListener listener;
    
    protected volatile boolean running = false;
    protected Thread thread = null;
    protected boolean resetLoop;
    protected float targetFps = DEFAULT_FRAME_RATE;
    protected float targetUps = DEFAULT_UPDATE_RATE;
    
    public Engine() {
        
    }
    
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

    public synchronized void stop() {
        if (!running) {
            log.warning("Engine is already stopped, cannot stop");
            return;
        }
        running = false;
    }
    
    public abstract Display getDisplay();
    
    public abstract Keyboard getKeyboard(); 
    public abstract Mouse getMouse(); 
    
    public abstract GraphicsDevice getGraphicsDevice();
    
    protected abstract void initEngine();
    protected abstract void init();
    protected abstract void update(float dt);
    protected abstract void render();
    protected abstract void exit();
    
    public void setEngineListener(EngineListener listener) {
        this.listener = listener;
    }
    
    public void setFrameRate(float fps) {
        targetFps = fps;
        resetLoop = true;
    }

    public void setUpdateRate(float ups) {
        targetUps = ups;
        resetLoop = true;
    }
    
    protected void gameLoop() {
        initEngine();
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
            if (resetLoop) {
                resetLoop = false;
                
                tickTimer = time.getTime();
                skipTicks = 1.0 / targetUps;
                
                frameTimer = time.getTime();
                skipFrames = 1.0 / targetFps;
            }
            
            if (getDisplay().shouldClose()) {
                if (listener.onCloseAttempt()) {
                    getDisplay().hide(); 
                    exit(); 
                    System.exit(0); 
                }
            }
            
            int loops = 0;
            while (running && tickTimer < time.getTime() && loops++ < 10) {
                update(0.0f);
                ticks++;
                tickTimer += skipTicks;
            }
            
            if (running && frameTimer < time.getTime()) {
                render();
                frames++;
                frameTimer += skipFrames;
            }
            
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
            log.warning("Interrupted exception joining JOGL Engine thread");
        }
    }
    
}
