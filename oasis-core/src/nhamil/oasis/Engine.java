package nhamil.oasis;

import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.sound.SoundSystem;

public class Engine {

    private GraphicsSystem graphics;
    private SoundSystem sound;
    private InputSystem input;
    private Application app;
    private GameLoop loop;
    
    public Engine(GameLoop loop, GraphicsSystem graphics, SoundSystem sound, InputSystem input, Application app) {
        this.loop = loop;
        this.graphics = graphics;
        this.sound = sound;
        this.input = input;
        this.app = app;
        app.setEngine(this);
        
        loop.registerSystem(graphics);
        loop.registerSystem(sound);
        loop.registerSystem(input);
        loop.registerSystem(app);
    }
    
    public void start() {
        if (loop.isRunning()) {
            return;
        }
        loop.start();
    }
    
    public void stop() {
        loop.stop();
    }
    
    public boolean isRunning() {
        return loop.isRunning();
    }
    
    public GraphicsSystem getGraphicsSystem() {
        return graphics;
    }
    
    public SoundSystem getSoundSystem() {
        return sound;
    }
    
    public InputSystem getInputSystem() {
        return input;
    }
    
    public Application getApplication() {
        return app;
    }
    
}
