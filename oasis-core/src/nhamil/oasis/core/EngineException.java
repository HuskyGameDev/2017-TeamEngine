package nhamil.oasis.core;

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
    
}
