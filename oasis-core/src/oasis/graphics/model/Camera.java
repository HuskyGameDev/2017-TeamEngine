package oasis.graphics.model;

import oasis.math.Matrix4f;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;

public abstract class Camera {

    private static final Vector3f UP = new Vector3f(0, 1, 0); 
    
    private Vector3f position; 
    private Quaternionf rotation; 
    private Matrix4f viewMatrix; 
    private boolean dirty = true; 
    
    protected int width; 
    protected int height; 
    
    public Camera(int width, int height) {
        this.width = width; 
        this.height = height; 
        position = new Vector3f(); 
        rotation = new Quaternionf(); 
        viewMatrix = new Matrix4f(); 
    }
    
    public abstract Matrix4f getProjectionMatrix(); 
    
    public int getWidth() {
        return width; 
    }
    
    public int getHeight() {
        return height; 
    }
    
    public void setSize(int w, int h) {
        this.width = w; 
        this.height = h;
    }
    
    public Matrix4f getViewMatrix() {
        if (dirty) {
            Vector3f forward = new Vector3f(0, 0, -1).rotate(rotation); 
            viewMatrix.set(Matrix4f.lookAt(position, position.add(forward), UP)); 
        }
        return viewMatrix; 
    }
    
    public void lookAt(Vector3f position) {
        // TODO 
    }
    
    public Vector3f getPosition() {
        return position; 
    }
    
    public Quaternionf getRotation() {
        return rotation; 
    }
    
    public void setPosition(Vector3f position) {
        this.position.set(position); 
    }
    
    public void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation); 
    }
    
    public void setRotation(float yaw, float pitch) {
        rotation.set(Quaternionf.axisAngle(new Vector3f(0, 1, 0), yaw)); 
        rotation.multiplySelf(Quaternionf.axisAngle(new Vector3f(1, 0, 0), pitch)); 
    }
    
}
