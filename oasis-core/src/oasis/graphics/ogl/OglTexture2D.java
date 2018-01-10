package oasis.graphics.ogl;

import java.nio.Buffer;

import oasis.graphics.NativeTexture;

public class OglTexture2D implements NativeTexture {

    private Ogl ogl; 
    
    private int[] id = new int[1]; 
    private int format; 
    private int width; 
    private int height; 
    
    public OglTexture2D(Ogl ogl, int format, int width, int height) {
        this.ogl = ogl; 
        this.format = format; 
        this.width = width; 
        this.height = height; 
    }
    
    protected int getId() {
        return id[0]; 
    }
    
    private void validate() {
        if (id[0] != 0) return; 
        
        ogl.glGenTextures(1, id, 0);
        ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id[0]);
        
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_BASE_LEVEL, 0);
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAX_LEVEL, 8);
    }
    
    @Override
    public void upload(int x, int y, int z, int width, int height, int depth, Buffer data) {
        validate(); 
        
        // TODO check for subimage
        
        ogl.glTexImage2D(Ogl.GL_TEXTURE_2D, 0, format, width, height, 0, Ogl.GL_RGBA, Ogl.GL_UNSIGNED_INT_8_8_8_8, data); 
        
        ogl.glGenerateMipmap(Ogl.GL_TEXTURE_2D); 
    }

    @Override
    public void release() {
        if (id[0] != 0) {
            ogl.glDeleteTextures(1, id, 0); 
            id[0] = 0; 
        }
    }

}
