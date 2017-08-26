package oasis.core;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Default exception type used by Oasis
 * 
 * @author Nicholas Hamilton
 *
 */
@SuppressWarnings("serial")
public class EngineException extends RuntimeException {

    /**
     * Constructor that takes a message 
     * 
     * @param string Exception message
     */
    public EngineException(String string) {
        super(string);
    }

    /**
     * Constructor that takes an object 
     * 
     * @param obj Exception object 
     */
    public EngineException(Object obj) {
        super(obj == null ? null : obj.toString());
    }
    
    /**
     * Constructor that takes a throwable. Can 
     * be used to wrap around another exception 
     * 
     * @param thrw Exception throwable 
     */
    public EngineException(Throwable thrw) {
        super(thrw);
    }
    
    /**
     * Stringifies the stack trace of an exception\
     * 
     * @param e The exception 
     * @return Stringified stack trace 
     */
    public static String getStackTrace(Exception e) {
        StringWriter string = new StringWriter();
        PrintWriter print = new PrintWriter(string);
        e.printStackTrace(print);
        return string.toString();
    }
    
}
