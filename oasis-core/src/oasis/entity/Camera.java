package oasis.entity;

import oasis.core.Oasis;
import oasis.graphics.RenderTarget;
import oasis.math.Mathf;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class Camera extends EntityComponent {

    private RenderTarget renderTarget; 
    
    private float fov; 
    private float near; 
    private float far; 
    
    public Camera() {
        activate(); 
    }
    
    public void activate() {
        fov = Mathf.toRadians(70.0f); 
        near = 0.1f; 
        far = 1000.0f; 
    }
    
    public RenderTarget getRenderTarget() {
        return renderTarget; 
    }
    
    public float getFov() {
        return fov; 
    }
    
    public float getNearPlane() {
        return near; 
    }
    
    public float getFarPlane() {
        return far; 
    }
    
    // TODO move this somewhere better 
    public static Matrix4 getViewMatrix(Vector3 position, Quaternion rotation) {
        // TODO make sure this is right 
        return Matrix4.rotation(rotation).transposeSelf().multiplySelf(Matrix4.translation(position.multiply(-1))); 
    }
    
    public Matrix4 getProjectionMatrix() {
        float ar = Oasis.getDisplay().getAspectRatio(); 
        return Matrix4.perspective(fov, ar, near, far); 
    }
    
    public void setRenderTarget(RenderTarget rt) {
        renderTarget = rt; 
    }
    
    // TODO move this somewhere better 
    public static void setRotation(float yaw, float pitch, Quaternion q) {
        Quaternion rot = Quaternion.axisAngle(new Vector3(1, 0, 0), pitch).normalize(); 
        rot = Quaternion.axisAngle(new Vector3(0, 1, 0), yaw).normalize().multiply(rot); 
        q.set(rot.normalize()); 
    }
    
    public void setFov(float fov) {
        this.fov = fov; 
    }
    
    public void setNearPlane(float near) {
        this.near = near; 
    }
    
    public void setFarPlane(float far) {
        this.far = far; 
    }
    
}
