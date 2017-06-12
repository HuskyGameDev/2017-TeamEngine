package oasis.graphics;

import oasis.util.QuickHash;

public class Uniform {

    public enum Type {
        UNKNOWN, 
        INT, 
        FLOAT, 
        VECTOR2, 
        VECTOR3, 
        MATRIX4, 
        SAMPLER, // don't really need a sampler type since it just an int, but I will keep it for now
    }
    
    private String name; 
    private Type type; 
    
    public Uniform(String name, Type type) {
        this.name = name; 
        this.type = type; 
    }
    
    // getters
    
    public String getName() { 
        return name; 
    }
    
    public Type getType() { 
        return type; 
    }
    
    public int hashCode() {
        return QuickHash.compute(QuickHash.compute(name), type.hashCode()); 
    }
    
}
