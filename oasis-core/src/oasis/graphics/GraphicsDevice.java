package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    VertexBuffer createVertexBuffer(VertexFormat format, int vertCount, BufferUsage usage); 
    
    IndexBuffer createIndexBuffer(int indCount, BufferUsage usage); 
    
    Shader createShader(String vs, String fs); 
    
    Geometry createGeometry(IndexBuffer ib, VertexBuffer... vbs); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void drawGeometry(Geometry geom); 
    
}
