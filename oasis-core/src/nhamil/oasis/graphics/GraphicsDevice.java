package nhamil.oasis.graphics;

public interface GraphicsDevice {

    int getWidth();
    int getHeight();
    
    void setClearColor(ColorRgba color);
    void clearScreen();
    
    Framebuffer createFrameBuffer(int width, int height, boolean colorBuffer, boolean depthBuffer);
    void setFrameBuffer(Framebuffer fb);

    Shader createShader();
    void setShader(Shader shader);
    
    Texture createTexture(int width, int height);
    Texture createTexture(Bitmap bmp);
    void setTexture(int unit, Texture tex);
    
}
