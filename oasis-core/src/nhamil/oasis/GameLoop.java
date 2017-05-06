package nhamil.oasis;

import java.util.ArrayList;
import java.util.List;

public abstract class GameLoop {

    private static final GameLogger log = new GameLogger(GameLoop.class);
    
    private volatile boolean running;
    private List<EngineSystem> systems;
    
    public GameLoop() {
        systems = new ArrayList<>();
        running = false;
    }
    
    protected abstract void run();
    
    public synchronized void start() {
        running = true;
        run();
    }
    
    public synchronized void stop() {
        running = false;
    }
    
    public synchronized boolean isRunning() {
        return running;
    }
    
    public void registerSystem(EngineSystem system) {
        if (system != null) {
            systems.add(system);
        }
        else {
            log.warning("Tried to register a null system");
        }
    }
    
    protected void init() {
        for (EngineSystem s : systems) s.onPreInit();
        for (EngineSystem s : systems) s.onInit();
        for (EngineSystem s : systems) s.onPostInit();
    }
    
    protected void update(float dt) {
        for (EngineSystem s : systems) s.onPreUpdate(dt);
        for (EngineSystem s : systems) s.onUpdate(dt);
        for (EngineSystem s : systems) s.onPostUpdate(dt);
    }
    
    protected void render(float lerp) {
        for (EngineSystem s : systems) s.onPreRender(lerp);
        for (EngineSystem s : systems) s.onRender(lerp);
        for (EngineSystem s : systems) s.onPostRender(lerp);
    }
    
    protected void exit() {
        for (EngineSystem s : systems) s.onPreExit();
        for (EngineSystem s : systems) s.onExit();
        for (EngineSystem s : systems) s.onPostExit();
    }
    
}
