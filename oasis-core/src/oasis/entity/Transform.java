package oasis.entity;

import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;

/**
 * 
 * 3D transform, has a position, rotation, and scale 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Transform extends Component {

    private Vector3 position; 
    private Quaternion rotation; 
    private Vector3 scale; 
    private Matrix4 matrix; 
    private boolean dirty = true; 
    
    public Transform() {
        position = new Vector3(); 
        rotation = new Quaternion(); 
        scale = new Vector3(1);
        matrix = Matrix4.identity(); 
        dirty = true; 
    }
    
    @Override
    public void activate() {
        position.set(0); 
        rotation.set(0); 
        scale.set(1); 
        dirty = true; 
    }
    
    public Vector3 getPosition() { return position; } 
    public Quaternion getRotation() { return rotation; } 
    public Vector3 getScale() { return scale; } 
    
    public Matrix4 getMatrix() { 
        if (dirty) {
            matrix = Matrix4.identity(); 
            matrix.multiplySelf(Matrix4.translation(position)); 
            matrix.multiplySelf(Matrix4.rotation(rotation)); 
            matrix.multiplySelf(Matrix4.scale(scale)); 
        }
        return matrix; 
    }
    
    public void setPosition(Vector3 pos) { position.set(pos); } 
    public void setRotation(Quaternion rot) { rotation.set(rot); } 
    public void setScale(Vector3 scl) { scale.set(scl); } 
    
}
