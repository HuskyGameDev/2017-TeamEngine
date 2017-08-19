package oasis.graphics;

import oasis.core.Disposable;

public interface Texture extends Disposable {

    TextureType getType(); 
    TextureFormat getFormat(); 
    
    MinFilter getMinFilter(); 
    MagFilter getMagFilter(); 
    
    int getMipmaps(); 
    
    void setMinFilter(MinFilter min); 
    void setMagFilter(MagFilter mag); 
    void setFilters(MinFilter min, MagFilter mag); 
    
    void setMipmaps(int levels); 
    
}
