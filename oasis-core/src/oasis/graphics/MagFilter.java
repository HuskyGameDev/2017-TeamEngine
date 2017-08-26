package oasis.graphics;

/**
 * Magnification filter for textures. Used 
 * when an image is drawn larger than it 
 * actually is
 * 
 * @author Nicholas Hamilton
 *
 */
public enum MagFilter {
    
    /**
     * Sharp, pixelated sampling of textures. 
     * Looks good for pixel art
     */
    NEAREST, 
    
    /**
     * Blurs the sampling of textures. Looks good 
     * on higher-res images
     */
    LINEAR;
    
}
