package oasis.graphics;

import oasis.core.Disposable;

public interface Texture extends Disposable {

    // getters 
    
    boolean isLost(); 
    
    int getWidth(); 
    int getHeight(); 
    
    TextureType getType(); 
    TextureFormat getFormat(); 
    
    MinFilter getMinFilter(); 
    MagFilter getMagFilter(); 
    
    WrapMode getWrapS(); 
    WrapMode getWrapT(); 
    
    int getMipmaps(); 
    
    // setters 
    
    void setMinFilter(MinFilter min); 
    void setMagFilter(MagFilter mag); 
    void setFilters(MinFilter min, MagFilter mag); 
    
    void setMipmaps(int levels); 
    
    void setWrapS(WrapMode s); 
    void setWrapT(WrapMode t); 
    void setWrapModes(WrapMode s, WrapMode t); 
    
}
