package oasis.core;

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
    public static final int VERSION_MINOR = 1;
    
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
     * Get information about the engine 
     * 
     * @return Information about the engine 
     */
    public static String getEngineInfo() {
        return "Running " + FULL_NAME;
    }
    
    /**
     * Private constructor
     */
    private Oasis() {}
    
}
