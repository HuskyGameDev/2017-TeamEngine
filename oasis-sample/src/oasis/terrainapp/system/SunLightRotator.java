package oasis.terrainapp.system;

import oasis.math.Quaternion;
import oasis.math.Vector3;
import oasis.scene.Entity;
import oasis.scene.EntitySystem;
import oasis.scene.Transform;
import oasis.terrainapp.component.SunLightTag;

public class SunLightRotator extends EntitySystem {

    private float time = 30; 
    
    public SunLightRotator() {
        super(DEFAULT_PRIORITY, Transform.class, SunLightTag.class); 
    }
    
    public void preUpdate(float dt) {
        time += dt; 
    }
    
    public void update(Entity e, float dt) {
        Transform t = e.get(Transform.class);  
        
        Vector3 dir = new Vector3(1, 0, 0).rotate(Quaternion.axisAngle(new Vector3(0, 0, 1), -time * 0.01f)); 
        
        if (dir.y > 0) dir.y *= -1; 
        
        t.setRotation(Quaternion.direction(dir)); 
        t.setPosition(dir.multiply(-500));
        t.setScale(10.0f);
    }
    
}
