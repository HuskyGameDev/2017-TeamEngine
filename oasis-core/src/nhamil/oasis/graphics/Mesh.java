package nhamil.oasis.graphics;

public interface Mesh {

    public enum Type {
        Triangle, 
        Line;
    }
    
    void dispose();
    
    VertexDefinition getVertexDefinition();
    
    Type getType();
    void setType(Type type);
    
    void setVertices(Vertex[] verts);
    void setIndices(int[] inds);
    void setIndices(Integer[] inds);
    
}
