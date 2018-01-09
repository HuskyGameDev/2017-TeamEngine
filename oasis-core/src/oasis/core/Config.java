package oasis.core;

/** 
 * Engine configuration 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Config {

    public static final float UNLIMITED_FPS = -1.0f; 
    
    /**
     * Type of engine 
     */
    public BackendType backend = BackendType.AUTO; 
    
    /**
     * Starting frames per second 
     */
    public float fps = 60.0f;
    
    /**
     * Starting updates per second 
     */
    public float ups = 60.0f;
    
    /**
     * Starting width 
     */
    public int width = 800; 
    
    /**
     * Starting height
     */
    public int height = 600; 
    
}
