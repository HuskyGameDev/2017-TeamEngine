package oasis.graphics.model;

import oasis.math.Matrix4f;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;

/**
 * Where the camera is positioned and orientated. 
 * This also supplies view and projection matrices. 
 * 
 * @author Nicholas Hamilton 
 *
 */
public abstract class Camera {

    private static final Vector3f UP = new Vector3f(0, 1, 0); 
    
    private Vector3f position; 
    private Quaternionf rotation; 
    
    // cached view matrix 
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
    
    /**
     * Set viewport size 
     * 
     * @param w
     * @param h
     */
    public void setSize(int w, int h) {
        this.width = w; 
        this.height = h;
    }
    
    /**
     * Create a view matrix based on position and orientation 
     * 
     * @return View matrix 
     */
    public Matrix4f getViewMatrix() {
        if (dirty) {
            Vector3f forward = new Vector3f(0, 0, -1).rotate(rotation); 
            viewMatrix.set(Matrix4f.lookAt(position, position.add(forward), UP)); 
        }
        return viewMatrix; 
    }
    
    /**
     * Make camera look at a position, from the camera's current position 
     * 
     * NOTE: This is not implemented, needs to be finished!
     * 
     * @param position target to look at 
     */
    public void lookAt(Vector3f position) {
        // TODO FINISH 
    }
    
    public Vector3f getPosition() {
        return position; 
    }
    
    public Quaternionf getRotation() {
        return rotation; 
    }
    
    public void setPosition(Vector3f position) {
        this.position.set(position); 
        dirty = true; 
    }
    
    public void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation); 
        dirty = true; 
    }
    
    public void setRotation(float yaw, float pitch) {
        rotation.set(Quaternionf.axisAngle(new Vector3f(0, 1, 0), yaw));
        rotation.multiplySelf(Quaternionf.axisAngle(new Vector3f(1, 0, 0), pitch));
        dirty = true; 
    }
    
}
