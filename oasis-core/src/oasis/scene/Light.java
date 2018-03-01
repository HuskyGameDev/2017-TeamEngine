package oasis.scene;

import oasis.math.Vector3;

public class Light extends EntityComponent {

    public enum Type {
        
        DIRECTIONAL, 
        
        POINT, 
        
        SPOT; 
        
    }
    
    private final Vector3 color = new Vector3(); 
    private Type type; 
    
    protected void attach() {
        this.color.set(1); 
        this.type = Type.POINT; 
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
