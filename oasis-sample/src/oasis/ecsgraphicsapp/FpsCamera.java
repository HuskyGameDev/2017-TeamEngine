package oasis.ecsgraphicsapp;

import oasis.entity.EntityComponent;

public class FpsCamera extends EntityComponent {

    public float yaw; 
    public float pitch; 
    
    public void activate() {
        yaw = pitch = 0; 
    }
    
}
