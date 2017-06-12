package oasis.graphics;

public class Texture2D extends Texture {

    protected int width, height; 
    protected WrapMode wrapS = DEFAULT_WRAP_MODE; 
    protected WrapMode wrapT = DEFAULT_WRAP_MODE;  
    
    public Texture2D(GraphicsDevice graphics, int width, int height, Format format) {
        super(graphics, format);
        this.width = width; 
        this.height = height; 
    }
    
    public Texture2D(GraphicsDevice graphics, int width, int height) { 
        this(graphics, width, height, Format.RGBA8); 
    }
    
    // getters
    
    public int getWidth() { 
        return width; 
    }
    
    public int getHeight() { 
        return height; 
    }
    
    public WrapMode getWrapModeS() { 
        return wrapS; 
    }
    
    public WrapMode getWrapModeT() { 
        return wrapT; 
    }
    
    // setters
    
    public void setWrapModeS(WrapMode wrap) { 
        wrapS = wrap; 
        setDirtyFlag(); 
    }
    
    public void setWrapModeT(WrapMode wrap) { 
        wrapT = wrap; 
        setDirtyFlag(); 
    }

}
