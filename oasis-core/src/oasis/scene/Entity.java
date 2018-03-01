package oasis.scene;

import java.util.HashMap;
import java.util.Map;

public final class Entity {

    private Scene scene; 
    private Map<Class<? extends EntityComponent>, EntityComponent> components = new HashMap<>(); 
    
    protected Entity(Scene scene) {
        this.scene = scene; 
    }
    
    protected void removedFromScene() {
        detachAll(); 
        scene = null; 
    }
    
    public Scene getScene() {
        return scene; 
    }
    
    public <T extends EntityComponent> boolean has(Class<T> type) {
        return components.containsKey(type); 
    }
    
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T get(Class<T> type) {
        EntityComponent comp = components.get(type);
        
        if (comp == null) return null; 
        
        return (T) comp; 
    }
    
    public boolean detachAll() {
        boolean removed = false; 
        for (Class<? extends EntityComponent> type : components.keySet()) {
            removed |= detach(type); 
        } 
        return removed; 
    }
    
    public <T extends EntityComponent> boolean detach(Class<T> type) {
        EntityComponent comp = components.get(type);
        
        if (comp != null) {
            comp.setEntity(null); 
            components.remove(type); 
            updateInScene(); 
            return true; 
        }
        else {
            return false; 
        }
    }
    
    public <T extends EntityComponent> T attach(T comp) {
        if (comp == null) return null; 
        
        Class<? extends EntityComponent> type = comp.getClass(); 
        EntityComponent old = components.get(type); 
        
        if (old != null) {
            old.setEntity(null); 
        }
        
        components.put(type, comp); 
        comp.setEntity(this); 
        
        updateInScene(); 
        
        return comp; 
    }
    
    public void attach(EntityComponent... comps) {
        if (comps == null) return; 
        
        for (EntityComponent comp : comps) {
            attach(comp); 
        }
    }
    
    private void updateInScene() {
        scene.checkEntity(this); 
    }
    
}
