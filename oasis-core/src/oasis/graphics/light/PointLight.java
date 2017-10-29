package oasis.graphics.light;

import oasis.math.Vector3f;

public class PointLight extends Light {

    private Vector3f position; 
    private float radius; 
    
    public PointLight(Vector3f color, Vector3f position, float radius) {
        super(color);
        this.position = new Vector3f(position); 
        this.radius = radius; 
    }
    
    public float getRadius() {
        return radius; 
    }
    
    public void setRadius(float rad) {
        radius = rad; 
    }
    
    public Vector3f getPosition() {
        return position; 
    }
    
    public void setPosition(Vector3f position) {
        this.position.set(position); 
    }

}
