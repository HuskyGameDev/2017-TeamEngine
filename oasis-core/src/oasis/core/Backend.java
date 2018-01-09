package oasis.core;

import oasis.graphics.GraphicsDevice;
import oasis.input.Keyboard;
import oasis.input.Mouse;

public interface Backend {

    void applyConfig(Config config); 
    
    void preInit(); 
    void postInit(); 
    
    void preUpdate(float dt); 
    void postUpdate(float dt); 
    
    void preRender(); 
    void postRender(); 
    
    void preExit(); 
    void postExit(); 
    
    void runOnMainThread(Runnable r); 
    
    DirectBufferAllocator getDirectBufferAllocator(); 
    
    Display getDisplay(); 
    
    Keyboard getKeyboard(); 
    
    Mouse getMouse(); 
    
    GraphicsDevice getGraphicsDevice(); 
    
}
