package oasis.graphics.ogl;

import java.nio.IntBuffer;

import oasis.core.Oasis;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;

public class OglTexture2D extends Texture2D {

    private Ogl ogl; 
    
    private int[] id = new int[1]; 
    
    private int[] pixels; 
    private IntBuffer buffer; 
    
    private boolean needUpload = false; 
    
    public OglTexture2D(Ogl ogl, TextureFormat format, int width, int height) {
        super(format, width, height);
        this.ogl = ogl;
        
        this.pixels = new int[width * height]; 
        buffer = Oasis.getDirectBufferAllocator().allocate(width * height * 4).asIntBuffer(); 
    }

    public void dispose() {
        if (id[0] != 0) {
            ogl.glDeleteTextures(1, id, 0);
            id[0] = 0; 
            needUpload = true; 
        }
    }
    
    private void validate() {
        if (id[0] != 0) return; 
        
        ogl.glGenTextures(1, id, 0);
        ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id[0]);
        
        if (needUpload) {
            upload(); 
            needUpload = false; 
        }
    }
    
    public int getId() {
        validate(); 
        return id[0]; 
    }
    
    @Override
    public void upload() {
        validate(); 
        
        ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id[0]);
        
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_BASE_LEVEL, 0);
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAX_LEVEL, levels);
        
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_S, OglConvert.getWrapMode(wrapU)); 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_T, OglConvert.getWrapMode(wrapV)); 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MIN_FILTER, OglConvert.getMinFilter(minFilter)); 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAG_FILTER, OglConvert.getMagFilter(magFilter)); 
        
        buffer.position(0); 
        buffer.put(pixels); 
        buffer.flip(); 
        
        ogl.glTexImage2D(Ogl.GL_TEXTURE_2D, 0, OglConvert.getTextureFormat(format), width, height, 0, Ogl.GL_RGBA, Ogl.GL_UNSIGNED_INT_8_8_8_8, buffer); 
        ogl.glGenerateMipmap(Ogl.GL_TEXTURE_2D); 
    } 
    
    @Override
    public int[] getPixels() {
        return pixels.clone();
    }

    @Override
    public void setPixels(int[] rgba) {
        if (rgba == null) {
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = 0; 
            }
        }
        else {
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = rgba[i]; 
            }
        }
    }
    
}
