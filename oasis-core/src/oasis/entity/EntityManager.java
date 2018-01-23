package oasis.entity;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class EntityManager {

    private Map<Class<? extends EntityComponent>, ComponentId<? extends EntityComponent>> compDict = new HashMap<>(); 
    
    private List<Entity> entities = new ArrayList<>(); 
    private List<BitSet> enabled = new ArrayList<>(); 
    private Queue<Integer> freeIds = new LinkedList<>(); 
    
    private List<ComponentPool<?>> pools = new ArrayList<>(); 
    
    private List<Behavior> behaviors = new ArrayList<>(); 
    
    public EntityManager() {
        
    }
    
    public void update(float dt) {
        for (int i = 0; i < behaviors.size(); i++) {
            Behavior b = behaviors.get(i); 
            if (!b.isEnabled()) continue; 
            b.setManager(this); 
            b.update(dt); 
        }
    }
    
    public void render() {
        for (int i = 0; i < behaviors.size(); i++) {
            Behavior b = behaviors.get(i); 
            if (!b.isEnabled()) continue; 
            b.setManager(this); 
            b.render(); 
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> ComponentId<T> getComponentId(Class<T> type) {
        ComponentId<?> id = compDict.get(type); 
        
        if (id == null) {
            throw new RuntimeException("Component is not registered: " + type); 
        }
        
        return (ComponentId<T>) id; 
    }
    
    public <T extends EntityComponent> boolean isComponentRegistered(Class<T> type) {
        return compDict.get(type) != null; 
    }
    
    public <T extends EntityComponent> void registerComponent(Class<T> type) {
        if (isComponentRegistered(type)) return; 
        
        ComponentPool<T> pool = new ComponentPool<T>(new DefaultComponentFactory<T>(type)); 
        ComponentId<T> id = new ComponentId<T>(type, pools.size()); 
        pool.ensureSize(entities.size()); 
        compDict.put(type, id); 
        pools.add(pool); 
    }
    
    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
        behaviors.sort(new Comparator<Behavior>() {
            public int compare(Behavior a, Behavior b) {
                return a.getPriority() - b.getPriority(); 
            }
        });
        
        behavior.setManager(this); 
    }
    
    public void removeBehavior(Behavior behavior) {
        behaviors.remove(behavior); 
    }
    
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T addComponent(int id, ComponentId<T> type) {
        int cid = type.getId(); 

        // activate any data 
        pools.get(cid).create(id); 
        
        enabled.get(id).set(cid, true); 
        
        onEntityUpdated(id); 
        
        return (T) pools.get(cid).get(id); 
    }
    
    @SuppressWarnings("unchecked")
    public <T extends EntityComponent> T getComponent(int id, ComponentId<T> type) {
        int cid = type.getId(); 
        
        if (!enabled.get(id).get(cid)) return null; 
        
        return (T) pools.get(cid).get(id); 
    }
    
    public <T extends EntityComponent> boolean hasComponent(int id, ComponentId<T> type) {
        int cid = type.getId(); 
        
        return enabled.get(id).get(cid); 
    }
    
    public <T extends EntityComponent> boolean removeComponent(int id, ComponentId<T> type) {
        int cid = type.getId(); 
        
        // deactivate any data 
        pools.get(cid).destroy(id); 
        
        boolean removed = enabled.get(id).get(cid); 
        enabled.get(id).set(cid, false); 
        
        onEntityUpdated(id); 
        
        return removed; 
    }
    
    public int getEntityCount() {
        return entities.size(); 
    }
    
    public Entity getEntity(int id) {
        return entities.get(id); 
    }
    
    public Entity createEntity() {
        if (freeIds.isEmpty()) {
            Entity e = new Entity(this, entities.size()); 
            entities.add(e); 
            enabled.add(new BitSet()); 
            resizePools(entities.size()); 
            onEntityUpdated(e.getId()); 
            return e; 
        }
        else {
            int id = freeIds.poll(); 
            return entities.get(id); 
        }
    }
    
    private void onEntityUpdated(int id) {
        Entity e = entities.get(id); 
        
        for (int i = 0; i < behaviors.size(); i++) {
            behaviors.get(i).onEntityChanged(e); 
        }
    }
    
    private void resizePools(int size) {
        for (int i = 0; i < pools.size(); i++) {
            pools.get(i).ensureSize(size); 
        }
    }
    
}
