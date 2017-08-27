package oasis.graphics;

/**
 * Wrap mode for sampling textures 
 * 
 * @author Nicholas Hamilton
 *
 */
public enum WrapMode {
    
    /**
     * When sampling past the edge of a texture, return 
     * the color of the edge of the texture 
     */
    CLAMP_EDGE, 
    
    /**
     * When sampling past the edge of a texture, return 
     * value of the coordinate % 1 
     */
    REPEAT;
    
}
