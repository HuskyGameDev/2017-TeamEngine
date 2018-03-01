package oasis.terrainapp.system;

import oasis.math.Vector3;
import oasis.scene.Entity;
import oasis.scene.EntitySystem;
import oasis.scene.Transform;
import oasis.terrainapp.component.Velocity;

public class Movement extends EntitySystem {

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
