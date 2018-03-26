package oasis.scene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import oasis.core.Logger;

public class Scene {

    private static final Logger log = new Logger(Scene.class); 
    
    private List<Entity> entities = new ArrayList<>(); 
    private List<EntitySystem> systems = new ArrayList<>(); 
    
    public Scene() {
        systems.add(new MeshRenderer()); 
        systems.add(new LightRenderer()); 
        
        systems.sort(new Comparator<EntitySystem>() {
            public int compare(EntitySystem a, EntitySystem b) {
                return a.getPriority() - b.getPriority(); 
            }
        });
    }
    
    public void addSystem(String name, EntitySystem system) {
        systems.add(system); 
        
        systems.sort(new Comparator<EntitySystem>() {
            public int compare(EntitySystem a, EntitySystem b) {
                return a.getPriority() - b.getPriority(); 
            }
        });
        
        system.setScene(this); 
    }
    
    public Entity createEntity() {
        Entity e = new Entity(this); 
        entities.add(e); 
        checkEntity(e); 
        return e; 
    }
    
    public void removeEntity(Entity e) {
        if (e.getScene() != this) {
            log.warning("Entities can only be removed if they are part of this scene"); 
            return; 
        }
        
        for (EntitySystem es : systems) {
            es.removeEntity(e); 
        }
        
        entities.remove(e); 
    }
    
    public void checkEntity(Entity e) {
        if (e.getScene() != this) {
            log.warning("Entities can only be updated if they are part of this scene"); 
            return; 
        }
        
        for (EntitySystem es : systems) {
            es.checkEntity(e); 
        }
    }
    
    public void update(float dt) {
        for (EntitySystem es : systems) {
            es.update(dt); 
        }
    }
    
    public void render() {
        for (EntitySystem es : systems) {
            es.render(); 
        }
    }
    
}
