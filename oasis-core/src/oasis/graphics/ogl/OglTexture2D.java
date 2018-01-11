package oasis.graphics.ogl;

import java.nio.Buffer;

import oasis.graphics.NativeResource;
import oasis.graphics.Texture2D;

public class OglTexture2D implements NativeResource {

    private Ogl ogl; 
    
    private int[] id = new int[1]; 
    private int format; 
    private int width; 
    private int height; 
    
    private Texture2D tex; 
    
    public OglTexture2D(Ogl ogl, Texture2D tex) {
        this.ogl = ogl; 
        this.tex = tex; 
        this.format = OglConvert.getTextureFormat(tex.getFormat()); 
        this.width = tex.getWidth(); 
        this.height = tex.getHeight(); 
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
    public void update() {
        validate(); 
        
        // TODO check for subimage
        
        Buffer data = tex.getAndFlipBuffer(); 
        
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
