package nhamil.oasis;

public enum LogLevel {

    FATAL("FATAL"), 
    SEVERE("SEVERE"), 
    WARNING("WARNING"), 
    INFO("INFO"), 
    DEBUG("DEBUG");
    
    public final String prefix;
    
    private LogLevel(String pre) {
        prefix = pre;
    }
    
}
