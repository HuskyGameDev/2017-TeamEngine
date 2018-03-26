package oasis.graphics;
/**
 * 
 * Blending modes 
 * 
 * @author Nicholas Hamilton
 *
 */
public enum BlendMode {
    
    /**
     * All color 
     */
    ONE, 
    
    /**
     * No color 
     */
    ZERO, 
    
    /**
     * Color multiplied by the source alpha 
     */
    SRC_ALPHA, 
    
    /**
     * Color multiplied by (1 - a), where a = source alpha  
     */
    ONE_MINUS_SRC_ALPHA, 
    
}