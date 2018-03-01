package oasis.core;

import oasis.input.Keyboard;

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
        Oasis.getGraphics().begin(); 
        preRenderGame(); 
    }

    @Override
    public final void postRender() {
        postRenderGame(); 
        Oasis.getGraphics().finish(); 
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
