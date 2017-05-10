package nhamil.oasis.graphics.jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import nhamil.oasis.core.Oasis;
import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.Display;

public class JoglDisplay implements Display {

    private boolean closed;
    private JFrame frame;
    private GLCanvas canvas;
    
    public JoglDisplay(JoglEngine engine) {
        closed = false;
        frame = new JFrame(Oasis.FULL_NAME);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closed = true;
                frame.setVisible(false);
            }
        });
        frame.setLocationRelativeTo(null);
        
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(engine);
        frame.add(canvas);
    }
    
    public void display() {
        canvas.display();
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
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public boolean canFullscreen() {
        return false;
    }

    @Override
    public void setFullscreen(boolean full) {
        
    }
    
    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void show() {
        frame.setVisible(true);
        closed = false;
    }

    @Override
    public void hide() {
        frame.setVisible(false);
        closed = true;
    }

    @Override
    public boolean isVisible() {
        return !closed;
    }

    @Override
    public boolean isResizable() {
        return frame.isResizable();
    }

    @Override
    public void setResizable(boolean resize) {
        frame.setResizable(false);
    }
    
}
