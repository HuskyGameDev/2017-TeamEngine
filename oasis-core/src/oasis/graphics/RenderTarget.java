package oasis.graphics;

import oasis.core.Disposable;

/**
 * Holds textures to render to 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface RenderTarget extends Disposable {

    /**
     * Disposes the render target, and optionally 
     * any textures attached to it 
     * 
     * @param textures Should attached textures be disposed too 
     */
    void dispose(boolean textures); 
    
    /**
     * Checks if the render target has at least one texture attached 
     * 
     * @return If render target is complete 
     */
    boolean isComplete(); 
    
    /**
     * Get render target width 
     * 
     * @return Render target width 
     */
	int getWidth(); 
	
	/**
	 * Get render target height 
	 * 
	 * @return Render target height 
	 */
	int getHeight(); 
	
	/**
	 * Get the maximum amount of color textures that 
	 * can be attached 
	 * 
	 * @return Maximum color textures 
	 */
	int getMaxColorTextureCount(); 
	
	/**
	 * Get a color texture attached to the render target 
	 * 
	 * @param index Index of the texture 
	 * @return Color texture or null
	 */
	Texture getColorTexture(int index); 
	
	/**
	 * Get the depth texture attached to the render target 
	 * 
	 * @return Depth texture or null
	 */
	Texture getDepthTexture(); 
	
	/**
	 * Set a color texture of the render target. Dimensions must 
	 * be the same as the render target. Format must not be a 
	 * depth format 
	 * 
	 * @param index Index of the texture 
	 * @param texture Texture to attach 
	 */
	void setColorTexture(int index, Texture2D texture); 
	
	/**
     * Set the depth texture of the render target. Dimensions must 
     * be the same as the render target. Format must be a 
     * depth format  
     * 
     * @param texture Texture to attach 
     */
	void setDepthTexture(Texture2D texture); 
	
}
