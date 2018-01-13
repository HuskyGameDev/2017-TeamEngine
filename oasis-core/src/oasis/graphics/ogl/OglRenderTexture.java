package oasis.graphics.ogl;

import oasis.core.OasisException;
import oasis.graphics.RenderTexture;
import oasis.graphics.internal.InternalRenderTexture;

public class OglRenderTexture implements InternalRenderTexture {

    private Ogl ogl; 
    
    private int[] id = new int[1];
    private int[] fbo = new int[1]; 
    private int format; 
    private int width; 
    private int height; 
    
    private RenderTexture tex; 
    
    public OglRenderTexture(Ogl ogl, RenderTexture t) {
        this.ogl = ogl; 
        this.tex = t; 
        this.format = OglConvert.getTextureFormat(tex.getFormat()); 
        this.width = tex.getWidth(); 
        this.height = tex.getHeight(); 
        
        validate(); 
    }

    public int getFboId() {
        return fbo[0]; 
    }
    
    public int getTextureId() {
        return id[0]; 
    }
    
    private void validate() {
        if (id[0] != 0) return; 
        
        ogl.glGenFramebuffers(1, fbo, 0); 
        ogl.glBindFramebuffer(Ogl.GL_FRAMEBUFFER, fbo[0]); 
        
        ogl.glGenTextures(1, id, 0);
        ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id[0]);
        
        ogl.glTexImage2D(
                Ogl.GL_TEXTURE_2D, 
                0, 
                format, 
                width, 
                height, 
                0, 
                Ogl.GL_RGBA, 
                Ogl.GL_UNSIGNED_BYTE, 
                null);
        
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_BASE_LEVEL, 0);
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAX_LEVEL, tex.getMipmapLevels());
    
        // needed for render-to-texture ? 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MIN_FILTER, Ogl.GL_NEAREST); 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_MAG_FILTER, Ogl.GL_NEAREST); 
        
        ogl.glFramebufferTexture(Ogl.GL_FRAMEBUFFER, Ogl.GL_COLOR_ATTACHMENT0, id[0], 0); 
    
        if (ogl.glCheckFramebufferStatus(Ogl.GL_FRAMEBUFFER) != Ogl.GL_FRAMEBUFFER_COMPLETE) {
            throw new OasisException("Framebuffer incomplete"); 
        }
    }
    
    @Override
    public void updateParams() {
        validate(); 
        
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_S, OglConvert.getWrapMode(tex.getUWrapMode())); 
        ogl.glTexParameteri(Ogl.GL_TEXTURE_2D, Ogl.GL_TEXTURE_WRAP_S, OglConvert.getWrapMode(tex.getVWrapMode())); 
        
        // TODO mipmaps ? 
    }

    @Override
    public void release() {
        if (id[0] != 0) {
            ogl.glDeleteTextures(1, id, 0); 
            ogl.glDeleteFramebuffers(1, fbo, 0); 
            id[0] = 0; 
            fbo[0] = 0; 
        }
    }

    @Override
    public void onNewDepthFormat() {
        // TODO Auto-generated method stub
        
    }
    
}
