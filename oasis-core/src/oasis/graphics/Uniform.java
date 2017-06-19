package oasis.graphics;

public class Uniform {

    public enum Type { 
        MATRIX4, 
    }
    
    private final Type type; 
    private final String name; 
    private Object value; 
    
    private boolean dirty = true; 
    
    public Uniform(String name, Type type) { 
        this.type = type; 
        this.name = name; 
        value = null; 
    }
    
    // getters
    
    public boolean isDirty() { 
        return dirty; 
    }
    
    public Type getType() { 
        return type; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public Object getValue() { 
        return value; 
    }
    
    // setters 
    
    public void setDirty(boolean dirty) { 
        this.dirty = dirty; 
    }
    
    public void setValue(Object object) { 
        value = object; 
        dirty = true; 
    }
    
}
