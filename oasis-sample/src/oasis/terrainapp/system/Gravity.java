package oasis.terrainapp.system;

import oasis.math.Vector3;
import oasis.scene.Entity;
import oasis.scene.EntitySystem;
import oasis.terrainapp.component.Velocity;

public class Gravity extends EntitySystem {

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
