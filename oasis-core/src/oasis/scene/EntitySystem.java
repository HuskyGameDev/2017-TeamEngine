package oasis.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class EntitySystem {
    
    public static final int DEFAULT_PRIORITY = 1000; 
    
    protected final List<Entity> entities = new ArrayList<>(); 

    private Scene scene; 
    private Matcher matcher; 
    
    private boolean update, render; 
    private int priority; 
    
    void setScene(Scene scene) {
        this.scene = scene; 
        onSceneSet(scene); 
    }
    
    public EntitySystem(int priority, boolean update, boolean render, Matcher matcher) {
        this.matcher = matcher == null ? Matcher.empty() : matcher; 
        this.update = update; 
        this.render = render; 
        this.priority = priority; 
    }
    
    public EntitySystem(int priority, Matcher matcher) {
        this(priority, true, true, matcher); 
    }
    
    @SafeVarargs
    public EntitySystem(int priority, Class<? extends EntityComponent>... require) {
        this(priority, Matcher.require(require).create());  
    }
    
    @SafeVarargs
    public EntitySystem(Class<? extends EntityComponent>... require) {
        this(DEFAULT_PRIORITY, Matcher.require(require).create());  
    }
    
    protected void onSceneSet(Scene scene) {} 
    
    public void preUpdate(float dt) {}

    public void update(Entity e, float dt) {} 
    
    public void postUpdate(float dt) {} 
    
    public void preRender() {} 
    
    public void render(Entity e) {} 
    
    public void postRender() {} 
    
    public int getPriority() {
        return priority; 
    }
    
    public Scene getScene() {
        return scene; 
    }
    
    public void checkEntity(Entity e) {
        entities.remove(e); 
        
        if (matcher.matches(e)) {
            entities.add(e); 
        }
    }
    
    public void removeEntity(Entity e) {
        entities.remove(e); 
    }
    
    public void update(float dt) {
        if (!update) return; 
        
        preUpdate(dt); 
        for (Entity e : entities) {
            update(e, dt); 
        }
        postUpdate(dt); 
    }
    
    public void render() {
        if (!render) return; 
        
        preRender(); 
        for (Entity e : entities) {
            render(e); 
        }
        postRender(); 
    }
    
}
