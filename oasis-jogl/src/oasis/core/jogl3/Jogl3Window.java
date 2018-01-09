package oasis.core.jogl3;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.awt.GLCanvas;

import oasis.core.Display;
import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.graphics.jogl3.Jogl3DebugOgl;
import oasis.graphics.ogl.OglGraphics;
import oasis.input.jogl3.Jogl3Keyboard;
import oasis.input.jogl3.Jogl3Mouse;

public class Jogl3Window implements Display, GLEventListener {

    private static final Logger log = new Logger(Jogl3Window.class); 
    
    private Jogl3Mouse mouse; 
    private Jogl3Keyboard keyboard; 
    private Jogl3DebugOgl ogl; 
    private OglGraphics graphics; 
    
    private GLContext context;
    private Frame frame;
    private GLCanvas canvas;
    private boolean shouldClose = false; 
    
    public Jogl3Window() {
        frame = new Frame(Oasis.FULL_NAME);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shouldClose = true;
            }
        });
        
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        GLProfile profile = GLProfile.get(GLProfile.GL3);
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
        
        ogl = new Jogl3DebugOgl(); 
        graphics = new OglGraphics(ogl); 
        
        keyboard = new Jogl3Keyboard(); 
        mouse = new Jogl3Mouse(this); 
        
        canvas.addKeyListener(keyboard); 
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse); 
        canvas.addMouseWheelListener(mouse); 
    }
    
    public GLCanvas getCanvas() {
        return canvas; 
    }
    
    public OglGraphics getGraphics() {
        return graphics; 
    }
    
    public Jogl3Mouse getMouse() {
        return mouse; 
    }
    
    public Jogl3Keyboard getKeyboard() {
        return keyboard; 
    }
    
    public void invoke(Runnable r) {
        canvas.invoke(true, new GLRunnable() {
            @Override
            public boolean run(GLAutoDrawable d) {
                ogl.setOgl(d.getGL().getGL3()); 
                r.run(); 
                return true; 
            }
        }); 
    }
    
    public int getX() {
        return frame.getX() + canvas.getX(); 
    }
    
    public int getY() {
        return frame.getY() + canvas.getY(); 
    }
    
    public GLContext getContext() {
        return context;
    }
    
    public void swapBuffers() {
        canvas.swapBuffers();
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
        canvas.requestFocus(); 
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
        
        GL3 gl = drawable.getGL().getGL3();
        gl.setSwapInterval(0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        log.info("GL Vendor: " + gl.glGetString(GL.GL_VENDOR));
        log.info("GL Renderer: " + gl.glGetString(GL.GL_RENDERER));
        log.info("GL Version: " + gl.glGetString(GL.GL_VERSION));
        log.info("GLSL Version: " + gl.glGetString(GL2.GL_SHADING_LANGUAGE_VERSION));
        
        log.debug("Changed context: " + context.getGLVersion());
        ogl.setOgl(gl); 
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
