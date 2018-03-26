package oasis.graphics.ogl;

import oasis.graphics.RenderTexture;
import oasis.graphics.TextureFormat;

public class OglRenderTexture extends RenderTexture {

    private Ogl ogl; 
    private int[] id = new int[1]; 
    
    public OglRenderTexture(Ogl ogl, TextureFormat format, int width, int height) {
        super(format, width, height);
        
        this.ogl = ogl; 
    }
    
    public void dispose() {
        if (id[0] != 0) {
            ogl.glDeleteTextures(1, id, 0);
            id[0] = 0; 
        }
    }
    
    private void validate() {
        if (id[0] == 0) {
            ogl.glGenTextures(1, id, 0);
            ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id[0]);
            
            if (format.isDepthFormat()) {
                ogl.glTexImage2D(
                        Ogl.GL_TEXTURE_2D, 
                        0, 
                        OglConvert.getTextureFormat(format), 
                        width, 
                        height, 
                        0, 
                        Ogl.GL_DEPTH_COMPONENT, 
                        Ogl.GL_UNSIGNED_INT, 
                        null);
                
                ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_COMPARE_MODE, Ogl.GL_COMPARE_R_TO_TEXTURE);
            }
            else {
                ogl.glTexImage2D(
                        Ogl.GL_TEXTURE_2D, 
                        0, 
                        OglConvert.getTextureFormat(format), 
                        width, 
                        height, 
                        0, 
                        Ogl.GL_RGBA, 
                        Ogl.GL_UNSIGNED_BYTE, 
                        null);
            }
            
            // TODO mipmaps 
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_BASE_LEVEL, 0);
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAX_LEVEL, 0);
        
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MIN_FILTER, OglConvert.getMinFilter(minFilter)); 
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAG_FILTER, OglConvert.getMagFilter(magFilter)); 
            
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_S, OglConvert.getWrapMode(wrapU)); 
            ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_T, OglConvert.getWrapMode(wrapV)); 
        }
    }
    
    public int getId() {
        validate(); 
        return id[0]; 
    }

}
