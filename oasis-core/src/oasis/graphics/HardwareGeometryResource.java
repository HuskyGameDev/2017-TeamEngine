package oasis.graphics;

public interface HardwareGeometryResource {

    void upload(IndexBuffer submeshes, VertexBuffer[] vertices); 
    
    void release(); 
    
}
