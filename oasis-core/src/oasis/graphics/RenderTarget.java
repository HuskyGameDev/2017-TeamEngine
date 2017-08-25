package oasis.graphics;

import oasis.core.Disposable;

public interface RenderTarget extends Disposable {

    void dispose(boolean textures); 
    
    boolean isComplete(); 
    
	int getWidth(); 
	int getHeight(); 
	
	int getMaxColorTextureCount(); 
	Texture getColorTexture(int index); 
	Texture getDepthTexture(); 
	
	void setColorTexture(int index, Texture2D texture); 
	void setColorTexture(int index, TextureCube texture, TextureCube.Face face); 
	void setColorTexture(int index, Texture3D texture, int layer); 
	void setColorTexture(int index, Texture2DArray texture, int layer); 
	
	void setDepthTexture(Texture2D texture); 
	void setDepthTexture(TextureCube texture, TextureCube.Face face); 
	void setDepthTexture(Texture2D texture, int layer); 
	void setDepthTexture(Texture2DArray texture, int layer); 
	
}
