package oasis.core;

import oasis.file.FileSystem;
import oasis.graphics.Graphics;
import oasis.graphics.GraphicsDevice;
import oasis.input.Keyboard;
import oasis.input.Mouse;

/**
 * Oasis engine constants 
 * 
 * @author Nicholas Hamilton
 *
 */
public final class Oasis {

    /**
     * Major version number 
     */
    public static final int VERSION_MAJOR = 0;
    
    /**
     * Minor version number 
     */
    public static final int VERSION_MINOR = 3;
    
    /**
     * Revision version number 
     */
    public static final int VERSION_REVISION = 0;
    
    /**
     * Version number string 
     */
    public static final String VERSION = "v" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;
    
    /**
     * Name of the engine 
     */
    public static final String NAME = "Oasis Engine";
    
    /**
     * Name of the engine and the version number 
     */
    public static final String FULL_NAME = NAME + " " + VERSION;
    
    /**
     * Default location of assets 
     */
    public static final String DEFAULT_ASSET_FOLDER = "assets/";
    
    /**
     * Default location of textures 
     */
    public static final String DEFAULT_TEXTURE_FOLDER = DEFAULT_ASSET_FOLDER + "textures/";
    
    /**
     * Default location of shaders 
     */
    public static final String DEFAULT_SHADER_FOLDER = DEFAULT_ASSET_FOLDER + "shaders/";
    
    /**
     * Default location of sounds 
     */
    public static final String DEFAULT_SOUND_FOLDER = DEFAULT_ASSET_FOLDER + "sounds/";
    
    /**
     * Default location of models
     */
    public static final String DEFAULT_MODEL_FOLDER = DEFAULT_ASSET_FOLDER + "models/";
    
    private static volatile boolean running = false; 
    private static Backend backend; 
    private static Engine engine; 
    private static Graphics graphics; 
    private static GraphicsDevice graphicsDevice; 
    private static DirectBufferAllocator alloc; 
    private static Display display; 
    private static Keyboard keyboard; 
    private static Mouse mouse; 
    private static FileSystem files = new FileSystem(); 
    
    public static Graphics getGraphics() {
        return graphics; 
    }
    
    public static GraphicsDevice getGraphicsDevice() {
        return graphicsDevice; 
    }
    
    public static DirectBufferAllocator getDirectBufferAllocator() {
        return alloc; 
    }
    
    public static FileSystem getFileSystem() {
        return files; 
    }
    
    public static Display getDisplay() {
        return display; 
    }
    
    public static Keyboard getKeyboard() {
        return keyboard; 
    }
    
    public static Mouse getMouse() {
        return mouse; 
    }
    
    public static void start(Config config, Application application) {
        if (running) {
            throw new OasisException("Oasis is already running"); 
        }
        
        if (application == null) {
            throw new OasisException("Application must not be null"); 
        }
        
        if (config == null) {
            config = new Config(); 
        }
        
        if (config.backend == null) {
            config.backend = BackendType.AUTO; 
        }
        
        backend = config.backend.create();
        
        if (backend == null) {
            throw new OasisException("Platform could not be created"); 
        }
        
        engine = new Engine(config, backend, application); 
        graphics = new Graphics(); 
        graphicsDevice = backend.getGraphicsDevice(); 
        alloc = backend.getDirectBufferAllocator(); 
        display = backend.getDisplay(); 
        keyboard = backend.getKeyboard(); 
        mouse = backend.getMouse(); 
        
        running = true; 
        engine.start(); 
    }
    
    public static void stop() {
        if (!running) {
            throw new OasisException("Oasis is not running"); 
        }
        
        engine.stop(); 
        running = false; 
    }
    
    /**
     * Private constructor
     */
    private Oasis() {}
    
}
