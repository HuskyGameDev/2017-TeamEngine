package oasis.graphics.vertex;

public interface VertexArray {
    
    // getters
    
    int getVertexBufferCount(); 
    
    VertexBuffer getVertexBuffer(int index); 
    
    // setters
    
    void setVertexBuffer(VertexBuffer buffer); 
    
    void setVertexBuffers(VertexBuffer... buffers); 
    
}
