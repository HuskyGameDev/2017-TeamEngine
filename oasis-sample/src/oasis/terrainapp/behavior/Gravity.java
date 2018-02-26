package oasis.terrainapp.behavior;

import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.math.Vector3;
import oasis.terrainapp.component.Velocity;

public class Gravity extends EntityBehavior {

    private static final Vector3 GRAVITY = new Vector3(0, -16.8f, 0); 
    
    public Gravity() {
        super(Velocity.class); 
    }
    
    @Override
    public void update(Entity e, float dt) {
        Velocity vel = e.get(Velocity.class); 
        
        vel.velocity.addSelf(GRAVITY.multiply(dt)); 
    }
    
}
