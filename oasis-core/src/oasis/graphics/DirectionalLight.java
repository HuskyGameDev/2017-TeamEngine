package oasis.graphics;

import oasis.math.Vector3;
import oasis.math.Vector4;

public class DirectionalLight extends Light {

    private Vector3 direction; 
    
    public DirectionalLight() {
        this.direction = new Vector3(0, -1, 0); 
    }
    
    public Vector3 getDirection() {
        return new Vector3(direction); 
    }
    
    public void setDirection(Vector3 dir) {
        this.direction.set(dir).normalizeSelf(); 
    }

    @Override
    protected Vector4 getPositionUniform() {
        return new Vector4(direction, 0); 
    }

    @Override
    protected Vector3 getAttenuationUniform() {
        return new Vector3(1, 0, 0); 
    }

    @Override
    public Type getType() {
        return Type.DIRECTIONAL; 
    }

}
