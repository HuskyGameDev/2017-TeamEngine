package oasis.scene;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Logger;

public class Scene {

    private static final Logger log = new Logger(Scene.class); 
    
    private List<Entity> entities = new ArrayList<>(); 
    private List<EntitySystem> systems = new ArrayList<>(); 
    
    public Scene() {
        
    }
    
    public void addSystem(String name, EntitySystem system) {
        systems.add(system); 
    }
    
    public Entity createEntity() {
        Entity e = new Entity(this); 
        entities.add(e); 
        return e; 
    }
    
    public void removeEntity(Entity e) {
        if (e.getScene() != this) {
            log.warning("Entities can only be removed if they are part of this scene"); 
            return; 
        }
        // TODO remove from systems 
        entities.remove(e); 
    }
    
    public void updateEntity(Entity e) {
        if (e.getScene() != this) {
            log.warning("Entities can only be updated if they are part of this scene"); 
            return; 
        }
        // TODO update systems 
    }
    
    public void update(float dt) {
        
    }
    
    public void render() {
        
    }
    
}
