package oasis.graphics;

public interface Texture3D extends Texture {

    int getDepth(); 
    
    ColorRgba[] getPixels(); 
    int[] getPixelsRgba(); 
    int[] getPixelsArgb(); 
    
    WrapMode getWrapR(); 
    
    void setPixels(ColorRgba[] data); 
    void setPixelsRgba(int[] rgba); 
    void setPixelsArgb(int[] argb); 
    
    void setWrapR(WrapMode wrap); 
    void setWrapModes(WrapMode s, WrapMode t, WrapMode r); 
    
}
