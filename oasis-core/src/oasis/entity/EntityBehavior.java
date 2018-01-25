package oasis.entity;

public abstract class EntityBehavior extends BaseEntityBehavior {

    @SafeVarargs
    public EntityBehavior(Class<? extends EntityComponent>... require) {
        super(DEFAULT_PRIORITY, require); 
    }
    
    @SafeVarargs
    public EntityBehavior(int priority, Class<? extends EntityComponent>... require) {
        super(priority, require); 
    }
    
    public EntityBehavior(int priority, Matcher matcher) {
        super(priority, matcher); 
    }
    
    public void preUpdate(float dt) {} 
    
    public void update(Entity e, float dt) {} 

    public void postUpdate(float dt) {} 
    
    public void preRender() {} 
    
    public void render(Entity e) {} 

    public void postRender() {} 
    
    @Override
    public void update(float dt) {
        preUpdate(dt); 
        
        for (int i = 0; i < entities.size(); i++) {
            update(entities.get(i), dt); 
        }
        
        postUpdate(dt); 
    }
    
    @Override
    public void render() {
        preRender(); 
        
        for (int i = 0; i < entities.size(); i++) {
            render(entities.get(i)); 
        }
        
        postRender(); 
    }
    
}
