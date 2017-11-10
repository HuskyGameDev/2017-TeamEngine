package oasis.core;

import oasis.core.jogl3.Jogl3Engine;

/** 
 * Engine configuration 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Config {

    public static final int UNLIMITED_FPS = -1; 
    
    /**
     * Type of engine 
     */
    public Class<? extends Engine> engine = Jogl3Engine.class;
    
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
