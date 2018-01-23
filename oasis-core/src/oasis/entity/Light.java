package oasis.entity;

import oasis.math.Vector3;

public class Light extends EntityComponent {

    public enum Type {
        
        DIRECTIONAL, 
        
        POINT, 
        
        SPOT; 
        
    }
    
    private Vector3 color; 
    private Type type; 
    
    public Light() {
        this.color = new Vector3(); 
    }
    
    public Type getType() {
        return type; 
    }
    
    public Vector3 getColor() {
        return new Vector3(color); 
    }
    
    public void setType(Type type) {
        this.type = type; 
    }
    
    public void setColor(Vector3 color) {
        this.color.set(color); 
    }
    
}
