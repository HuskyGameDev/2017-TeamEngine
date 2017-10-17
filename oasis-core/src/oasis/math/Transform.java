package oasis.math;

public class Transform {

    private Vector3f position; 
    private Quaternionf rotation; 
    private Vector3f scale; 
    private Matrix4f matrix; 
    private boolean dirty = true; 
    
    public Transform() {
        position = new Vector3f(); 
        rotation = new Quaternionf(); 
        scale = new Vector3f();
        matrix = Matrix4f.identity(); 
    }
    
    public Vector3f getWorldPosition() { return position; }
    public Quaternionf getWorldRotation() { return rotation; } 
    public Vector3f getWorldScale() { return scale; } 
    
    public Matrix4f getWorldMatrix() { 
        return getMatrix(); 
    }
    
    public Vector3f getPosition() { return position; } 
    public Quaternionf getRotation() { return rotation; } 
    public Vector3f getScale() { return scale; } 
    
    public Matrix4f getMatrix() { 
        if (dirty) {
            matrix = Matrix4f.identity(); 
            matrix.multiplySelf(Matrix4f.translation(position)); 
            matrix.multiplySelf(Matrix4f.rotation(rotation)); 
            matrix.multiplySelf(Matrix4f.scale(scale)); 
        }
        return matrix; 
    }
    
    public void setPosition(Vector3f pos) { position.set(pos); } 
    public void setRotation(Quaternionf rot) { rotation.set(rot); } 
    public void setScale(Vector3f scl) { scale.set(scl); } 
    
}
