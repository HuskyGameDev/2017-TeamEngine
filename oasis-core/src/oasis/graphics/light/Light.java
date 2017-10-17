package oasis.graphics.light;

import oasis.math.Vector3f;

public abstract class Light {

    private Vector3f color; 
    
    public Light(Vector3f color) {
        this.color = new Vector3f(color); 
    }
    
    public Vector3f getColor() {
        return color; 
    }
    
    public void setColor(Vector3f color) {
        this.color.set(color); 
    }
    
}
