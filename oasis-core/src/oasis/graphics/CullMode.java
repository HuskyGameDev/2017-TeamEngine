package oasis.graphics;

/**
 * Polygon culling mode 
 * 
 * @author Nicholas Hamilton
 *
 */
public enum CullMode {
    
    /**
     * No culling 
     */
    NONE, 
    
    /**
     * Cull faces that are in clockwise order 
     */
    CW, 
    
    /**
     * Cull faces that are in counter-clockwise order 
     */
    CCW; 
}
