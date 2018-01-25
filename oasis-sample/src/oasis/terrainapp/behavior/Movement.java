package oasis.terrainapp.behavior;

import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.entity.Transform;
import oasis.math.Vector3;
import oasis.terrainapp.component.Velocity;

public class Movement extends EntityBehavior {

    public Movement() {
        super(Transform.class, Velocity.class); 
    }
    
    @Override
    public void update(Entity e, float dt) {
        Velocity vel = e.get(Velocity.class); 
        Transform tfm = e.get(Transform.class); 
        
        Vector3 newPos = tfm.getPosition().addSelf(vel.velocity.multiply(dt)); 
        tfm.setPosition(newPos); 
    }
    
}
