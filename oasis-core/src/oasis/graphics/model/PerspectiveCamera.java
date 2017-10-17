package oasis.graphics.model;

import oasis.math.Matrix4f;

public class PerspectiveCamera extends Camera {

    private float fov; 
    private float near; 
    private float far; 
    
    public PerspectiveCamera(int width, int height, float fov, float near, float far) {
        super(width, height); 
        this.fov = fov; 
        this.near = near; 
        this.far = far; 
    }
    
    @Override
    public Matrix4f getProjectionMatrix() {
        return Matrix4f.perspective(fov, (float)width / height, near, far); 
    }

}
