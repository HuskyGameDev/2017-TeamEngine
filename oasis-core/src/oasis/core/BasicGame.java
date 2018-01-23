package oasis.core;

import oasis.entity.Camera;
import oasis.entity.EntityManager;
import oasis.entity.Light;
import oasis.entity.LightRenderer;
import oasis.entity.MeshContainer;
import oasis.entity.MeshRenderer;
import oasis.entity.Transform;
import oasis.graphics.Graphics;

public abstract class BasicGame implements Application {

    protected final EntityManager entityManager = new EntityManager(); 
    
    public BasicGame() {} 
    
    public abstract void initGame(); 
    
    public void preUpdateGame(float dt) {} 
    public void postUpdateGame(float dt) {} 
    
    public void preRenderGame() {} 
    public void postRenderGame() {} 
    
    public void preExitGame() {} 
    public void postExitGame() {} 
    
    @Override
    public final void init() {
        Oasis.getDisplay().setResizable(true); 
        
        entityManager.registerComponent(Transform.class); 
        entityManager.registerComponent(MeshContainer.class);
        entityManager.registerComponent(Light.class);
        entityManager.registerComponent(Camera.class); 
        
        entityManager.addBehavior(new MeshRenderer()); 
        entityManager.addBehavior(new LightRenderer()); 
        
        initGame(); 
    }
    
    @Override
    public final void update(float dt) {
        preUpdateGame(dt); 
        
        entityManager.update(dt); 
        
        postUpdateGame(dt); 
    }
    
    @Override
    public final void render() {
        Graphics g = Oasis.getGraphics(); 
        g.begin(); 
        preRenderGame(); 
        
        entityManager.render(); 
        
        postRenderGame(); 
        g.finish(); 
    }
    
    @Override
    public final void exit() {
        preExitGame(); 
        postExitGame(); 
    }
    
    @Override
    public final boolean closeAttempt() {
        return true; 
    }
    
}
