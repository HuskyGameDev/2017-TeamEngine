package oasis.core;

/**
 * Contains callbacks used by the game engine 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface EngineListener {

    /**
     * Called when the engine is initializing 
     */
    void onInit();
    
    /**
     * Called when the engine is updating 
     * 
     * @param dt Time since last update 
     */
    void onUpdate(float dt);
    
    /**
     * Called when the engine is rendering 
     */
    void onRender();
    
    /**
     * Called when the engine is exiting 
     */
    void onExit();
    
    /**
     * Called when the window is trying to be closed 
     * 
     * @return Whether the window should close or not 
     */
    boolean onCloseAttempt(); 
    
}
