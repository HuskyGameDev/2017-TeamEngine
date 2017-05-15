package nhamil.oasis.graphics;

import nhamil.oasis.core.Disposable;

public interface Framebuffer extends Disposable {

    int getWidth();
    int getHeight();
    
    boolean hasTexture();
    boolean hasDepthAttachment();
    boolean hasStencilAttachment();
    
    Texture getTexture();
    
}
