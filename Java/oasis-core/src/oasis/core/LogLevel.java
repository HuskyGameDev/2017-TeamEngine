package oasis.core;

/**
 * Levels to be used for logging 
 * 
 * @author Nicholas Hamilton
 *
 */
public enum LogLevel {

    FATAL("FATAL"), 
    SEVERE("SEVERE"), 
    WARNING("WARNING"), 
    INFO("INFO"), 
    DEBUG("DEBUG");
    
    /**
     * The default prefix for the level 
     */
    public final String prefix;
    
    private LogLevel(String pre) {
        prefix = pre;
    }
    
}
