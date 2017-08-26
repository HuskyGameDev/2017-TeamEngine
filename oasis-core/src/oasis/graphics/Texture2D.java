package oasis.graphics;

/**
 * 2D texture interface. 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Texture2D extends Texture {

    /**
     * Get the data of the texture as a row major array of ColorRgba
     * 
     * @return Array of colors 
     */
    ColorRgba[] getPixels(); 
    
    /**
     * Get the data of the texture as a row major array of packed int
     * in the order RGBA 
     * 
     * @return Array of colors 
     */
    int[] getPixelsRgba(); 
    
    /**
     * Get the data of the texture as a row major array of packed int
     * in the order ARGB 
     * 
     * @return Array of colors 
     */
    int[] getPixelsArgb(); 
    
    /**
     * Set the data of the texture from a row major array of ColorRgba 
     * 
     * @param data Texture data 
     */
    void setPixels(ColorRgba[] data); 
    
    /**
     * Set the data of the texture from a row major array of packed int
     * in the order RGBA 
     * 
     * @param rgba Texture data 
     */
    void setPixelsRgba(int[] rgba); 
    
    /**
     * Set the data of the texture from a row major array of packed int
     * in the order ARGB 
     * 
     * @param argb Texture data 
     */
    void setPixelsArgb(int[] argb); 
    
}
