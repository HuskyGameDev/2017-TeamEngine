package oasis.core;

import oasis.core.jogl.Jogl3Engine;

/** 
 * Engine configuration 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Config {

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
    
}
