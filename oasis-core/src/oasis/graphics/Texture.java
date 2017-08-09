package oasis.graphics;

import oasis.core.Disposable;

public interface Texture extends Disposable {

    TextureType getType(); 
    TextureFormat getFormat(); 
    
    MinFilter getMinFilter(); 
    MagFilter getMagFilter(); 
    
    void setMinFilter(MinFilter min); 
    void setMagFilter(MagFilter mag); 
    void setFilter(MinFilter min, MagFilter mag); 
    
}
