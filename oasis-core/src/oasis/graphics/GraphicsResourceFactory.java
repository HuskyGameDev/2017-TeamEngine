package oasis.graphics;

import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexLayout;

public interface GraphicsResourceFactory {

    Shader createShaderFromSource(String vertex, String fragment); 
    
    VertexBuffer createVertexBuffer(VertexLayout layout); 
    
    VertexArray createVertexArray(VertexBuffer[] vertexBuffers); 
    
}
