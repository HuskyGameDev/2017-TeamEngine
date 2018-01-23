package oasis.ecsapp;

import oasis.ecsapp.behavior.EntityMover;
import oasis.ecsapp.behavior.EntityRenderer;
import oasis.ecsapp.component.Transform;
import oasis.ecsapp.component.Velocity;
import oasis.entity.Entity;
import oasis.entity.EntityManager;

public class Main {

    public static void main(String[] args) {
        EntityManager manager = new EntityManager(); 
        
        manager.registerComponent(Transform.class);
        manager.registerComponent(Velocity.class);
        
        manager.addBehavior(new EntityMover()); 
        manager.addBehavior(new EntityRenderer()); 
        
        Entity entity; 
        Velocity v; 
        Transform t; 
        
        entity = manager.createEntity(); 
        entity.add(Transform.class); 
        v = entity.add(Velocity.class);
        v.dx = 0.5f; 
        v.dy = 0.2f; 
        
        entity = manager.createEntity(); 
        t = entity.add(Transform.class); 
        t.x = 3; 
        t.y = 5; 
        
        entity = manager.createEntity(); 
        
        while (true) {
            manager.update(1); 
            manager.render(); 
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
