package nhamil.oasis.graphics;

public interface GraphicsContext {

    int getWidth();
    int getHeight();
    
    void setScreenClearColor(ColorRgba color);
    void clearScreen();
    
    FrameBuffer createFrameBuffer(int width, int height);
    ShaderProgram createShaderProgram(String vSource, String fSource);
    Texture createTexture(int width, int height, Texture.Format format);
    Texture createTexture(int width, int height);
    Mesh createMesh();
    
}
