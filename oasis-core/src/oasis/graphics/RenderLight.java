package oasis.graphics;

import oasis.entity.Light;
import oasis.entity.Transform;

public class RenderLight {

    public Light light; 
    public Transform transform; 
    
    public RenderLight(Light light, Transform transform) {
        this.light = light; 
        this.transform = transform; 
    }
    
    public boolean isValid() {
        return light != null && transform != null; 
    }
    
}
