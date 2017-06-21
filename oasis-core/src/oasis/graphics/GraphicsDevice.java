package oasis.graphics;

import oasis.graphics.vertex.Mesh;

public interface GraphicsDevice {

    // rendering
    
    // TODO blending
    
    GraphicsResourceManager getResourceManager(); 
    
    void clearScreen(ColorRgba color); 
    
    void setShader(Shader shader); 
    
    void drawMesh(Mesh mesh); 
    
}
