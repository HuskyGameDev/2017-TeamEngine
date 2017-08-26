package oasis.graphics;

/**
 * Array of 2D textures. 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Texture2DArray extends Texture {

    /**
     * Get the number of 2D textures in the array 
     * @return
     */
    int getLayerCount(); 
    
    /**
     * Get the data of the texture as a row major array of ColorRgba
     * 
     * @param Index of texture array 
     * @return Array of colors 
     */
    ColorRgba[] getPixels(int layer); 
    
    /**
     * Get the data of the texture as a row major array of packed int
     * in the order RGBA 
     * 
     * @param Index of texture array 
     * @return Array of colors 
     */
    int[] getPixelsRgba(int layer); 
    
    /**
     * Get the data of the texture as a row major array of packed int
     * in the order ARGB 
     * 
     * @param Index of texture array 
     * @return Array of colors 
     */
    int[] getPixelsArgb(int layer); 
    
    /**
     * Set the data of the texture from a row major array of ColorRgba 
     * 
     * @param Index of texture array 
     * @param data Texture data 
     */
    void setPixels(int layer, ColorRgba[] data); 
    
    /**
     * Set the data of the texture from a row major array of packed int
     * in the order RGBA 
     * 
     * @param Index of texture array 
     * @param rgba Texture data 
     */
    void setPixelsRgba(int layer, int[] rgba); 
    
    /**
     * Set the data of the texture from a row major array of packed int
     * in the order ARGB 
     * 
     * @param Index of texture array 
     * @param argb Texture data 
     */
    void setPixelsArgb(int layer, int[] argb); 
    
}
