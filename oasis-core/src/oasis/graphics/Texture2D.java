package oasis.graphics;

import oasis.core.Oasis;
import oasis.core.OasisException;

public abstract class Texture2D extends Texture {

    public static Texture2D create(TextureFormat format, int width, int height) {
        return Oasis.getGraphicsDevice().createTexture2D(format, width, height); 
    }
    
    public Texture2D(TextureFormat format, int width, int height) {
        super(format, width, height); 
        
        if (format.isDepthFormat()) {
            throw new OasisException("Texture2D cannot have a depth format"); 
        }
    }
    
    public abstract void upload(); 
    
    public abstract int[] getPixels(); 
    
    public abstract void setPixels(int[] rgba); 
    
    public TextureType getType() {
        return TextureType.TEXTURE_2D; 
    }

}
