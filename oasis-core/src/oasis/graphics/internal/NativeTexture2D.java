package oasis.graphics.internal;

import oasis.graphics.texture.WrapMode;

public interface NativeTexture2D extends NativeTexture { 
    
    // getters
    
    int getWidth(); 
    int getHeight(); 
    
    WrapMode getWrapModeS(); 
    WrapMode getWrapModeT(); 
    
    // setters 
    
    void setWrapModeS(WrapMode wrap); 
    void setWrapModeT(WrapMode wrap); 
    void setWrapModes(WrapMode s, WrapMode t); 
    
}
