package oasis.graphics.vertex;

import java.nio.ByteBuffer;

import oasis.core.EngineException;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsResource;
import oasis.util.BufferUtil;

public class VertexBuffer extends GraphicsResource {

    private GraphicsDevice graphics; 
    private VertexLayout layout; 
    private Vertex[] vertices = null; 
    private BufferUsage usage = BufferUsage.DYNAMIC; 
    private ByteBuffer buffer = null; 
    private int bufferSize = 0; 
    private int bufferCurSize = 0; 
    
    public VertexBuffer(GraphicsDevice graphics, VertexLayout layout) { 
        this.graphics = graphics; 
        this.layout = layout; 
        graphics.update(this);
    }
    
    // getters 
    
    public VertexLayout getVertexLayout() { 
        return layout; 
    }
    
    public ByteBuffer getBuffer() { 
        return buffer; 
    }
    
    public int getTotalBufferSizeInBytes() { 
        return bufferSize; 
    }
    
    public int getCurrentBufferSizeInBytes() { 
        return bufferCurSize; 
    }
    
    public Vertex[] getVertices() { 
        return vertices; 
    }
    
    public BufferUsage getUsage() { 
        return usage; 
    }
    
    // setters 
    
    public void setVertices(Vertex[] vertices) { 
        this.vertices = vertices; 
        
        int sizeInBytes = vertices.length * layout.getTotalSizeInBytes(); 
        if (buffer == null || bufferSize < sizeInBytes) {
            buffer = BufferUtil.createNativeByteBuffer(sizeInBytes);  
            bufferSize = sizeInBytes; 
        }
        
        bufferCurSize = sizeInBytes; 
        
        // write vertex data to buffer 
        buffer.rewind(); 
        for (int i = 0; i < vertices.length; i++) { 
            Vertex v = vertices[i]; 
            // make sure there's no funny business
            if (!layout.isSupportedBy(v)) { 
                throw new EngineException("All vertices must support the vertex buffer's layout"); 
            }
            
            float[] tmp = new float[4]; 
            for (int j = 0; j < layout.getVertexElementCount(); j++) { 
                VertexElement elem = layout.getVertexElement(j); 
                v.getFloats(elem, tmp);
                for (int k = 0; k < elem.getComponentCount(); k++) { 
                    buffer.putFloat(tmp[k]); 
                }
            }
        }
        buffer.flip(); 
        
        dirty = true; 
        graphics.update(this); 
    }
    
    public void setUsage(BufferUsage usage) { 
        this.usage = usage; 
        dirty = true; 
    }
    
}
