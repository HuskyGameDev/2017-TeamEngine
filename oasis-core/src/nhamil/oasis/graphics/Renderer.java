package nhamil.oasis.graphics;

public interface Renderer {

    void clear(ColorRgba color);
    
    void setFrameBuffer(FrameBuffer target);
    void setShader(Shader shader);
    void setColor(ColorRgba color);
    void setLayer(float layer);
    
    void pushTransform();
    void popTransform();
    
    void translate(float tx, float ty);
    void scale(float sx, float sy);
    void rotate(float rad);
    
    void loadNDC();
    void loadUnit(float unitSize);
    void loadScreenCoords();
    void loadOrtho(float x, float y, float w, float h, float near, float far);
    
}
