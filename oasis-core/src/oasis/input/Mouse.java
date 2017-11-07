package oasis.input;

/**
 * Mouse interface 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Mouse {

    public enum ScrollDirection {
        NONE, 
        UP, 
        DOWN; 
    }
    
    /**
     * Sets mouse position (origin bottom left) 
     * 
     * This resets dx, dy
     * 
     * @param x
     * @param y
     */
    void setPosition(float x, float y); 
    
    /**
     * Puts mouse in the center of the window
     * 
     * This resets dx, dy
     */
    void center(); 
    
    /**
     * Get mouse X position, with origin in the bottom left 
     * 
     * @return Mouse x position 
     */
    float getX(); 
    
    /** 
     * Get mouse Y position, with origin in the bottom left 
     * 
     * @return Mouse y position 
     */
    float getY(); 
    
    /**
     * Get change in mouse X position, with origin in bottom left 
     */
    float getDx(); 
    
    /**
     * Get change in mouse Y position, with origin in bottom left
     */
    float getDy(); 
    
    /**
     * Check if a mouse button is down
     * 
     * @param button Button index 
     * @return If button is pressed 
     */
    boolean isButtonDown(int button);
    
    /**
     * Check if a mouse button was just pressed during this update 
     * 
     * @param button Button index 
     * @return If button was just pressed 
     */
    boolean isButtonJustDown(int button); 
    
    /**
     * Get the scroll wheel direction this frame 
     */
    ScrollDirection getScroll(); 
    
}
