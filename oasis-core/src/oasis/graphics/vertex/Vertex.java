package oasis.graphics.vertex;

public interface Vertex {

    boolean hasElement(VertexElement elem);  
    
    void getFloats(VertexElement elem, float[] out); 
    
}
