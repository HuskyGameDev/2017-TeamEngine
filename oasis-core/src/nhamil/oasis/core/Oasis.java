package nhamil.oasis.core;

public final class Oasis {

    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 0;
    public static final int VERSION_REVISION = 1;
    
    public static final String VERSION = "v" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;
    public static final String NAME = "Oasis Engine";
    public static final String FULL_NAME = NAME + " " + VERSION;
    
    public static String getEngineInfo() {
        return "Running " + FULL_NAME;
    }
    
    private Oasis() {}
    
}
