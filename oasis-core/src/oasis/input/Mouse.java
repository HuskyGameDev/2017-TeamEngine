package oasis.input;

/**
 * Mouse interface 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Mouse {

    /**
     * Button press or release event 
     * 
     * @author Nicholas Hamilton
     *
     */
    public class Event {
        public final int button; 
        public final boolean down; 
        
        public Event(int button, boolean down) {
            this.button = button; 
            this.down = down; 
        }
    }
    
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
     * Check if any events have happened since last update 
     * 
     * @return If mouse has events queued 
     */
    boolean hasNextEvent(); 
    
    /**
     * Gets the next queued event 
     * 
     * @return Next event or null 
     */
    Event nextEvent(); 
    
}
