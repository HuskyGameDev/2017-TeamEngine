package oasis.graphics;

import oasis.math.Vector3;
import oasis.math.Vector4;

public abstract class Light {

    public enum Type {
        
        DIRECTIONAL, 
        
        POINT, 
        
        SPOT; 
        
    }
    
    private Vector3 color; 
    
    public Light() {
        this.color = new Vector3(); 
    }
    
    public abstract Type getType(); 
    
    protected abstract Vector4 getPositionUniform(); 
    protected abstract Vector3 getAttenuationUniform(); 
    
    public Vector3 getColor() {
        return new Vector3(color); 
    }
    
    public void setColor(Vector3 color) {
        this.color.set(color); 
    }
    
}
