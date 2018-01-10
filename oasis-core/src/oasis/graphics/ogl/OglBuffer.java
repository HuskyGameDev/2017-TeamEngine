package oasis.graphics.ogl;

import java.nio.Buffer;

import oasis.graphics.BufferUsage;
import oasis.graphics.NativeBuffer;

public class OglBuffer implements NativeBuffer {

    private int[] id = new int[1]; 
    private Ogl ogl; 
    private int type; 
    private int usage; 
    
    public OglBuffer(Ogl ogl, int type, int usage) {
        this.ogl = ogl; 
        this.type = type; 
        this.usage = usage; 
    }
    
    public int getId() {
        return id[0]; 
    }
    
    /**
     * Creates buffer object if it did not exist
     * 
     * @return True if already existed
     */
    private boolean validate(Ogl ogl) {
        if (id[0] == 0) {
            // vbo does not exist, create it 
            ogl.glGenBuffers(1, id, 0); 
            return false; 
        }
        else {
            return true; 
        }
    }
    
    @Override
    public void release() {
        if (id[0] != 0) {
            // delete vbo
            ogl.glDeleteBuffers(1, id, 0); 
        }
    }

    @Override
    public Type getType() {
        return type == Ogl.GL_ARRAY_BUFFER ? Type.VERTEX : Type.INDEX; 
    }

    @Override
    public void upload(int bytes, Buffer data, BufferUsage u) {
        boolean alreadyExist = validate(ogl); 
        
        if (u != null) {
            usage = OglConvert.getBufferUsage(u); 
        }
        
        ogl.glBindBuffer(type, id[0]); 
        
        if (alreadyExist) {
            ogl.glBufferSubData(type, 0, bytes, data);
        }
        else {
            ogl.glBufferData(type, bytes, data, usage);
        }
    }
    
}
