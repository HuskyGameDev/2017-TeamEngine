package oasis.graphics.light;

import oasis.math.Vector3;

/**
 * Light where all rays are parallel. 
 * Instead of a position, this light is given a direction 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class DirectionalLight extends Light {

    private Vector3 direction; 
    
    public DirectionalLight(Vector3 color, Vector3 direction) {
        super(color); 
        this.direction = new Vector3(direction); 
    }
    
    public Vector3 getDirection() {
        return direction; 
    }
    
    public void setDirection(Vector3 direction) {
        this.direction.set(direction); 
    }
    
}
