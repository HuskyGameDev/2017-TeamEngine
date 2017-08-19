package oasis.graphics;

public interface Texture2D extends Texture {

    int getWidth(); 
    int getHeight(); 
    
    WrapMode getWrapS(); 
    WrapMode getWrapT(); 
    
    void setWrapS(WrapMode s); 
    void setWrapT(WrapMode t); 
    void setWrapModes(WrapMode s, WrapMode t); 
    
    ColorRgba[] getPixels(); 
    ColorRgba[] getPixels(ColorRgba[] out); 
    int[] getPixelsRgba(); 
    int[] getPixelsRgba(int[] out); 
    int[] getPixelsArgb(); 
    int[] getPixelsArgb(int[] out); 
    
    void setPixels(ColorRgba[] data); 
    void setPixelsRgba(int[] rgba); 
    void setPixelsArgb(int[] argb); 
    
}
