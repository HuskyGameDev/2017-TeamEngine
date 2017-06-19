package oasis.core;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
public class EngineException extends RuntimeException {

    public EngineException(String string) {
        super(string);
    }

    public EngineException(Object obj) {
        super(obj == null ? null : obj.toString());
    }
    
    public EngineException(Throwable thrw) {
        super(thrw);
    }
    
    public static String getStackTrace(Exception e) {
        StringWriter string = new StringWriter();
        PrintWriter print = new PrintWriter(string);
        e.printStackTrace(print);
        return string.toString();
    }
    
}
