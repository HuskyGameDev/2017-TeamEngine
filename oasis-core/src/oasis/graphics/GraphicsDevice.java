package oasis.graphics;

public interface GraphicsDevice {

    // resource creation 
    
    Shader createShader(String vertex, String fragment); 
    FrameBuffer createFrameBuffer(int width, int height); 
    VertexBuffer createVertexBuffer(VertexFormat fmt, BufferUsage usage); 
    IndexBuffer createIndexBuffer(BufferUsage usage); 
    VertexArray createVertexArray(); 
    Texture2D createTexture2D(TextureFormat format, int width, int height); 
    
    // getters
    
    int getWidth(); 
    int getHeight(); 
    
    FrameBuffer getFrameBuffer(); 
    VertexArray getVertexArray(); 
    Shader getShader(); 
    Texture getTexture(int index); 
    int getMaxTextureCount(); 
    
    // setters 
    
    void setFrameBuffer(FrameBuffer fbo); 
    void setShader(Shader shader); 
    void setVertexArray(VertexArray vao); 
    void setTexture(int index, Texture texture); 
    
    // rendering
    
    // TODO blending
    
    void clearScreen(ColorRgba color); 
    
    void drawArrays(Primitive prim, int start, int count); 
    void drawElements(Primitive prim, int start, int count); 

}
