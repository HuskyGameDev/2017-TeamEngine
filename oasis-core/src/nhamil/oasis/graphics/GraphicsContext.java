package nhamil.oasis.graphics;

public interface GraphicsContext {

    int getWidth();
    int getHeight();
    
    void setClearColor(ColorRgba color);
    void clearScreen();
    
    Framebuffer createFramebuffer(int width, int height, boolean colorBuffer, boolean depthBuffer);
    void setFramebuffer(Framebuffer fb);

    ShaderProgram createShaderProgram(String vSource, String fSource);
    void setShaderProgram(ShaderProgram shader);
    
    Texture createTexture(int width, int height);
    Texture createTexture(Bitmap bmp);
    void setTexture(int unit, Texture tex);
    
}
