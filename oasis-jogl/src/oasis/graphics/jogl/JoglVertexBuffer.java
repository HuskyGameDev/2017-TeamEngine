package oasis.graphics.jogl;

import java.nio.ByteBuffer;

import com.jogamp.opengl.GL;

import oasis.core.EngineException;
import oasis.graphics.vertex.BufferUsage;
import oasis.graphics.vertex.Vertex;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexElement;
import oasis.graphics.vertex.VertexLayout;
import oasis.util.BufferUtil;

public class JoglVertexBuffer extends JoglGraphicsResource implements VertexBuffer {

    private VertexLayout layout; 
    private Vertex[] vertices = null; 
    private BufferUsage usage = BufferUsage.DYNAMIC; 
    private ByteBuffer buffer = null; 
    private int bufferSize = 0; 
    private int bufferCurSize = 0; 
    private int id; 
    
    public JoglVertexBuffer(JoglGraphicsDevice graphics, VertexLayout layout) {
        super(graphics);
        this.layout = layout; 
        create(); 
    }

    public int getVbo() { 
        return id; 
    }
    
    @Override
    public BufferUsage getUsage() { 
        return usage; 
    }
    
    @Override
    public VertexLayout getVertexLayout() {
        return layout; 
    }

    @Override
    public Vertex[] getVertices() {
        return vertices;
    }
    
    @Override
    public void setUsage(BufferUsage usage) {
        // can't change this in OpenGL until
        // new data is uploaded
        this.usage = usage; 
    }

    @Override
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
        
        graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
        if (bufferSize <= sizeInBytes) { 
            bufferSize = sizeInBytes; 
            graphics.gl.glBufferData(GL.GL_ARRAY_BUFFER, sizeInBytes, buffer, JoglConvert.getBufferUsageInt(usage));
        }
        else { 
            graphics.gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, bufferCurSize, buffer);
        }
    }

    private void create() { 
        int[] id = new int[1]; 
        graphics.gl.glGenBuffers(1, id, 0);
        this.id = id[0]; 
    }
    
}
