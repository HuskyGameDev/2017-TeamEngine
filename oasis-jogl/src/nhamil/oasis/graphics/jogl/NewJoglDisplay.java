package nhamil.oasis.graphics.jogl;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import nhamil.oasis.core.Oasis;
import nhamil.oasis.graphics.Display;

public class NewJoglDisplay implements Display, GLEventListener {

    private GLContext context;
    
    private Frame frame;
    private GLCanvas canvas;
    
    private boolean shouldClose = false;
    
    public NewJoglDisplay() {
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
        
        GLProfile profile = GLProfile.get(GLProfile.GL2ES2);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setRedBits(8);
        caps.setGreenBits(8);
        caps.setBlueBits(8);
        caps.setAlphaBits(8);
        caps.setDepthBits(32);
        caps.setStencilBits(8);
        canvas = new GLCanvas(caps);
        canvas.setAutoSwapBufferMode(false);
        
        frame.add(canvas);
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
        // TODO Auto-generated method stub
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
