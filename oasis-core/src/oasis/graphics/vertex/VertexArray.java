package oasis.graphics.vertex;

import oasis.core.Disposable;

public interface VertexArray extends Disposable {

    // vertex buffer
    int getVertexBufferCount(); 
    VertexBuffer getVertexBuffer(int index); 
    void setVertexBuffers(VertexBuffer[] list); 
    void setVertexBuffer(VertexBuffer vbo); 
    
    // index buffer
    IndexBuffer getIndexBuffer(); 
    void setIndexBuffer(IndexBuffer ibo); 
    
}
