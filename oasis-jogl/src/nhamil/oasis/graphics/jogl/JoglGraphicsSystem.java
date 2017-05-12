package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL3;

import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.graphics.Renderer;

public class JoglGraphicsSystem implements GraphicsSystem {

    private JoglDisplay display;
    private JoglRenderer renderer;
    
    public JoglGraphicsSystem(JoglEngine engine) {
        display = new JoglDisplay(engine);
        renderer = new JoglRenderer(engine);
    }
    
    public void setGL(GL3 gl) {
        renderer.setGL(gl);
    }
    
    @Override
    public JoglDisplay getDisplay() {
        return display;
    }

    @Override
    public Renderer getRenderer() {
        return renderer;
    }
    
}
