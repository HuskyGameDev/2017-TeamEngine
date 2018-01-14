package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender(); 
    
    void postRender(); 
    
    void applyState(GraphicsState state); 
    
    void setRenderTarget(RenderTarget rt); 
    
    void useShader(Shader shader); 
    
    void drawMesh(Mesh mesh, int submesh);

    void clearBuffers(Vector4 colorBuffer, boolean depthBuffer);

    Mesh createMesh(); 
    
    Texture2D createTexture2D(TextureFormat format, int width, int height); 

    RenderTexture createRenderTexture(TextureFormat format, int width, int height); 
    
    Shader createShader(String vs, String fs); 
    
    RenderTarget createRenderTarget(); 
    
}
