package oasis.graphics;

import oasis.graphics.vertex.VertexDeclaration;

public class VertexBuffer extends GraphicsObject {

    private VertexDeclaration vertexDeclare;
    
    public VertexBuffer(GraphicsDevice graphics, VertexDeclaration vertDeclare) {
        super(graphics);
        vertexDeclare = vertDeclare; 
    }
    
    public VertexDeclaration getVertexDeclaration() { 
        return vertexDeclare; 
    }
    
    
    
}
