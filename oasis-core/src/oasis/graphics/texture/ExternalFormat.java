package oasis.graphics.texture;

public enum ExternalFormat {
    RGBA(false), 
    BGRA(false), 
    DEPTH(true); 
    
    private final boolean depth; 
    
    private ExternalFormat(boolean depth) { 
        this.depth = depth; 
    }
    
    public boolean isDepthFormat() { 
        return depth; 
    }
}
