package nhamil.oasis.core;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.graphics.Display;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.graphics.Renderer;
import nhamil.oasis.graphics.ShaderManager;
import nhamil.oasis.graphics.TextureManager;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.input.Keyboard;
import nhamil.oasis.input.Mouse;

public abstract class Application implements EngineListener {

    private static final GameLogger log = new GameLogger(Application.class);
    
    protected GraphicsSystem graphics;
    protected TextureManager textures;
    protected ShaderManager shaders;
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
        engine.setEngineListener(this);
        
        graphics = engine.getGraphics();
        textures = graphics.getTextureManager();
        shaders = graphics.getShaderManager();
        renderer = graphics.getRenderer();
        display = graphics.getDisplay();
        
        input = engine.getInput();
        keyboard = input.getKeyboard();
        mouse = input.getMouse();
        
        audio = engine.getAudio();
    }
    
}
