package oasis.graphics;

public interface Texture2D extends Texture {

    boolean isLost(); 
    
    int getWidth(); 
    int getHeight(); 
    
    WrapMode getWrapS(); 
    WrapMode getWrapT(); 
    
    ColorRgba[] getPixels(); 
    int[] getPixelsRgba(); 
    int[] getPixelsArgb(); 
    
    void setWrapS(WrapMode s); 
    void setWrapT(WrapMode t); 
    void setWrapModes(WrapMode s, WrapMode t); 
    
    void setPixels(ColorRgba[] data); 
    void setPixelsRgba(int[] rgba); 
    void setPixelsArgb(int[] argb); 
    
}
