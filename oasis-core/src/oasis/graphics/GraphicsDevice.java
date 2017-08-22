package oasis.graphics;

public interface GraphicsDevice {

    // resource creation 
    
    Shader createShader(String vertex, String fragment); 
    RenderTarget createRenderTarget(int width, int height); 
    RenderTarget createRenderTarget(int width, int height, TextureFormat depthBuffer, TextureFormat... colorBuffers); 
    VertexBuffer createVertexBuffer(VertexFormat fmt, BufferUsage usage); 
    IndexBuffer createIndexBuffer(BufferUsage usage); 
    VertexArray createVertexArray(); 
    Texture2D createTexture2D(TextureFormat format, int width, int height); 
    
    // getters
    
    int getScreenWidth(); 
    int getScreenHeight(); 
    int getWidth(); 
    int getHeight(); 
    
    RenderTarget getRenderTarget(); 
    VertexArray getVertexArray(); 
    Shader getShader(); 
    Texture getTexture(int index); 
    int getMaxTextureCount(); 
    
    boolean isDepthTestEnabled(); 
    BlendMode getSourceBlendMode(); 
    BlendMode getDestBlendMode(); 
    
    // setters 
    
    void setRenderTarget(RenderTarget fbo); 
    void setShader(Shader shader); 
    void setVertexArray(VertexArray vao); 
    void setTexture(int index, Texture texture); 
    
    void setDepthTestEnabled(boolean enabled); 
    void setBlendMode(BlendMode src, BlendMode dst); 
    
    // rendering
    
    void clearScreen(ColorRgba color); 
    
    void drawArrays(Primitive prim, int start, int count); 
    void drawElements(Primitive prim, int start, int count); 

}
