package oasis.graphics;

import oasis.core.Disposable;

/**
 * Base interface for textures. 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Texture extends Disposable {

    /**
     * Pixel format 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum Format {
        
        /**
         * Packed int with 8 bits for each of red, green, blue, alpha, in that order 
         */
        RGBA8(false), 
        
        /**
         * Half-floats (16 bit) values for red, green, blue, alpha 
         */
        RGBA16F(false), 
        
        /**
         * 32 bit float values for red, green, blue, alpha 
         */
        RGBA32F(false), 
        
        /**
         * 24 bit depth values
         */
        DEPTH24(true), 
        
        /**
         * 24 bit depth values with 8 bits for stencil 
         */
        DEPTH24STENCIL8(true), 
        
        /**
         * 32 bit depth values
         */
        DEPTH32(true);

        private final boolean depth;

        private Format(boolean depth) {
            this.depth = depth;
        }

        /**
         * Check if format is a depth format 
         * 
         * @return If format is depth format 
         */
        public boolean isDepthFormat() {
            return depth;
        }
        
        /** 
         * Check if format is a depth and stencil format 
         * 
         * @return If format is depth-stencil format 
         */
        public boolean isDepthStencilFormat() {
            return this == DEPTH24STENCIL8; 
        }
        
    }
    
    /**
     * Type of texture 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum Type {
        
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
    }
    
    // getters 
    
    /**
     * Checks if texture data was lost. 
     * This only matters if the texture was 
     * attached to a RenderTarget. If the texture
     * data was manually set then you do not have 
     * to worry. However, if it was attached to a 
     * RenderTarget, you will have to re-render 
     * the scene the texture had on it 
     * 
     * @return If texture data was lost 
     */
    boolean isLost(); 
    
    /**
     * Get texture width 
     * 
     * @return Texture width 
     */
    int getWidth(); 
    
    /**
     * Get texture height 
     * 
     * @return Texture height 
     */
    int getHeight(); 
    
    /**
     * Get texture type 
     * 
     * @return Texture type 
     */
    Type getType(); 
    
    /**
     * Get texture format 
     * 
     * @return Texture format 
     */
    Format getFormat(); 
    
    /**
     * Get texture min filter 
     * 
     * @return Texture min filter 
     */
    MinFilter getMinFilter(); 
    
    /**
     * Get texture mag filter 
     * 
     * @return Texture mag filter 
     */
    MagFilter getMagFilter(); 
    
    /**
     * Get wrap mode for horizontal direction of texture 
     * 
     * @return Horizontal wrap mode 
     */
    WrapMode getWrapS(); 
    
    /**
     * Get wrap mode for vertical direction of texture 
     * 
     * @return Vertical wrap mode 
     */
    WrapMode getWrapT(); 
    
    /**
     * Get number of mipmaps 
     * 
     * @return Number of mipmaps 
     */
    int getMipmaps(); 
    
    // setters 
    
    /**
     * Set min filter of texture 
     * 
     * @param min New min filter 
     */
    void setMinFilter(MinFilter min);
    
    /**
     * Set mag filter of texture 
     * 
     * @param mag New mag filter 
     */
    void setMagFilter(MagFilter mag);
    
    /**
     * Set min and mag filters of texture 
     * 
     * @param min Min filter 
     * @param mag Mag filter 
     */
    void setFilters(MinFilter min, MagFilter mag); 
    
    /**
     * Set number of mipmaps. The texture will automatically 
     * manage mipmaps for you. [levels] must be greater than 
     * or equal to 0
     * 
     * @param levels Number of mipmaps 
     */
    void setMipmaps(int levels); 
    
    /**
     * Set horizontal wrap mode of the texture 
     * 
     * @param s Horizontal wrap mode 
     */
    void setWrapS(WrapMode s); 
    
    /**
     * Set vertical wrap mode of the texture 
     * 
     * @param t Vertical wrap mode 
     */
    void setWrapT(WrapMode t); 
    
    /**
     * Set horizontal and vertical wrap modes of the texture 
     * 
     * @param s Horizontal wrap mode 
     * @param t Vertical wrap mode 
     */
    void setWrapModes(WrapMode s, WrapMode t); 
    
}
