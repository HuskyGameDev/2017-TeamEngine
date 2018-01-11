package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    void assignNativeResource(VertexBuffer vb); 
    
    void assignNativeResource(IndexBuffer ib); 
    
    void assignNativeResource(Geometry g); 
    
    void assignNativeResource(Shader s); 
    
    void assignNativeResource(Texture2D t); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void drawGeometry(Geometry geom); 
    
}
