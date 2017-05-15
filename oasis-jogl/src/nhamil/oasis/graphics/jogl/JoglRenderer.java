package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.Bitmap;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.Framebuffer;
import nhamil.oasis.graphics.GraphicsDevice;
import nhamil.oasis.graphics.Shader;
import nhamil.oasis.graphics.Texture;

public class JoglRenderer implements GraphicsDevice {

    private static final GameLogger log = new GameLogger(JoglRenderer.class);
    
    private JoglEngine engine;
    private JoglDisplay display;
    private JoglContext gl;
    
    public JoglRenderer(JoglDisplay display, JoglContext gl) {
        this.display = display;
        this.gl = gl;
    }
    
    @Override
    public int getWidth() {
        return display.getWidth();
    }

    @Override
    public int getHeight() {
        return display.getHeight();
    }

    @Override
    public void setClearColor(ColorRgba color) {
        gl.clearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void clearScreen() {
        gl.clear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public void setFrameBuffer(Framebuffer fb) {
        if (fb != null) {
            gl.bindFramebuffer(((JoglFramebuffer) fb).getId());
            gl.viewport(0, 0, fb.getWidth(), fb.getHeight());
        }
        else {
            gl.bindFramebuffer(0);
            gl.viewport(0, 0, getWidth(), getHeight());
        }
    }
    
    @Override
    public void setTexture(int unit, Texture tex) {
        gl.activeTexture(unit);
        gl.enableTexture();
        if (tex != null) {
            gl.bindTexture(((JoglTexture) tex).getId());
        }
        else {
            gl.bindTexture(0);
        }
    }

    @Override
    public Framebuffer createFrameBuffer(int width, int height, boolean colorBuffer, boolean depthBuffer) {
        JoglFramebuffer fb = new JoglFramebuffer(width, height, colorBuffer, depthBuffer, gl);
        return fb;
    }

    @Override
    public Shader createShader() {
        return null;
    }

    @Override
    public Texture createTexture(int width, int height) {
        return null;
    }

    @Override
    public Texture createTexture(Bitmap bmp) {
        return null;
    }

    @Override
    public void setShader(Shader shader) {
        
    }
}
