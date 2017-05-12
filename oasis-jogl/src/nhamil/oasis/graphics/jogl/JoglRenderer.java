package nhamil.oasis.graphics.jogl;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

import nhamil.oasis.core.Asset;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.FrameBuffer;
import nhamil.oasis.graphics.Mesh;
import nhamil.oasis.graphics.Renderer;
import nhamil.oasis.graphics.Shader;
import nhamil.oasis.graphics.Texture;
import nhamil.oasis.graphics.VertexBuffer;

public class JoglRenderer implements Renderer {

    private static final GameLogger log = new GameLogger(JoglRenderer.class);
    
    private JoglEngine engine;
    private GL3 gl;
    
    public JoglRenderer(JoglEngine engine) {
        this.engine = engine;
    }
    
    public void setGL(GL3 gl) {
        this.gl = gl;
    }

    @Override
    public int getWidth() {
        return engine.getGraphics().getDisplay().getWidth();
    }

    @Override
    public int getHeight() {
        return engine.getGraphics().getDisplay().getHeight();
    }

    @Override
    public void setClearColor(ColorRgba color) {
        gl.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void clearScreen() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public void setFrameBuffer(FrameBuffer fb) {
        if (fb == null) {
            gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
            gl.glViewport(0, 0, getWidth(), getHeight());
        }
        else {
            if (fb.hasInvalidId()) {
                initFrameBuffer(fb);
            }
            
            gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, fb.getId());
            gl.glViewport(0, 0, fb.getWidth(), fb.getHeight());
            
            if (fb.needsUpdate()) {
                updateFrameBuffer(fb);
            }
        }
    }
    
    private void initFrameBuffer(FrameBuffer fb) {
        int[] buffer = new int[1];
        gl.glGenFramebuffers(1, buffer, 0);
        fb.setId(buffer[0]);
        log.debug("Created FrameBuffer with ID of " + fb.getId());
    }
    
    private void updateFrameBuffer(FrameBuffer fb) {
        if (fb == null || !fb.needsUpdate()) return;
        
        // TODO FINISH
        Texture color = fb.getColorBuffer();
        Texture depth = fb.getDepthBuffer();
        
        if (color != null) {
            updateTexture(color);
            gl.glFramebufferTexture(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, color.getId(), 0);
        }
        else {
            gl.glFramebufferTexture(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, 0, 0);
        }
        
        if (depth != null) {
            updateTexture(depth);
            gl.glFramebufferTexture(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, depth.getId(), 0);
        }
        else {
            gl.glFramebufferTexture(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, 0, 0);
        }
        
        fb.setUpdated();
    }

    @Override
    public void deleteFrameBuffer(FrameBuffer fb) {
        if (fb.hasInvalidId()) return;
        
        gl.glDeleteFramebuffers(1, new int[] { fb.getId() }, 0);
        
        fb.setId(Asset.ID_INVALID);
    }

    @Override
    public void setShader(Shader shader) {
        
    }

    @Override
    public void deleteShader(Shader shader) {

    }

    @Override
    public void setTexture(int unit, Texture tex) {
        
    }
    
    private void updateTexture(Texture tex) {
        if (!tex.needsUpdate()) return;
        
        if (tex.hasInvalidId()) {
            int[] id = new int[1];
            gl.glGenTextures(1, id, 0);
            tex.setId(id[0]);
            log.debug("Created Texture with ID of " + tex.getId());
            gl.glBindTexture(GL.GL_TEXTURE_2D, tex.getId());
            
            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, tex.getWidth(), tex.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_INT, null);
            
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        }
    }

    @Override
    public void deleteTexture(Texture tex) {

    }

    @Override
    public void deleteVertexBuffer(VertexBuffer buffer) {

    }

    @Override
    public void drawMesh(Mesh mesh) {

    }

    @Override
    public void deleteMesh(Mesh mesh) {

    }

}
