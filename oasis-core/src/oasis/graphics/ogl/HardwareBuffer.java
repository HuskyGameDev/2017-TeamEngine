package oasis.graphics.ogl;

import java.nio.Buffer;

public class HardwareBuffer {

    private int[] id = new int[1]; 
    
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
    
    public void upload(Ogl ogl, int type, long size, Buffer data, int usage) {
        boolean alreadyExist = validate(ogl); 
        
        ogl.glBindBuffer(type, id[0]); 
        
        if (alreadyExist) {
            ogl.glBufferSubData(type, 0, size, data);
        }
        else {
            ogl.glBufferData(type, size, data, usage);
        }
    }
    
    public void release(Ogl ogl) {
        if (id[0] != 0) {
            // delete vbo
            ogl.glDeleteBuffers(1, id, 0); 
        }
    }
    
}
