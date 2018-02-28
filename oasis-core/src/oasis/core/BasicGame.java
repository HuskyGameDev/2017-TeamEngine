package oasis.core;

import oasis.entity.Camera;
import oasis.entity.EntityManager;
import oasis.entity.Light;
import oasis.entity.LightRenderer;
import oasis.entity.MeshContainer;
import oasis.entity.MeshRenderer;
import oasis.entity.Transform;
import oasis.graphics.Graphics;
import oasis.input.Keyboard;
import oasis.math.Vector3;

public abstract class BasicGame implements Application {

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
        initGame(); 
    }
    
    @Override
    public final void preUpdate(float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        if (keys.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Oasis.stop();
        }
        
        preUpdateGame(dt); 
    }
    
    @Override
    public final void postUpdate(float dt) {
        postUpdateGame(dt); 
    }
    
    @Override
    public final void preRender() {
        preRenderGame(); 
    }

    @Override
    public final void postRender() {
        postRenderGame(); 
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
