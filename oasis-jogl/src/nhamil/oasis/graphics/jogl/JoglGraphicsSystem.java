package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.GraphicsSystem;

public class JoglGraphicsSystem implements GraphicsSystem {

    private JoglContext gl;
    private JoglDisplay display;
    private JoglRenderer renderer;
    
    public JoglGraphicsSystem(JoglEngine engine) {
        display = new JoglDisplay(engine);
        gl = new JoglContext();
        renderer = new JoglRenderer(display, gl);
    }
    
    public void setGL(GL gl) {
        this.gl.setGL(gl);
    }
    
    public JoglContext getJoglContext() {
        return gl;
    }
    
    @Override
    public JoglDisplay getDisplay() {
        return display;
    }

    @Override
    public JoglRenderer getRenderer() {
        return renderer;
    }
    
}
