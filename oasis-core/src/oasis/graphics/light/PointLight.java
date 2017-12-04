package oasis.graphics.light;

import oasis.math.Vector3;

public class PointLight extends Light {

    private Vector3 position; 
    private float radius; 
    
    public PointLight(Vector3 color, Vector3 position, float radius) {
        super(color);
        this.position = new Vector3(position); 
        this.radius = radius; 
    }
    
    public float getRadius() {
        return radius; 
    }
    
    public void setRadius(float rad) {
        radius = rad; 
    }
    
    public Vector3 getPosition() {
        return position; 
    }
    
    public void setPosition(Vector3 position) {
        this.position.set(position); 
    }

}
