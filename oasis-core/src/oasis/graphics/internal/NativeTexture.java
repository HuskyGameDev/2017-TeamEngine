package oasis.graphics.internal;

import oasis.graphics.texture.Format;
import oasis.graphics.texture.MagFilter;
import oasis.graphics.texture.MinFilter;

public interface NativeTexture {
    
    Format getFormat(); 
    
    MinFilter getMinFilter(); 
    MagFilter getMagFilter(); 
    
    void setMinFilter(MinFilter filter); 
    void setMagFilter(MagFilter filter); 
    void setFilters(MinFilter min, MagFilter mag); 
    
}
