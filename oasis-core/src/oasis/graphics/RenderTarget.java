package oasis.graphics;

import oasis.core.Disposable;

public interface RenderTarget extends Disposable {

    void dispose(boolean textures); 
    
	int getWidth(); 
	int getHeight(); 
	
	int getMaxColorTextureCount(); 
	Texture getColorTexture(int index); 
	Texture getDepthTexture(); 
	
	void setColorTexture(int index, Texture2D texture); 
	void setDepthTexture(Texture2D texture); 
	
}
