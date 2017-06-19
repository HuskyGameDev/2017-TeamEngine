package oasis.core;

public class GameLogger {

    private String prefix = "";
    
    public GameLogger(Class<?> name) {
        prefix = name.getName();
    }
    
    public GameLogger(String name) {
        prefix = name;
    }
    
    public void log(LogLevel level, Object message) {
        if (level.ordinal() <= LogLevel.SEVERE.ordinal()) { 
            System.err.println("[" + level.prefix + "][" + prefix + "] " + (message == null ? "null" : message.toString()));
        }
        else { 
            System.out.println("[" + level.prefix + "][" + prefix + "] " + (message == null ? "null" : message.toString()));
        }
        
        if (level == LogLevel.FATAL) {
            System.exit(1);
        }
    }
    
    public void fatal(Object msg) {
        log(LogLevel.FATAL, msg);
    }
    
    public void severe(Object msg) {
        log(LogLevel.SEVERE, msg);
    }
    
    public void warning(Object msg) {
        log(LogLevel.WARNING, msg);
    }
    
    public void info(Object msg) {
        log(LogLevel.INFO, msg);
    }
    
    public void debug(Object msg) {
        log(LogLevel.DEBUG, msg);
    }
    
}
