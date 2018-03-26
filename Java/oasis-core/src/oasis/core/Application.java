package oasis.core;

/**
 * Basic class to make a game from 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Application {

    /**
     * Called when application is initializing 
     */
    void init();
    
    /**
     * Called when application is updating 
     * before the scene is updated
     * 
     * @param dt Time since last update 
     */
    void preUpdate(float dt);

    /**
     * Called when application is updating 
     * after the scene is updated
     * 
     * @param dt Time since last update 
     */
    void postUpdate(float dt);
    
    /**
     * Called when application is rendering 
     * before the scene is rendered
     */
    void preRender();

    /**
     * Called when application is rendering 
     * after the scene is rendered
     */
    void postRender();
    
    /**
     * Called when application is exiting 
     */
    void exit();
    
    /**
     * Called when window is trying to close 
     * 
     * @return Whether window should close 
     */
    boolean closeAttempt();
    
}
