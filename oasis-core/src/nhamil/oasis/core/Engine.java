package nhamil.oasis.core;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.input.InputSystem;

public abstract class Engine {

    protected EngineListener listener = null;
    protected float targetFps = 60.0f;
    protected float targetUps = 60.0f;
    protected boolean running = false;
    
    public abstract GraphicsSystem getGraphics();
    
    public abstract InputSystem getInput();
    
    public abstract AudioSystem getAudio();
    
    protected abstract void init();
    
    protected abstract void update(float dt);
    
    protected abstract void render(float lerp);
    
    protected abstract void exit();
    
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
        
        while (running) {
            update(0.0f);
            render(0.0f);
        }
        
        exit();
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
