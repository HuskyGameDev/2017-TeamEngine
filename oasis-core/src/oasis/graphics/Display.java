package oasis.graphics;

/**
 * The display or window 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Display {

    /**
     * Get the window's title 
     * 
     * @return Window title 
     */
    String getTitle();
    
    /**
     * Set the window's title
     * 
     * @param title New window title 
     */
    void setTitle(String title);
    
    /**
     * Get the window screen width 
     * 
     * @return Window width 
     */
    int getWidth();
    
    /**
     * Get the window screen height 
     * 
     * @return Window height 
     */
    int getHeight();
    
    /**
     * Set the window screen size 
     * 
     * @param width New window width 
     * @param height New window height 
     */
    void setSize(int width, int height);
    
    /**
     * Get the window screen aspect ratio (width / height) 
     * @return Window aspect ratio 
     */
    float getAspectRatio();
    
    /**
     * Show the window
     */
    void show();
    
    /**
     * Hide the window 
     */
    void hide();
    
    /**
     * Check if window is visible 
     * 
     * @return If window is visible 
     */
    boolean isVisible();
    
    /**
     * Check is window is resizable by the user
     * 
     * @return If window is resizable 
     */
    boolean isResizable();
    
    /**
     * Set if window should be resizable by the user
     * 
     * @param resize If window should be resizable 
     */
    void setResizable(boolean resize);
    
    /**
     * Check if window is fullscreen 
     * 
     * @return If window is fullscreen 
     */
    boolean isFullscreen();
    
    /**
     * Check if the window is able to go fullscreen 
     * 
     * @return If the window is able to go fullscreen 
     */
    boolean canFullscreen();
    
    /**
     * Set whether the window should be fullscreen 
     * 
     * @param full Should window be fullscreen 
     */
    void setFullscreen(boolean full);
    
    /**
     * Check if window is trying to close 
     * 
     * @return If window is trying to close 
     */
    boolean shouldClose();
    
}
