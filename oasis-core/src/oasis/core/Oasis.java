package oasis.core;

public final class Oasis {

    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 0;
    public static final int VERSION_REVISION = 1;
    
    public static final String VERSION = "v" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;
    public static final String NAME = "Oasis Engine";
    public static final String FULL_NAME = NAME + " " + VERSION;
    
    public static final String DEFAULT_ASSET_FOLDER = "assets/";
    public static final String DEFAULT_TEXTURE_FOLDER = DEFAULT_ASSET_FOLDER + "textures/";
    public static final String DEFAULT_SHADER_FOLDER = DEFAULT_ASSET_FOLDER + "shaders/";
    public static final String DEFAULT_SOUND_FOLDER = DEFAULT_ASSET_FOLDER + "sounds/";
    
    public static String getEngineInfo() {
        return "Running " + FULL_NAME;
    }
    
    private Oasis() {}
    
}
