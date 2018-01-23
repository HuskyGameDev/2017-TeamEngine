package oasis.testapp;

import oasis.entity.ComponentId;
import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.entity.EntityManager;
import oasis.entity.Transform;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class SunLightRotator extends EntityBehavior {

    private ComponentId<Transform> transformId; 
    
    private float time = 0; 
    
    public SunLightRotator() {
        super(DEFAULT_PRIORITY, Transform.class, SunLightTag.class); 
    }
    
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class);
    }
    
    public void preUpdate(float dt) {
        time += dt; 
    }
    
    public void update(Entity e, float dt) {
        Transform t = e.get(transformId); 
        
        t.setRotation(Quaternion.direction(new Vector3(1, 0, 0).rotate(Quaternion.axisAngle(new Vector3(0, 0, 1), -time * 0.1f)))); 
    }
    
}
