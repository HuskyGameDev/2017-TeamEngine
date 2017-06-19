package oasis.graphics.jogl;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.awt.GLCanvas;

import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.graphics.Display;

public class JoglDisplay implements Display, GLEventListener {

    private static final GameLogger log = new GameLogger(JoglDisplay.class);
    
    public Object contextWait = new Object();
    
    private GLContext context;
    private Frame frame;
    private GLCanvas canvas;
    private JoglGraphicsDevice device; 
    private boolean shouldClose = false;
    
    public JoglDisplay() {
        context = null;
        
        frame = new Frame(Oasis.FULL_NAME);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shouldClose = true;
            }
        });
        
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setRedBits(8);
        caps.setGreenBits(8);
        caps.setBlueBits(8);
        caps.setAlphaBits(8);
        // 32 bits crashes my laptop from last year
        // I will assume this means it is fairly
        // likely other devices might potentially
        // crash from requesting 32 bits as well. 
        // You should really draw to an FBO (which
        // can have 32 bit depth) anyways so that
        // you can use post-processing. 
        // A logarithmic depth buffer can fix a low
        // precision depth buffer, but that is not
        // how the default shaders will work, so you
        // have to make the shaders manually. 
        caps.setDepthBits(24);
        caps.setStencilBits(8);
        
        canvas = new GLCanvas(caps);
        canvas.setAutoSwapBufferMode(false);
        canvas.addGLEventListener(this);
        frame.add(canvas);
        
        device = new JoglGraphicsDevice(); 
    }
    
    public void invoke(final Runnable run) { 
        canvas.invoke(true, new GLRunnable() {
            @Override
            public boolean run(GLAutoDrawable drawable) {
                device.setOglContext(drawable.getGL());
                run.run();
                return true;
            }
        });
    }
    
    public JoglGraphicsDevice getGraphics() {
        return device;
    }
    
    public GLContext getContext() {
        return context;
    }
    
    public void swapBuffers() {
        canvas.swapBuffers();
    }
    
    public void updateOgl() {
        context.makeCurrent();
        device.setOglContext(context.getGL());
    }
    
    @Override
    public String getTitle() {
        return frame.getTitle();
    }

    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }

    @Override
    public void setSize(int width, int height) {
        canvas.setSize(width, height);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return frame.isVisible();
    }

    @Override
    public boolean isResizable() {
        return frame.isResizable();
    }

    @Override
    public void setResizable(boolean resize) {
        frame.setResizable(resize);
    }

    @Override
    public boolean isFullscreen() {
        // TODO FINISH
        return false;
    }

    @Override
    public boolean canFullscreen() {
        // TODO FINISH
        return false;
    }

    @Override
    public void setFullscreen(boolean full) {
        // TODO FINISH
    }

    @Override
    public boolean shouldClose() {
        return shouldClose;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        context = drawable.getContext();
        
        GL2ES2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        log.info("GL Vendor: " + gl.glGetString(GL.GL_VENDOR));
        log.info("GL Renderer: " + gl.glGetString(GL.GL_RENDERER));
        log.info("GL Version: " + gl.glGetString(GL.GL_VERSION));
        log.info("GLSL Version: " + gl.glGetString(GL2.GL_SHADING_LANGUAGE_VERSION));
        
        synchronized (contextWait) {
            context = drawable.getContext();
            device.setOglContext(context.getGL());
            
            log.debug("Changed context: " + context.getGLVersion());
            contextWait.notify();
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        context = drawable.getContext();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        context = drawable.getContext();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        context = drawable.getContext();
    }
    
    @Override
    public float getAspectRatio() {
        return (float) getWidth() / getHeight();
    }

}
