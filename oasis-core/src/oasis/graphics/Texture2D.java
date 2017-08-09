package oasis.graphics;

public interface Texture2D extends Texture {

    int getWidth(); 
    int getHeight(); 
    
    WrapMode getWrapS(); 
    WrapMode getWrapT(); 
    
    void setWrapS(WrapMode s); 
    void setWrapT(WrapMode t); 
    void setWrap(WrapMode s, WrapMode t); 
    
    ColorRgba[] getPixels(); 
    ColorRgba[] getPixels(ColorRgba[] out); 
    int[] getIntPixels(); 
    int[] getIntPixels(int[] out); 
    
    void setPixels(ColorRgba[] data); 
    void setIntPixels(int[] rgba); 
    
}
