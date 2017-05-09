package nhamil.oasis.graphics;

public abstract class GraphicsSystem {

    public abstract Display getDisplay();
    
    public abstract TextureManager getTextureManager();
    
    public abstract Renderer getRenderer();
    
}
