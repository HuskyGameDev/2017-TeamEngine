package oasis.graphics.ogl;

import java.nio.Buffer;

import oasis.graphics.IndexBuffer;
import oasis.graphics.NativeResource;

public class OglIndexBuffer implements NativeResource {

    public static final int TYPE = Ogl.GL_ELEMENT_ARRAY_BUFFER; 
    
    private int[] id = new int[1]; 
    private Ogl ogl; 
    private IndexBuffer ib; 
    
    public OglIndexBuffer(Ogl ogl, IndexBuffer ib) {
        this.ogl = ogl; 
        this.ib = ib; 
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
    public void update() {
        boolean alreadyExist = validate(ogl); 
        
        int usage = OglConvert.getBufferUsage(ib.getUsage()); 
        
        ogl.glBindBuffer(TYPE, id[0]); 
        
        int bytes = ib.getSizeInBytes(); 
        Buffer data = ib.getAndFlipBuffer(); 
        
        if (alreadyExist) {
            ogl.glBufferSubData(TYPE, 0, bytes, data);
        }
        else {
            ogl.glBufferData(TYPE, bytes, data, usage);
        }
    }
    
}
