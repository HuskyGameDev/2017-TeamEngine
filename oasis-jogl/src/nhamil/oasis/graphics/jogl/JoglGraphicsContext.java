package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import nhamil.oasis.graphics.Bitmap;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.Framebuffer;
import nhamil.oasis.graphics.GraphicsContext;
import nhamil.oasis.graphics.Shader;
import nhamil.oasis.graphics.Texture;

public class JoglGraphicsContext implements GraphicsContext {

    private JoglDisplay display;
    private JoglGlContext gl;
    private ColorRgba clear = new ColorRgba(0, 0, 0, 0);
    private Framebuffer framebuffer;
    
    public JoglGraphicsContext(JoglDisplay display, JoglGlContext gl) {
        this.display = display;
        this.gl = gl;
    }
    
    public void setGL(GL gl) {
        this.gl.setGL(gl);
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
        clear = color;
    }

    @Override
    public void clearScreen() {
        if (framebuffer != null) {
            if (framebuffer.hasTexture()) ((JoglTexture) framebuffer.getTexture()).clear();
        }
        
        gl.clear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public void setFrameBuffer(Framebuffer fb) {
        this.framebuffer = fb;
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
    public Framebuffer createFramebuffer(int width, int height, boolean colorBuffer, boolean depthBuffer) {
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
