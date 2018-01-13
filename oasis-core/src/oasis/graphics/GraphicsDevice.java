package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    void requestInternal(VertexBuffer vb); 
    
    void requestInternal(IndexBuffer ib); 
    
    void requestInternal(Geometry g); 
    
    void requestInternal(Shader s); 
    
    void requestInternal(Texture2D t); 
    
    void requestInternal(RenderTexture t); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void setRenderTexture(RenderTexture rt); 
    
    void drawGeometry(Geometry geom); 
    
}
