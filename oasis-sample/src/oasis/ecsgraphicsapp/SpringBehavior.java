package oasis.ecsgraphicsapp;

import oasis.entity.ComponentId;
import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.entity.EntityManager;
import oasis.entity.Transform;
import oasis.math.Mathf;
import oasis.math.Vector3;

public class SpringBehavior extends EntityBehavior {

    private ComponentId<Transform> transformId; 
    private ComponentId<Spring> springId; 
    
    public SpringBehavior() {
        super(DEFAULT_PRIORITY, Transform.class, Spring.class); 
    }
    
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
        springId = em.getComponentId(Spring.class); 
    }
    
    public void update(Entity e, float dt) {
        Transform t = e.get(transformId); 
        Spring s = e.get(springId); 
        
        s.time += dt; 
        s.offset = 10 * Mathf.sin(s.time * s.speed);
        
        t.setPosition(new Vector3(s.origin.add(new Vector3(0, s.offset, 0))));
    }
    
}
