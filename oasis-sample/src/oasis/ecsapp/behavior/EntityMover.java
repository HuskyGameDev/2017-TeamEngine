package oasis.ecsapp.behavior;

import oasis.ecsapp.component.Transform;
import oasis.ecsapp.component.Velocity;
import oasis.entity.ComponentId;
import oasis.entity.IterateEntityBehavior;
import oasis.entity.Entity;
import oasis.entity.EntityManager;

public class EntityMover extends IterateEntityBehavior {

    private ComponentId<Transform> transformId; 
    private ComponentId<Velocity> velocityId; 
    
    public EntityMover() {
        super(DEFAULT_PRIORITY - 10, Transform.class, Velocity.class);
    }
    
    @Override
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
        velocityId = em.getComponentId(Velocity.class); 
    }

    @Override
    public void update(Entity e, float dt) {
        Transform t = e.get(transformId); 
        Velocity v = e.get(velocityId); 
        
        t.x += v.dx; 
        t.y += v.dy; 
    }

}
