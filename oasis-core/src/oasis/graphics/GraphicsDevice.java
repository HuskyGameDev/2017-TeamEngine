package oasis.graphics;

import oasis.graphics.vertex.BufferUsage;
import oasis.graphics.vertex.IndexBuffer;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexFormat;

public interface GraphicsDevice {

    // resource creation 
    
    Shader createShaderFromSource(String vertex, String fragment); 
    Shader createShaderFromFile(String vertex, String fragment); 
    VertexBuffer createVertexBuffer(VertexFormat fmt, BufferUsage usage); 
    IndexBuffer createIndexBuffer(BufferUsage usage); 
    VertexArray createVertexArray(); 
    
    // getters
    
    VertexArray getVertexArray(); 
    Shader getShader(); 
    
    // setters 
    
    // sets current shader
    void setShader(Shader shader); 
    void setVertexArray(VertexArray vao); 
    
    // rendering
    
    // TODO blending
    
    void clearScreen(ColorRgba color); 
    
    void drawArrays(Primitive prim, int start, int count); 
    void drawElements(Primitive prim, int start, int count); 

}
