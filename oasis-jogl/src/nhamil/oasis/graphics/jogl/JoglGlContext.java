package nhamil.oasis.graphics.jogl;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;

import nhamil.oasis.graphics.Texture;

public class JoglGlContext {

    private GL3 gl;
    
    private int boundFbo = 0;
    private int boundTex = 0;
    
    public JoglGlContext() {
        gl = null;
    }
    
    public void setGL(GL gl) {
        this.gl = gl.getGL3();
    }
    
    public GL getGL() {
        return gl;
    }
    
    public int getBoundFbo() {
        return boundFbo;
    }
    
    public int getBoundTexture() {
        return boundTex;
    }
    
    public void clearColor(float r, float g, float b, float a) {
        gl.glClearColor(r, g, b, a);
    }
    
    public void clear(int mask) {
        gl.glClear(mask);
    }
    
    public void viewport(int x, int y, int w, int h) {
        gl.glViewport(x, y, w, h);
    }
    
    public int genFramebuffer() {
        int[] ids = new int[1];
        gl.glGenFramebuffers(1, ids, 0);
        return ids[0];
    }
    
    public void bindFramebuffer(int id) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, id);
        gl.glDrawBuffers(1, new int[] { GL.GL_COLOR_ATTACHMENT0 }, 0);
        boundFbo = id;
    }
    
    public void framebufferTextureColor(int id) {
        gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_TEXTURE_2D, id, 0);
    }
    
    public void framebufferRenderbufferDepth(int rbo) {
        gl.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, GL.GL_RENDERBUFFER, rbo);
    }
    
    public void framebufferRenderbufferColor(int rbo) {
        gl.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_RENDERBUFFER, rbo);
    }
    
    public void deleteFramebuffer(int id) {
        gl.glDeleteFramebuffers(1, new int[] { id }, 0);
    }
    
    public int genTexture() {
        int[] id = new int[1];
        gl.glGenTextures(1, id, 0);
        return id[0];
    }
    
    public void deleteTexture(int id) {
        gl.glDeleteTextures(1, new int[] { id }, 0);
    }
    
    public void activeTexture(int unit) {
        gl.glActiveTexture(GL.GL_TEXTURE0 + unit);
    }
    
    public void enableTexture() {
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
    
    public void bindTexture(int id) {
        gl.glBindTexture(GL.GL_TEXTURE_2D, id);
        boundTex = id;
    }
    
    public void texImage(int width, int height, IntBuffer pixels) {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_INT, pixels);
    }
    
    public void texParameterMinMagFilter(Texture.Filter min, Texture.Filter max) {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filter(min));
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filter(max));
    }
    
    public int genRenderbuffer() {
        int[] id = new int[1];
        gl.glGenRenderbuffers(1, id, 0);
        return id[0];
    }
    
    public void renderbufferStorageDepth(int width, int height) {
        gl.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL.GL_DEPTH_COMPONENT32, width, height);
    }
    
    public void renderbufferStorageColor(int width, int height) {
        gl.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL.GL_RGBA, width, height);
    }
    
    public void bindRenderbuffer(int rbo) {
        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, rbo);
    }
    
    public void deleteRenderbuffer(int id) {
        gl.glDeleteRenderbuffers(0, new int[] { id }, 0);
    }
    
    private int filter(Texture.Filter filter) {
        switch (filter) {
        case Nearest: 
            return GL.GL_NEAREST;
        case Linear: 
            return GL.GL_LINEAR;
        default: 
            // TODO EXCEPTION? ?
            return 0;
        }
    }

    public boolean checkFramebufferStatus() {
        int status = gl.glCheckFramebufferStatus(GL.GL_FRAMEBUFFER);
        return status == GL.GL_FRAMEBUFFER_COMPLETE;
    }
    
}
