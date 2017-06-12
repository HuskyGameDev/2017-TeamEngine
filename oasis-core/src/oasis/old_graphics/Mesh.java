package oasis.old_graphics;

public interface Mesh {

    public enum Primitive {
        Triangles, 
        TriangleStrip, 
        TriangleFan,
        Lines;
    }
    
    public enum UsageHint {
        Static, 
        Dynamic, 
        Stream;
    }
    
    void dispose();
    
    void draw();
    
    void bind();
    void unbind();
    
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
