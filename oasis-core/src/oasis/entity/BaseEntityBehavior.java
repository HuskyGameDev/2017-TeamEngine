package oasis.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityBehavior extends Behavior {

    protected final List<Entity> entities = new ArrayList<>(); 
    
    private Matcher matcher; 
    
    @SafeVarargs
    public BaseEntityBehavior(int priority, Class<? extends EntityComponent>... require) {
        this(priority, Matcher.require(require).create()); 
    }
    
    public BaseEntityBehavior(int priority, Matcher matcher) {
        super(priority); 
        
        if (matcher == null) {
            this.matcher = Matcher.empty(); 
        }
        else {
            this.matcher = matcher; 
        }
    }
    
    public void updateIds(EntityManager em) {} 
    
    public Matcher getMatcher() {
        return matcher; 
    }
    
    public void onManagerSet(EntityManager em) {
        entities.clear(); 
        
        for (int i = 0; i < em.getEntityCount(); i++) {
            Entity e = em.getEntity(i); 
            
            if (matcher.matches(e)) {
                entities.add(e); 
            }
        }
        
        updateIds(em); 
    }
    
    public void onEntityChanged(Entity e) {
        entities.remove(e); 
        
        if (matcher.matches(e)) {
            entities.add(e); 
        }
    }
    
}
