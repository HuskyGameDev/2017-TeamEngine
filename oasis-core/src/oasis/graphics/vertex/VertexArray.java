package oasis.graphics.vertex;

import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsResource;

public class VertexArray extends GraphicsResource {

    private IndexBuffer indexBuffer; 
    private VertexBuffer[] vertexBuffers; 
    
    public VertexArray(GraphicsDevice graphics) { 
        graphics.update(this);
    }
    
    // getters
    
    public IndexBuffer getIndexBuffer() { 
        return indexBuffer; 
    }
    
    public int getVertexBufferCount() { 
        return vertexBuffers.length; 
    }
    
    public VertexBuffer getVertexBuffer(int index) { 
        return vertexBuffers[index]; 
    }
    
    // setters
    
    public void setIndexBuffer(IndexBuffer index) { 
        indexBuffer = index; 
        dirty = true; 
    }
    
    public void setVertexBuffer(VertexBuffer vertex) { 
        vertexBuffers = new VertexBuffer[] { vertex }; 
        dirty = true; 
    }
    
    public void setVertexBuffers(VertexBuffer[] vertices) { 
        if (vertices == null) { 
            vertexBuffers = new VertexBuffer[0]; 
        } 
        else {
            vertexBuffers = vertices.clone(); 
        }
        dirty = true; 
    }
    
}
