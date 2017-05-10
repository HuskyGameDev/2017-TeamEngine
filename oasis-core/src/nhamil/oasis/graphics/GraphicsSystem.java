package nhamil.oasis.graphics;

public interface GraphicsSystem {

    Display getDisplay();
    
    Renderer getRenderer();
    
    TextureManager getTextureManager();
    
    ShaderManager getShaderManager();
    
}
