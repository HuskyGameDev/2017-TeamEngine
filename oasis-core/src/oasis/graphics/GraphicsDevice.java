package oasis.graphics;

import oasis.graphics.vertex.Mesh;

public interface GraphicsDevice {

    // rendering
    
    // TODO blending
    
    void clearScreen(ColorRgba color); 
    
    void setShader(Shader shader); 
    
    void drawMesh(Mesh mesh); 

    Shader createShaderFromSource(String vertex, String fragment); 
    
    Mesh createMesh(); 
    
}
