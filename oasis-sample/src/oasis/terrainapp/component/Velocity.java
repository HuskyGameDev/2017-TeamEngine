package oasis.terrainapp.component;

import oasis.math.Vector3;
import oasis.scene.EntityComponent;

public class Velocity extends EntityComponent {

    public final Vector3 velocity = new Vector3();

    protected void attach() {
        velocity.set(0); 
    }
    
}
