package oasis.graphics.jogl3;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL;

import oasis.graphics.BufferUsage;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;
import oasis.util.BufferUtil;

public class Jogl3VertexBuffer implements VertexBuffer {

    protected int id; 
    
    private Jogl3GraphicsDevice gd; 
    private VertexFormat format; 
    private BufferUsage usage; 
    private int bufferSize = 0; 
    private int curBufferSize = 0; 
    private FloatBuffer data; 
    
    public Jogl3VertexBuffer(Jogl3GraphicsDevice gd, VertexFormat format, BufferUsage usage) {
        this.gd = gd; 
        this.format = format; 
        this.usage = usage; 
        this.data = BufferUtil.createNativeFloatBuffer(0); 
        
        int[] ids = new int[1]; 
        gd.gl.glGenBuffers(1, ids, 0);
        id = ids[0]; 
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
    
    @Override
    public BufferUsage getUsage() {
        return usage; 
    }

    @Override
    public VertexFormat getFormat() {
        return format; 
    }

    private void setData(FloatBuffer data) {
        int newSize = data.limit() * 4; 
        
        gd.context.bindVbo(id);
        
        if (bufferSize <= newSize) {
            // buffer is not big enough, create a new one
            // if buffer is the same size, still create a new buffer 
            // since glBufferSubData is synchronized, I think 
            bufferSize = newSize; 
            gd.gl.glBufferData(GL.GL_ARRAY_BUFFER, newSize, data, Jogl3Convert.getBufferUsageInt(usage));
            gd.getError("glBufferData (array buffer)"); 
        }
        else {
            // old buffer size is big enough, no need to allocate 
            // more space
            gd.gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, newSize, data);
            gd.getError("glBufferSubData (array buffer)"); 
        }
        
        curBufferSize = newSize; 
    }

    @Override
    public int getVertexCount() {
        return curBufferSize / format.getByteCount(); 
    }

    @Override
    public int getFloatCount() {
        return curBufferSize / 4; 
    }

    @Override
    public float[] getData(float[] out) {
        data.get(out); 
        return out; 
    }

    @Override
    public float[] getData() {
        return getData(new float[getFloatCount()]); 
    }

    @Override
    public void setData(float[] verts) {
        if (data.capacity() < verts.length) {
            data = BufferUtil.createNativeFloatBuffer(verts.length); 
        }
        
        data.clear(); 
        data.put(verts); 
        data.flip(); 
        setData(data); 
    }
    
}
