package oasis.graphics;

public class Uniform {

    public enum Type {
        
        INT, 
        
        FLOAT, 
        
        VECTOR2, 
        
        VECTOR3, 
        
        VECTOR4, 
        
        MATRIX3, 
        
        MATRIX4, 
        
        TEXTURE_2D, 
        
        RENDER_TEXTURE_2D, 
        
        UNKNOWN; 
        
    }
    
    private Type type; 
    private String name; 
    
    public Uniform(Type type, String name) {
        this.type = type; 
        this.name = name; 
    }
    
    public Type getType() {
        return type; 
    }
    
    public String getName() {
        return name; 
    }
    
}
