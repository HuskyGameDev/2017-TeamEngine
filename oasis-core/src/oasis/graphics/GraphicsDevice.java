package oasis.graphics;

import oasis.graphics.vertex.IndexBuffer;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;

public interface GraphicsDevice {

    // rendering
    
    // TODO blending
    
    void clearScreen(ColorRgba color); 
    
    void setShader(Shader shader); 
    
    void setVertexArray(VertexArray vertArray); 
    
    void drawArrays(Primitive primitive, int start, int count); 
    
    void drawArraysIndexed(Primitive primitive, int start, int count); 
    
    // resource util
    
    void update(IndexBuffer ibo); 
    void update(VertexBuffer vbo); 
    void update(VertexArray vao); 
    void update(Shader Shader); 
    
}
