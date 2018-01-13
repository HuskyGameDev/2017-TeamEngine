package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    void requestInternalVertexBuffer(VertexBuffer vb); 
    
    void requestInternalIndexBuffer(IndexBuffer ib); 
    
    void requestInternalGeometry(Geometry g); 
    
    void requestInternalShader(Shader s); 
    
    void requestInternalTexture2D(Texture2D t); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void drawGeometry(Geometry geom); 
    
}
