package oasis.graphics.vertex;

public interface VertexBuffer {

    // getters 
    
    BufferUsage getUsage(); 
    
    VertexLayout getVertexLayout(); 
    
    Vertex[] getVertices(); 
    
    // setters 
    
    void setUsage(BufferUsage usage); 
    
    void setVertices(Vertex[] vertices); 
    
}
