package oasis.graphics.light;

import oasis.math.Vector3f;

/**
 * Light where all rays are parallel. 
 * Instead of a position, this light is given a direction 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class DirectionalLight extends Light {

    private Vector3f direction; 
    
    public DirectionalLight(Vector3f color, Vector3f direction) {
        super(color); 
        this.direction = new Vector3f(direction); 
    }
    
    public Vector3f getDirection() {
        return direction; 
    }
    
    public void setDirection(Vector3f direction) {
        this.direction.set(direction); 
    }
    
}
