package oasis.terrainapp.component;

import oasis.entity.EntityComponent;
import oasis.math.Vector3;

public class Velocity extends EntityComponent {

    public final Vector3 velocity = new Vector3(); 
    
    public void activate() {
        velocity.set(0); 
    }
    
}
