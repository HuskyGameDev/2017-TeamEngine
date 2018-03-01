package oasis.terrainapp.component;

import oasis.scene.EntityComponent;

public class FpsCamera extends EntityComponent {

    public float yaw; 
    public float pitch; 
    
    protected void attach() {
        yaw = pitch = 0; 
    }
    
}
