package oasis.graphics.jogl;

import java.nio.Buffer;

import oasis.graphics.vertex.BufferUsage;

public class JoglVertexBuffer extends JoglGraphicsResource {

    private int bufferSize = 0; 
    private int bufferCurSize = 0; 
    private int id; 
    
    public JoglVertexBuffer(JoglGraphicsDevice graphics) {
        super(graphics);
        create(); 
    }

    public int getVbo() { 
        return id; 
    }
    
    public void setData(int target, Buffer buffer, int bytes, BufferUsage usage) {
        bufferCurSize = bytes; 
        
        graphics.gl.glBindBuffer(target, id);
        if (bufferSize <= bytes) { 
            bufferSize = bytes; 
            graphics.gl.glBufferData(target, bytes, buffer, JoglConvert.getBufferUsageInt(usage));
        }
        else { 
            graphics.gl.glBufferSubData(target, 0, bufferCurSize, buffer);
        }
    }

    private void create() { 
        int[] id = new int[1]; 
        graphics.gl.glGenBuffers(1, id, 0);
        this.id = id[0]; 
    }
    
}
