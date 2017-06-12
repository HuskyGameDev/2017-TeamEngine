package oasis.graphics;

public abstract class Texture extends GraphicsObject {

    // defaults to RGBA, will probably be the most common format
    public static final Format DEFAULT_FORMAT = Format.RGBA8; 
    // most people would probably use this with low res art, so nearest filtering is a good default
    public static final MinFilter DEFAULT_MIN_FILTER = MinFilter.NEAREST; 
    public static final MagFilter DEFAULT_MAG_FILTER = MagFilter.NEAREST; 
    public static final WrapMode DEFAULT_WRAP_MODE = WrapMode.CLAMP_EDGE; 
    
    public enum Format {
        RGBA8(false), 
        RGBA16F(false), 
        RGBA32F(false), 
        DEPTH32(false); 
        
        private final boolean depth; 
        
        private Format(boolean depth) { 
            this.depth = depth; 
        }
        
        public boolean isDepthFormat() { 
            return depth; 
        }
    }
    
    public enum MinFilter {
        NEAREST, 
        LINEAR, 
        NEAREST_MIPMAP_NEAREST, 
        NEAREST_MIPMAP_LINEAR, 
        LINEAR_MIPMAP_NEAREST, 
        LINEAR_MIPMAP_LINEAR;  
    }
    
    public enum MagFilter {
        NEAREST, 
        LINEAR; 
    }
    
    public enum WrapMode {
        CLAMP_EDGE, 
        REPEAT; 
    }
    
    protected Format format = DEFAULT_FORMAT; 
    protected MinFilter minFilter = DEFAULT_MIN_FILTER; 
    protected MagFilter magFilter = DEFAULT_MAG_FILTER; 
    
    public Texture(GraphicsDevice graphics, Format format) { 
        super(graphics); 
        this.format = format; 
    }
    
    // getters
    
    public Format getFormat() { 
        return format; 
    }
    
    public MinFilter getMinFilter() { 
        return minFilter; 
    }
    
    public MagFilter getMagFilter() { 
        return magFilter; 
    }
    
    // setters
    
    public void setMinFilter(MinFilter filter) { 
        minFilter = filter; 
        setDirtyFlag(); 
    }
    
    public void setMagFilter(MagFilter filter) { 
        magFilter = filter; 
        setDirtyFlag(); 
    }
    
}
