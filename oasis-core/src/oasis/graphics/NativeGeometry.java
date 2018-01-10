package oasis.graphics;

public interface NativeGeometry {

    void upload(IndexBuffer submeshes, VertexBuffer[] vertices); 
    
    void release(); 
    
}
