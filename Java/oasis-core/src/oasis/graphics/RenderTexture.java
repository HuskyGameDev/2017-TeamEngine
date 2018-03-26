package oasis.graphics;

import oasis.core.Oasis;

/**
 * 
 * Texture that can be rendered to 
 * 
 * @author Nicholas Hamilton 
 *
 */
public abstract class RenderTexture extends Texture {

    public static RenderTexture create(TextureFormat format, int width, int height) {
        return Oasis.getGraphicsDevice().createRenderTexture(format, width, height); 
    }
    
    public RenderTexture(TextureFormat format, int width, int height) {
        super(format, width, height);
    }
    
    public TextureType getType() {
        return TextureType.RENDER_TEXTURE; 
    }

}
