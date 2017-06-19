package oasis.graphics;

import oasis.graphics.vertex.VertexArray;

public interface GraphicsDevice {

    // rendering
    
    // TODO blending
    
    GraphicsResourceFactory getResourceFactory(); 
    
    void clearScreen(ColorRgba color); 
    
    void setShader(Shader shader); 
    
    void setVertexArray(VertexArray vertArray); 
    
    void drawArrays(Primitive primitive, int start, int count); 
    
    void drawArraysIndexed(Primitive primitive, int start, int count); 
    
}
