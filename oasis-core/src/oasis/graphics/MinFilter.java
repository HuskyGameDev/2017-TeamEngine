package oasis.graphics;

/**
 * Minification filter for textures. Used when an image is drawn smaller than it
 * actually is
 * 
 * @author Nicholas Hamilton
 *
 */
public enum MinFilter {

    /**
     * Pixelated
     */
    NEAREST,

    /**
     * Linearly interpolated, blurry
     */
    LINEAR,

    /**
     * Pixelated, with mipmaps
     */
    NEAREST_MIPMAP_NEAREST,

    /**
     * Pixelated, with linearly interpolated mipmaps
     */
    NEAREST_MIPMAP_LINEAR,

    /**
     * Linearly interpolated, with mipmaps
     */
    LINEAR_MIPMAP_NEAREST,

    /**
     * Linearly interpolated, with linearly interpolated mipmaps. Also known as
     * Tri-linear filtering
     */
    LINEAR_MIPMAP_LINEAR;

}