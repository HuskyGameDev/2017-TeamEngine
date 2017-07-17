package oasis.graphics.texture;

public interface Texture {
    
    TextureType getTextureType(); 
    
    Format getFormat(); 
    
    MinFilter getMinFilter(); 
    MagFilter getMagFilter(); 
    
    void setMinFilter(MinFilter filter); 
    void setMagFilter(MagFilter filter); 
    void setFilters(MinFilter min, MagFilter mag); 
    
}
