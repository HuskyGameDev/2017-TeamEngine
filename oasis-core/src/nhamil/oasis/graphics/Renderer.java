package nhamil.oasis.graphics;

public interface Renderer {

    int getWidth();
    int getHeight();
    
    void setClearColor(ColorRgba color);
    void clearScreen();
    
    void setFrameBuffer(FrameBuffer fb);
    void deleteFrameBuffer(FrameBuffer fb);

    void setShader(Shader shader);
    void deleteShader(Shader shader);
    
    void setTexture(int unit, Texture tex);
    void deleteTexture(Texture tex);
    
    void deleteVertexBuffer(VertexBuffer buffer);
    
    void drawMesh(Mesh mesh);
    void deleteMesh(Mesh mesh);
    
}
