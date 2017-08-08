package oasis.graphics.jogl3;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL;

import oasis.graphics.vertex.BufferUsage;
import oasis.graphics.vertex.IndexBuffer;
import oasis.util.BufferUtil;

public class Jogl3IndexBuffer implements IndexBuffer {

    protected int id; 
    
    private Jogl3GraphicsDevice gd; 
    private BufferUsage usage; 
    private int bufferSize = 0; 
    private int curBufferSize = 0; 
    private IntBuffer data; 
    
    public Jogl3IndexBuffer(Jogl3GraphicsDevice gd, BufferUsage usage) {
        this.gd = gd; 
        this.usage = usage; 
        data = BufferUtil.createNativeIntBuffer(0); 
        
        int[] ids = new int[1]; 
        gd.gl.glGenBuffers(1, ids, 0);
        id = ids[0]; 
    }
    
    @Override
    public BufferUsage getUsage() {
        return usage; 
    }

    private void setData(IntBuffer data) {
        int newSize = data.limit() * 4; // TODO is this the right method? 
        
        gd.context.bindVao(0);
        gd.context.bindIbo(id);
        
        if (bufferSize <= newSize) {
            // buffer is not big enough, create a new one
            // if buffer is the same size, still create a new buffer 
            // since glBufferSubData is synchronized, I think 
            bufferSize = newSize; 
            gd.gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, newSize, data, Jogl3Convert.getBufferUsageInt(usage));
            gd.getError("glBufferData (element buffer)"); 
        }
        else {
            // old buffer size is big enough, no need to allocate 
            // more space
            gd.gl.glBufferSubData(GL.GL_ELEMENT_ARRAY_BUFFER, 0, newSize, data);
            gd.getError("glBufferSubData (element buffer)"); 
        }
        
        curBufferSize = newSize; 
    }

    @Override
    public int getIndexCount() {
        return curBufferSize / 4; 
    }

    @Override
    public int[] getIndices(int[] out) {
        data.get(out); 
        return out; 
    }

    @Override
    public int[] getIndices() {
        return getIndices(new int[getIndexCount()]); 
    }

    @Override
    public void setData(int[] inds) {
        if (inds.length > data.capacity()) {
            data = BufferUtil.createNativeIntBuffer(inds.length); 
        }
        
        data.clear(); 
        data.put(inds); 
        data.flip(); 
        setData(data); 
    }

    @Override
    public void dispose() {
        if (id != 0) {
            gd.gl.glDeleteBuffers(1, new int[] { id }, 1);
            id = 0; 
        }
    }

    @Override
    public boolean isDisposed() {
        return id == 0; 
    }

}
