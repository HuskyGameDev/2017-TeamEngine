package oasis.graphics.model;

import oasis.math.Matrix4;

/**
 * Camera with a perspective projection 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class PerspectiveCamera extends Camera {

    private float fov; 
    private float near; 
    private float far; 
    
    /**
     * Constructor 
     * 
     * @param width with in pixels 
     * @param height height in pixels 
     * @param fov field of view in radians 
     * @param near near plane 
     * @param far far plane 
     */
    public PerspectiveCamera(int width, int height, float fov, float near, float far) {
        super(width, height); 
        this.fov = fov; 
        this.near = near; 
        this.far = far; 
    }
    
    /**
     * Generate a perspective projection matrix 
     */
    @Override
    public Matrix4 getProjectionMatrix() {
        return Matrix4.perspective(fov, (float)width / height, near, far); 
    }

}
