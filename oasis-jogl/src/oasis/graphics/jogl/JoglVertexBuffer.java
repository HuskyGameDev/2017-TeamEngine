package oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import oasis.graphics.vertex.VertexBuffer;

public class JoglVertexBuffer {

    private JoglGraphicsDevice graphics; 
    private VertexBuffer buffer; 
    private int lastTotalSize = 0; 
    
    private int id; 
    
    public JoglVertexBuffer(JoglGraphicsDevice graphics, VertexBuffer buffer) { 
        this.graphics = graphics; 
        this.buffer = buffer; 
        createVertexBuffer(); 
        update(); 
    }
    
    public void update() { 
        if (!buffer.isDirty()) { 
            return; 
        }
        
        graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
        if (lastTotalSize <= buffer.getTotalBufferSizeInBytes()) { 
            lastTotalSize = buffer.getTotalBufferSizeInBytes(); 
            graphics.gl.glBufferData(GL.GL_ARRAY_BUFFER, buffer.getTotalBufferSizeInBytes(), buffer.getBuffer(), JoglConvert.getBufferUsageInt(buffer.getUsage()));
            System.out.println("Buffer Data! " + buffer.getBuffer());
        }
        else { 
            graphics.gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, buffer.getCurrentBufferSizeInBytes(), buffer.getBuffer());
            System.out.println("Sub Buffer Data!" + buffer.getBuffer());
        }
        // TODO need to rewind ? flip ? 
        
        buffer.setDirty(false); 
    }
    
    private void createVertexBuffer() { 
        int[] id = new int[1]; 
        graphics.gl.glGenBuffers(1, id, 0);
        this.id = id[0]; 
    }

    public int getVbo() {
        return id;
    }
    
}
