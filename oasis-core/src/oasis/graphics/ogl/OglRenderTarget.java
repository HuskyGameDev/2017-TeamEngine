package oasis.graphics.ogl;

import oasis.graphics.RenderTarget;
import oasis.graphics.RenderTexture;

public class OglRenderTarget extends RenderTarget {

    private static int currentFbo = 0; 
    
    private Ogl ogl; 
    private int[] fbo = new int[1]; 
    
    private int lastColorAttachments = 0; 
    
    public OglRenderTarget(Ogl ogl) {
        this.ogl = ogl; 
    }
    
    protected static void bind(Ogl ogl, int id) {
        if (currentFbo != id) {
            ogl.glBindFramebuffer(Ogl.GL_FRAMEBUFFER, id); 
            currentFbo = id; 
        }
    }
    
    public void dispose() {
        if (fbo[0] != 0) {
            ogl.glDeleteFramebuffers(1, fbo, 0); 
            fbo[0] = 0; 
        }
    }
    
    private void validate() {
        if (fbo[0] == 0) {
            ogl.glGenFramebuffers(1, fbo, 0); 
        }
    }
    
    public int getFboId() {
        validate(); 
        return fbo[0]; 
    }
    
    @Override
    public void setDepthBuffer(RenderTexture depth) {
        validate(); 
        super.setDepthBuffer(depth); 
        
        int lastId = currentFbo; 
        bind(ogl, fbo[0]); 
        
        OglRenderTexture rt = OglGraphicsDevice.safeCast(depthBuffer, OglRenderTexture.class); 
        
        if (depthBuffer == null) {
            ogl.glFramebufferTexture(Ogl.GL_FRAMEBUFFER, Ogl.GL_DEPTH_ATTACHMENT, 0, 0);
        }
        else {
            ogl.glFramebufferTexture(Ogl.GL_FRAMEBUFFER, Ogl.GL_DEPTH_ATTACHMENT, rt.getId(), 0);
        }
        
        bind(ogl, lastId); 
    }
    
    @Override
    public void setColorBuffers(RenderTexture... rts) {
        validate(); 
        super.setColorBuffers(rts); 
        
        int numBuffers = colorBuffers.size(); 
        
        int[] attachments = new int[numBuffers]; 
        
        int lastId = currentFbo; 
        bind(ogl, fbo[0]); 
        
        for (int i = 0; i < Math.max(numBuffers, lastColorAttachments); i++) {
            if (i < numBuffers) {
                OglRenderTexture rt = OglGraphicsDevice.safeCast(colorBuffers.get(i), OglRenderTexture.class); 
                
                attachments[i] = Ogl.GL_COLOR_ATTACHMENT0 + i; 
                ogl.glFramebufferTexture(Ogl.GL_FRAMEBUFFER, Ogl.GL_COLOR_ATTACHMENT0 + i, rt.getId(), 0);
            }
            else {
                ogl.glFramebufferTexture(Ogl.GL_FRAMEBUFFER, Ogl.GL_COLOR_ATTACHMENT0 + i, 0, 0);
            }
        }
        
        ogl.glDrawBuffers(numBuffers, attachments, 0); 
        
        bind(ogl, lastId); 
        
        lastColorAttachments = numBuffers; 
    }
    
}
