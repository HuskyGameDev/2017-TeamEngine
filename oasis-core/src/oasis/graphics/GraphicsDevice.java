package oasis.graphics;

public interface GraphicsDevice {

    // rendering
    
    void setClearColor(ColorRgba color); 
    
    void clearScreen(); 
    
    void setFrameBuffer(FrameBuffer frameBuffer); 
    
    void setShader(Shader shader); 
    
    void setVertexArray(VertexArray vertArray); 
    
    void drawArrays(PrimitiveType type, int start, int count); 
    
    // resource util
    
    void update(Texture2D texture); 
    
    void update(Shader shader); 
    
    void update(VertexArray vertArray); 
    
    void update(VertexBuffer vertBuffer); 
    
    void update(IndexBuffer indexBuffer); 

    void update(FrameBuffer framebuffer); 
    
}
