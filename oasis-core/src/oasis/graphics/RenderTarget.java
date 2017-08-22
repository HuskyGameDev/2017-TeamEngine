package oasis.graphics;

import oasis.core.Disposable;

public interface RenderTarget extends Disposable {

    void dispose(boolean textures); 
    
	int getWidth(); 
	int getHeight(); 
	
	int getMaxColorTextureCount(); 
	Texture getColorTexture(int index); 
	Texture getDepthTexture(); 
	
	void setColorTexture(int index, Texture texture); 
	void setDepthTexture(Texture texture); 
	
}
