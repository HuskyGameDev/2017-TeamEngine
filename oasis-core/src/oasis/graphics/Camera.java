package oasis.graphics;

import oasis.core.Oasis;
import oasis.math.Mathf;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class Camera {

    private static final Vector3 UP = new Vector3(0, 1, 0); 
    
    private RenderTexture renderTexture; 
    
    private Vector3 position; 
    private Quaternion rotation; 
    private float fov; 
    private float near; 
    private float far; 
    
    public Camera() {
        position = new Vector3(0, 0, 0); 
        rotation = Quaternion.axisAngle(UP, 0); 
        fov = Mathf.toRadians(70.0f);  
        near = 0.1f; 
        far = 1000.0f; 
    }
    
    public RenderTexture getRenderTexture() {
        return renderTexture; 
    }
    
    public Vector3 getPosition() {
        return new Vector3(position); 
    }
    
    public Quaternion getRotation() {
        return new Quaternion(rotation); 
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
    
    public Matrix4 getViewMatrix() {
        // TODO make sure this is right 
        return Matrix4.rotation(rotation).transposeSelf().multiplySelf(Matrix4.translation(position.multiply(-1))); 
    }
    
    public Matrix4 getProjectionMatrix() {
        return Matrix4.perspective(fov, Oasis.getDisplay().getAspectRatio(), near, far); 
    }
    
    public void setRenderTexture(RenderTexture rt) {
        renderTexture = rt; 
    }
    
    public void setPosition(Vector3 pos) {
        position.set(pos); 
    }
    
    public void setRotation(Quaternion rot) {
        rotation.set(rot); 
    }
    
    public void setRotation(float yaw, float pitch) {
        Quaternion rot = Quaternion.axisAngle(new Vector3(1, 0, 0), pitch).normalize(); 
        rot = Quaternion.axisAngle(new Vector3(0, 1, 0), yaw).normalize().multiply(rot); 
        setRotation(rot.normalize()); 
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
