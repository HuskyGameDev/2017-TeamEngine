package oasis.entity;

public abstract class Behavior {

    public static final int DEFAULT_PRIORITY = 1000; 
    
    private EntityManager manager; 
    private int priority; 
    private boolean enabled = true; 
    
    public Behavior(int priority) {
        this.priority = priority; 
    } 

    public void onManagerSet(EntityManager em) {} 
    
    public void onEntityChanged(Entity e) {}
    
    public void update(float dt) {} 
    
    public void render() {} 
    
    public boolean isEnabled() {
        return enabled; 
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled; 
    }
    
    public int getPriority() {
        return priority; 
    }
    
    public EntityManager getManager() {
        return manager; 
    }
    
    final void setManager(EntityManager em) {
        if (this.manager != em) {
            this.manager = em; 
            onManagerSet(em); 
        }
    }
    
}
