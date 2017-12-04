package oasis.graphics.model;

import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;

/**
 * Where the camera is positioned and orientated. 
 * This also supplies view and projection matrices. 
 * 
 * @author Nicholas Hamilton 
 *
 */
public abstract class Camera {

    private static final Vector3 UP = new Vector3(0, 1, 0); 
    
    private Vector3 position; 
    private Quaternion rotation; 
    
    // cached view matrix 
    private Matrix4 viewMatrix; 
    private boolean dirty = true; 
    
    protected int width; 
    protected int height; 
    
    public Camera(int width, int height) {
        this.width = width; 
        this.height = height; 
        position = new Vector3(); 
        rotation = new Quaternion(); 
        viewMatrix = new Matrix4(); 
    }
    
    public abstract Matrix4 getProjectionMatrix(); 
    
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
    public Matrix4 getViewMatrix() {
        if (dirty) {
            Vector3 forward = new Vector3(0, 0, -1).rotate(rotation); 
            viewMatrix.set(Matrix4.lookAt(position, position.add(forward), UP)); 
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
    public void lookAt(Vector3 position) {
        // TODO FINISH 
    }
    
    public Vector3 getPosition() {
        return position; 
    }
    
    public Quaternion getRotation() {
        return rotation; 
    }
    
    public void setPosition(Vector3 position) {
        this.position.set(position); 
        dirty = true; 
    }
    
    public void move(Vector3 move) {
        this.position.addSelf(move); 
        dirty = true; 
    }
    
    public void setRotation(Quaternion rotation) {
        this.rotation.set(rotation); 
        dirty = true; 
    }
    
    public void setRotation(float yaw, float pitch) {
        // TODO 
        rotation.set(Quaternion.axisAngle(new Vector3(0, 1, 0), yaw));
        rotation.multiplySelf(Quaternion.axisAngle(new Vector3(1, 0, 0), pitch));
        rotation.normalizeSelf(); 
        dirty = true; 
    }
    
}
