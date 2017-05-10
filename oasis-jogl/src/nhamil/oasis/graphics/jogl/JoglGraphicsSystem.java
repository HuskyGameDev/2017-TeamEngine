package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL3;

import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.graphics.Renderer;
import nhamil.oasis.graphics.ShaderManager;
import nhamil.oasis.graphics.TextureManager;

public class JoglGraphicsSystem implements GraphicsSystem {

    private JoglDisplay display;
    private JoglTextureManager textures;
    private JoglShaderManager shaders;
    private GL3 gl;
    
    public JoglGraphicsSystem(JoglEngine engine) {
        display = new JoglDisplay(engine);
        textures = new JoglTextureManager();
        shaders = new JoglShaderManager();
    }
    
    public void setGL(GL3 gl) {
        this.gl = gl;
    }
    
    @Override
    public JoglDisplay getDisplay() {
        return display;
    }

    @Override
    public TextureManager getTextureManager() {
        return textures;
    }

    @Override
    public Renderer getRenderer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ShaderManager getShaderManager() {
        return shaders;
    }
}
