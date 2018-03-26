package oasis.core;

/**
 * Basic logging class 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Logger {

    private String prefix = "";
    
    /**
     * Constructor that takes a class
     * 
     * @param name Class to be used as the logging prefix 
     */
    public Logger(Class<?> name) {
        prefix = name.getName();
    }
    
    /**
     * Constructor that takes a name
     * 
     * @param name Name to be used as the logging prefix 
     */
    public Logger(String name) {
        prefix = name;
    }
    
    /**
     * Logs a message 
     * 
     * @param level Priority of the message 
     * @param message The message 
     */
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
    
    /**
     * Logs a fatal message. 
     * Fatal messages will log and then 
     * exit the system 
     * 
     * @param msg The message 
     */
    public void fatal(Object msg) {
        log(LogLevel.FATAL, msg);
    }
    
    /**
     * Logs a severe message 
     * 
     * @param msg The message 
     */
    public void severe(Object msg) {
        log(LogLevel.SEVERE, msg);
    }
    
    /**
     * Logs a warning message 
     * 
     * @param msg The message 
     */
    public void warning(Object msg) {
        log(LogLevel.WARNING, msg);
    }
    
    /**
     * Logs an info message
     * 
     * @param msg The message 
     */
    public void info(Object msg) {
        log(LogLevel.INFO, msg);
    }
    
    /** Logs a debug message 
     * 
     * @param msg The message 
     */
    public void debug(Object msg) {
        log(LogLevel.DEBUG, msg);
    }
    
}
