package nhamil.oasis.graphics;

public interface GraphicsContext {

    int getWidth();
    int getHeight();
    
    void setScreenClearColor(ColorRgba color);
    void clearScreen();
    
    FrameBuffer createFrameBuffer(int width, int height);
    ShaderProgram createShaderProgram(String vSource, String fSource);
    Texture createTexture(int width, int height);
    Mesh createMesh(VertexDefinition def);
    Renderer createRenderer();
    
}
