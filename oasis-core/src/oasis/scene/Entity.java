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
        detachAllComponents(); 
        scene = null; 
    }
    
    public Scene getScene() {
        return scene; 
    }
    
    public <T extends EntityComponent> boolean hasComponent(Class<T> type) {
        return components.containsKey(type); 
    }
    
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T getComponent(Class<T> type) {
        EntityComponent comp = components.get(type);
        
        if (comp == null) return null; 
        
        return (T) comp; 
    }
    
    public boolean detachAllComponents() {
        boolean removed = false; 
        for (Class<? extends EntityComponent> type : components.keySet()) {
            removed |= detachComponent(type); 
        } 
        return removed; 
    }
    
    public <T extends EntityComponent> boolean detachComponent(Class<T> type) {
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
    
    public void attachComponent(EntityComponent comp) {
        if (comp == null) return; 
        
        Class<? extends EntityComponent> type = comp.getClass(); 
        EntityComponent old = components.get(type); 
        
        if (old != null) {
            old.setEntity(null); 
        }
        
        components.put(type, comp); 
        comp.setEntity(this); 
        
        updateInScene(); 
    }
    
    public void attachComponents(EntityComponent... comps) {
        if (comps == null) return; 
        
        for (EntityComponent comp : comps) {
            attachComponent(comp); 
        }
    }
    
    private void updateInScene() {
        scene.updateEntity(this); 
    }
    
}
