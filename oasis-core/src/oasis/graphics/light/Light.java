package oasis.graphics.light;

import oasis.math.Vector3;

/**
 * Base light class. 
 * Holds any data that all lights need (currently color). 
 * 
 * @author Nicholas Hamilton
 *
 */
public abstract class Light {

    private Vector3 color; 
    
    public Light(Vector3 color) {
        this.color = new Vector3(color); 
    }
    
    public Vector3 getColor() {
        return color; 
    }
    
    public void setColor(Vector3 color) {
        this.color.set(color); 
    }
    
}
