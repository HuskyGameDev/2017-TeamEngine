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
     * 
     * @param dt Time since last update 
     */
    void update(float dt);
    
    /**
     * Called when application is rendering 
     */
    void render();
    
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
