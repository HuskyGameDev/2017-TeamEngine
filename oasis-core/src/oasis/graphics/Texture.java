package oasis.graphics;

public abstract class Texture {

    protected TextureFormat format; 
    protected int width; 
    protected int height; 
    protected MinFilter minFilter = MinFilter.LINEAR_MIPMAP_LINEAR; 
    protected MagFilter magFilter = MagFilter.LINEAR; 
    protected WrapMode wrapU = WrapMode.REPEAT; 
    protected WrapMode wrapV = WrapMode.REPEAT; 
    protected int levels = 4; 
    
    public Texture(TextureFormat format, int width, int height) {
        this.format = format; 
        this.width = width; 
        this.height = height; 
    }
    
    public abstract void dispose(); 
    
    public abstract TextureType getType(); 
    
    public TextureFormat getFormat() {
        return format; 
    }
    
    public int getWidth() {
        return width; 
    }
    
    public int getHeight() {
        return height; 
    }
    
    public MinFilter getMinFilter() {
        return minFilter; 
    }
    
    public MagFilter getMagFilter() {
        return magFilter; 
    }
    
    public WrapMode getUWrapMode() {
        return wrapU; 
    }
    
    public WrapMode getVWrapMode() {
        return wrapV; 
    }
    
    public int getMipmapLevels() {
        return levels; 
    }
    
    public void setMinFilter(MinFilter min) {
        minFilter = min; 
    }
    
    public void setMagFilter(MagFilter mag) {
        magFilter = mag; 
    }
    
    public void setFilters(MinFilter min, MagFilter mag) {
        setMinFilter(min); 
        setMagFilter(mag); 
    }
    
    public void setUWrapMode(WrapMode u) {
        wrapU = u; 
    }
    
    public void setVWrapMode(WrapMode v) {
        wrapV = v; 
    }
    
    public void setWrapModes(WrapMode u, WrapMode v) {
        setUWrapMode(u); 
        setVWrapMode(v); 
    }
    
    public void setMipmapLevels(int levels) {
        this.levels = levels; 
    }
    
}
