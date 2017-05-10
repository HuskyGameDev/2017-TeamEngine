package nhamil.oasis.core;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.util.Timer;

public abstract class Engine {

    private static final GameLogger log = new GameLogger(Engine.class);
    
    protected EngineListener listener = null;
    protected float targetFps = 60.0f;
    protected float targetUps = 60.0f;
    protected boolean running = false;
    
    public abstract GraphicsSystem getGraphics();
    
    public abstract InputSystem getInput();
    
    public abstract AudioSystem getAudio();
    
    protected void preInit() {}
    protected void postInit() {}
    
    protected void preUpdate(float dt) {}
    protected void postUpdate(float dt) {}
    
    protected void preRender(float lerp) {}
    protected void postRender(float lerp) {}
    
    protected void preExit() {}
    protected void postExit() {}
    
    public void setEngineListener(EngineListener el) {
        listener = el;
    }
    
    public EngineListener getEngineListener() {
        return listener;
    }
    
    public void start() {
        if (running) {
            return;
        }
        running = true;
        gameLoop();
    }
    
    public void stop() {
        running = false;
    }
    
    protected void gameLoop() {
        init();
        
        Timer time = new Timer();
        
        double tickTimer = 0.0;
        double skipTicks = 1.0 / targetUps;
        
        double frameTimer = 0.0;
        double skipFrames = 1.0 / targetFps;
        
        Timer timer = new Timer();
        int ticks = 0;
        int frames = 0;
        
        while (running) {
            int loops = 0;
            while (tickTimer < time.getTime() && loops++ < 10) {
                update(0.0f);
                tickTimer += skipTicks;
                ticks++;
            }
            
            if (frameTimer < time.getTime()) {
                render(1.0f);
                frameTimer += skipFrames;
                frames++;
            }
            
            if (timer.getTime() >= 1.0) {
                log.info("Time: " + String.format("%1.0f", time.getTime()) + "s, Frames: " + frames + ", Ticks: " + ticks);
                ticks = frames = 0;
                timer.reset();
            }
        }
        
        exit();
        System.exit(0);
    }
    
    protected void init() {
        preInit();
        initListener();
        postInit();
    }
    
    protected void update(float dt) {
        preUpdate(dt);
        updateListener(dt);
        postUpdate(dt);
    }
    
    protected void render(float lerp) {
        preRender(lerp);
        renderListener(lerp);
        postRender(lerp);
    }
    
    protected void exit() {
        preExit();
        exitListener();
        postExit();
    }
    
    protected void initListener() {
        if (listener != null) listener.onInit();
    }
    
    protected void updateListener(float dt) {
        if (listener != null) listener.onUpdate(dt);
    }
    
    protected void renderListener(float lerp) {
        if (listener != null) listener.onRender(lerp);
    }
    
    protected void exitListener() {
        if (listener != null) listener.onExit();
    }
    
}
