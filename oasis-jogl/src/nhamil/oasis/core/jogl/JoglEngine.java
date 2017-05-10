package nhamil.oasis.core.jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.core.Engine;
import nhamil.oasis.core.test.TestAudio;
import nhamil.oasis.core.test.TestInput;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.graphics.jogl.JoglGraphicsSystem;
import nhamil.oasis.input.InputSystem;

public class JoglEngine extends Engine implements GLEventListener {

    private JoglGraphicsSystem graphics;
    private float lerp;
    
    public JoglEngine() {
        graphics = new JoglGraphicsSystem(this);
    }
    
    @Override
    public GraphicsSystem getGraphics() {
        return graphics;
    }

    @Override
    public InputSystem getInput() {
        return new TestInput();
    }

    @Override
    public AudioSystem getAudio() {
        return new TestAudio();
    }
    
    @Override
    protected void render(float lerp) {
        this.lerp = lerp;
        graphics.getDisplay().display();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        graphics.setGL(gl);
        
        preRender(lerp);
        renderListener(lerp);
        postRender(lerp);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
    }
}
