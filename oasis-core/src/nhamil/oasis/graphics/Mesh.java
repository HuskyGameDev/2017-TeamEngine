package nhamil.oasis.graphics;

public interface Mesh {

    public enum Primitive {
        Triangle, 
        TriangleStrip, 
        TriangleFan,
        Line;
    }
    
    public enum UsageHint {
        Static, 
        Dynamic, 
        Stream;
    }
    
    void dispose();
    
    void bind();
    void unbind();
    
    VertexDefinition getVertexDefinition();
    
    Primitive getPrimitive();
    void setPrimitive(Primitive type);
    
    boolean isFrontFaceClockwise();
    void setFrontFaceClockwise(boolean cw);
    
    UsageHint getUsageHint();
    void setUsageHint(UsageHint hint);
    
    void setVertices(Vertex[] verts);
    void setIndices(int[] inds);
    void setIndices(Integer[] inds);
    
}
