package oasis.scene;

public abstract class EntityComponent {

    private Entity entity; 
    
    void setEntity(Entity e) {
        if (entity == null) {
            if (e == null) return; // no entity before or after 
            
            // new entity 
            entity = e; 
            attach(); 
        }
        else {
            // replace
            detach(); 
            entity = e; 
            
            if (e != null) {
                entity = e; 
                attach(); 
            }
        }
    }
    
    public Entity getEntity() {
        return entity; 
    }
    
    public Scene getScene() {
        return entity.getScene(); 
    }
    
    protected void attach() {}
    
    protected void detach() {} 
    
    // protected abstract void copy(Entity e); 
    
}
