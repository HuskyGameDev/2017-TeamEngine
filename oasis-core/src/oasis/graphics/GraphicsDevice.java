package oasis.graphics;

/**
 * Main interface for rendering graphics to the screen 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface GraphicsDevice {

    // resource creation 
    
    /**
     * Creates a shader. This renders things based on
     * geometry given to the graphics device 
     * 
     * @param vertex Vertex shader source code 
     * @param fragment Fragment shader source code 
     * @return Shader resource 
     */
    Shader createShader(String vertex, String fragment); 
    
    /**
     * Creates a render target. The graphics device
     * can render to textures set in this 
     * 
     * @param width Width of the render target 
     * @param height Height of the render target 
     * @return Render target resource
     */
    RenderTarget createRenderTarget(int width, int height); 
    
    /**
     * Creates a vertex buffer. This holds vertex information
     * 
     * @param fmt Format of vertices in the buffer 
     * @param usage Usage type of the buffer 
     * @return Vertex buffer resource 
     */
    VertexBuffer createVertexBuffer(VertexFormat fmt, BufferUsage usage);
    
    /**
     * Creates an index buffer. This holds information on the order
     * vertices should be rendered 
     * 
     * @param usage Usage type of the buffer 
     * @return Index buffer resource 
     */
    IndexBuffer createIndexBuffer(BufferUsage usage); 
    
    /**
     * Creates a vertex array. This holds vertex buffers and an index buffer 
     * 
     * @return Vertex array resource 
     */
    VertexArray createVertexArray(); 
    
    /**
     * Creates a 2D texture
     * 
     * @param format Pixel format of the texture 
     * @param width Width of the texture 
     * @param height Height of the texture 
     * @return 2D texture resource 
     */
    Texture2D createTexture2D(Texture.Format format, int width, int height); 
    
    /**
     * Creates a cubemap texture
     * 
     * @param format Pixel format of the texture 
     * @param size Width and height of the texture 
     * @return Cubemap texture resource 
     */
    TextureCube createTextureCube(Texture.Format format, int size);
    
    /**
     * Creates a 3D texture 
     * 
     * @param format Pixel format of the texture 
     * @param width Width of the texture 
     * @param height Height of the texture 
     * @param depth Depth of the texture 
     * @return 3D texture resource 
     */
    Texture3D createTexture3D(Texture.Format format, int width, int height, int depth);
    
    /**
     * Creates an array of 2D textures 
     * 
     * @param format Pixel format of the texture 
     * @param width Width of the texture 
     * @param height Height of the texture 
     * @param layers Number of 2D textures in the array 
     * @return 2D texture array resource 
     */
    Texture2DArray createTexture2DArray(Texture.Format format, int width, int height, int layers); 
    
    // getters
    
    /**
     * Get the width of the screen, same as Display.getWidth()
     * 
     * @return Width of the screen  
     */
    int getScreenWidth(); 
    
    /**
     * Get the height of screen, same as Display.getHeight() 
     * 
     * @return Height of the screen 
     */
    int getScreenHeight(); 
    
    /**
     * Get the width of the current render target
     * 
     * @return Current render target width 
     */
    int getWidth(); 
    
    /**
     * Get the height of the current render target 
     * 
     * @return Current render target height 
     */
    int getHeight(); 
    
    /**
     * Get the current render target 
     * 
     * @return Current render target 
     */
    RenderTarget getRenderTarget(); 
    
    /**
     * Get the current vertex array 
     * 
     * @return Current vertex array 
     */
    VertexArray getVertexArray(); 
    
    /**
     * Get the current shader 
     * 
     * @return Current shader 
     */
    Shader getShader(); 
    
    /**
     * Get the current texture at a texture unit 
     * 
     * @param index Texture unit 
     * @return Current texture at that unit 
     */
    Texture getTexture(int index); 
    
    /**
     * Get the number of usable texture units 
     * 
     * @return Number of texture units 
     */
    int getMaxTextureCount(); 
    
    /**
     * Checks if depth testing is enabled. You usually want 
     * this for 3D graphics
     * 
     * @return If depth testing is enabled 
     */
    boolean isDepthTestEnabled(); 
    
    /**
     * Get the current blend mode for the source graphic
     * 
     * @return Source blend mode 
     */
    BlendMode getSourceBlendMode();
    
    /**
     * Get the current blend mode for the destination graphic 
     * 
     * @return Destination blend mode 
     */
    BlendMode getDestBlendMode(); 
    
    /**
     * Get which face to cull 
     * 
     * @return Which face will be culled 
     */
    CullMode getCullMode(); 
    
    // setters 
    
    /**
     * Set the current render target. The render target must 
     * have been created from this graphics device. If this 
     * is set to null, the screen is used instead. A render 
     * target must have at least one attachment to be used  
     * 
     * @param fbo New render target
     */
    void setRenderTarget(RenderTarget fbo); 
    
    /**
     * Set the current shader. The shader must have been 
     * created from this graphics device 
     * 
     * @param shader New shader 
     */
    void setShader(Shader shader);
    
    /**
     * Set the current vertex array. The vertex array must 
     * have been created from this graphics device 
     * 
     * @param vao New vertex array 
     */
    void setVertexArray(VertexArray vao); 
    
    /**
     * Set the current texture of a texture unit. The texture 
     * must have been created from this graphics device 
     * 
     * @param index Texture unit 
     * @param texture New texture 
     */
    void setTexture(int index, Texture texture); 
    
    /**
     * Set depth testing 
     * 
     * @param enabled If depth testing should be enabled 
     */
    void setDepthTestEnabled(boolean enabled); 
    
    /**
     * Set blend mode 
     * 
     * @param src Source blend mode 
     * @param dst Destination blend mode 
     */
    void setBlendMode(BlendMode src, BlendMode dst);
    
    /**
     * Set which face to cull 
     * 
     * @param cull Which face to cull 
     */
    void setCullMode(CullMode cull); 
    
    // rendering
    
    /**
     * Clear the current render target. This clears the depth 
     * and stencil buffers too 
     * 
     * @param color Color to clear with 
     */
    void clearScreen(ColorRgba color); 
    
    /**
     * Draw geometry directly from vertex buffers in the current 
     * vertex array. Note that the number of vertices is NOT the 
     * number of primitives. To draw 3 triangles, [count] would be
     * equal to 9 (3 triangles * 3 vertices per triangle) 
     * 
     * @param prim Primitive type to draw 
     * @param start Starting vertex 
     * @param count Number of vertices 
     */
    void drawArrays(Primitive prim, int start, int count); 
    
    /**
     * Draw geometry from the index buffer set in the current 
     * vertex array. Note that the number indices is NOT the 
     * number of primitives. To draw 3 triangles, [count] would be
     * equal to 9 (3 triangles * 3 indices per triangle) 
     * 
     * @param prim Primitive type to draw 
     * @param start Starting index 
     * @param count Number of indices 
     */
    void drawElements(Primitive prim, int start, int count); 

}
