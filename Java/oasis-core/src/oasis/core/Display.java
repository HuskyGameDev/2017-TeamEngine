package oasis.core;

/**
 * The window/display 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Display {

    /**
     * Swap display buffer
     */
    void swapBuffers(); 
    
    /**
     * Get the display's title 
     * 
     * @return display title 
     */
    String getTitle();
    
    /**
     * Set the display's title
     * 
     * @param title New display title 
     */
    void setTitle(String title);
    
    /**
     * Get the display X position 
     * 
     * @return display X position
     */
    int getX(); 
    
    /**
     * Get the display Y position 
     * 
     * @return display Y position
     */
    int getY(); 
    
    /**
     * Get the display screen width 
     * 
     * @return display width 
     */
    int getWidth();
    
    /**
     * Get the display screen height 
     * 
     * @return display height 
     */
    int getHeight();
    
    /**
     * Set the display screen size 
     * 
     * @param width New display width 
     * @param height New display height 
     */
    void setSize(int width, int height);
    
    /**
     * Get the display screen aspect ratio (width / height) 
     * @return display aspect ratio 
     */
    float getAspectRatio();
    
    /**
     * Show the display
     */
    void show();
    
    /**
     * Hide the display 
     */
    void hide();
    
    /**
     * Check if display is visible 
     * 
     * @return If display is visible 
     */
    boolean isVisible();
    
    /**
     * Check is display is resizable by the user
     * 
     * @return If display is resizable 
     */
    boolean isResizable();
    
    /**
     * Set if display should be resizable by the user
     * 
     * @param resize If display should be resizable 
     */
    void setResizable(boolean resize);
    
    /**
     * Check if display is fullscreen 
     * 
     * @return If display is fullscreen 
     */
    boolean isFullscreen();
    
    /**
     * Check if the display is able to go fullscreen 
     * 
     * @return If the display is able to go fullscreen 
     */
    boolean canFullscreen();
    
    /**
     * Set whether the display should be fullscreen 
     * 
     * @param full Should display be fullscreen 
     */
    void setFullscreen(boolean full);
    
    /**
     * Check if display is trying to close 
     * 
     * @return If display is trying to close 
     */
    boolean shouldClose();
    
}
