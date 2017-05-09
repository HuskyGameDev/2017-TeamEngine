package nhamil.oasis.core;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.graphics.Display;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.graphics.Renderer;
import nhamil.oasis.graphics.TextureManager;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.input.Keyboard;
import nhamil.oasis.input.Mouse;

public abstract class Application implements EngineListener {

    protected GraphicsSystem graphics;
    protected TextureManager textures;
    protected Renderer renderer;
    protected Display display;
    protected InputSystem input;
    protected Keyboard keyboard;
    protected Mouse mouse;
    protected AudioSystem audio;
    
    private Engine engine;
    
    public abstract void onInit();
    
    public abstract void onUpdate(float dt);
    
    public abstract void onRender(float lerp);
    
    public abstract void onExit();
    
    public final synchronized void start(Config config) {
        initEngine(config);
        engine.start();
    }
    
    public final void stop() {
        engine.stop();
    }
    
    private void initEngine(Config config) {
        engine = config.engine.createEngine();
        engine.setEngineListener(this);
        
        graphics = engine.getGraphics();
        textures = graphics.getTextureManager();
        renderer = graphics.getRenderer();
        display = graphics.getDisplay();
        
        input = engine.getInput();
        keyboard = input.getKeyboard();
        mouse = input.getMouse();
        
        audio = engine.getAudio();
    }
    
}
