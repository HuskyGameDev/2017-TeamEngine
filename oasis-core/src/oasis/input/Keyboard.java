package oasis.input;

/**
 * Keyboard interface 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Keyboard {

    /**
     * Key press or release event 
     * 
     * @author Nicholas Hamilton
     *
     */
    public class Event {
        public final int key; 
        public final boolean down; 
        
        public Event(int key, boolean down) {
            this.key = key; 
            this.down = down; 
        }
    }
    
    /**
     * Checks if a key is down
     * 
     * @param keycode Key code
     * @return If key is currently down 
     */
    boolean isKeyDown(int keycode); 
    
    /**
     * Checks if a key was just pressed during 
     * this update 
     * 
     * @param keycode Key code 
     * @return If key was just pressed 
     */
    boolean isKeyJustDown(int keycode); 
    
    /**
     * Check if any events have happened since last update 
     * 
     * @return If keyboard has events queued 
     */
    boolean hasNextEvent(); 
    
    /**
     * Gets the next queued event 
     * 
     * @return Next event or null 
     */
    Event nextEvent(); 
    
}
