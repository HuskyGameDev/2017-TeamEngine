package oasis.graphics;

/**
 * Type of texture
 * 
 * @author Nicholas Hamilton
 *
 */
public enum TextureType {

    /**
     * 2D texture
     */
    TEXTURE_2D,

    /**
     * Cubemap (6 2D textures)
     */
    TEXTURE_CUBE,

    /**
     * Array of 2D textures
     */
    TEXTURE_2D_ARRAY,

    /**
     * 3D texture
     */
    TEXTURE_3D,

    /**
     * Render texture
     */
    RENDER_TEXTURE;

}