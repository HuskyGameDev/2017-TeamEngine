package oasis.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class EntitySystem {
    
    protected final List<Entity> entities = new ArrayList<>(); 

    private Scene scene; 
    private Matcher matcher; 
    
    void setScene(Scene scene) {
        this.scene = scene; 
    }
    
    public EntitySystem(Matcher matcher) {
        this.matcher = matcher == null ? Matcher.empty() : matcher; 
    }
    
    public void preUpdate(float dt) {}

    public void update(float dt) {} 
    
    public void postUpdate(float dt) {} 
    
    public void preRender() {} 
    
    public void render() {} 
    
    public void postRender() {} 
    
    public Scene getScene() {
        return scene; 
    }
    
    public void updateEntity(Entity e) {
        entities.remove(e); 
        
        if (matcher.matches(e)) {
            entities.add(e); 
        }
    }
    
    
    
}
