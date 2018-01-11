package oasis.graphics.ogl;

import java.nio.Buffer;

import oasis.graphics.NativeResource;
import oasis.graphics.VertexBuffer;

public class OglVertexBuffer implements NativeResource {

    public static final int TYPE = Ogl.GL_ARRAY_BUFFER; 
    
    private int[] id = new int[1]; 
    private Ogl ogl; 
    private VertexBuffer vb; 
    
    public OglVertexBuffer(Ogl ogl, VertexBuffer vb) {
        this.ogl = ogl; 
        this.vb = vb; 
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
        
        int usage = OglConvert.getBufferUsage(vb.getUsage()); 
        
        ogl.glBindBuffer(TYPE, id[0]); 
        
        int bytes = vb.getSizeInBytes(); 
        Buffer data = vb.getAndFlipBuffer(); 
        
        if (alreadyExist) {
            ogl.glBufferSubData(TYPE, 0, bytes, data);
        }
        else {
            ogl.glBufferData(TYPE, bytes, data, usage);
        }
    }
    
}
