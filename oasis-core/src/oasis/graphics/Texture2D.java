package oasis.graphics;

public interface Texture2D extends Texture {

    ColorRgba[] getPixels(); 
    int[] getPixelsRgba(); 
    int[] getPixelsArgb(); 
    
    void setPixels(ColorRgba[] data); 
    void setPixelsRgba(int[] rgba); 
    void setPixelsArgb(int[] argb); 
    
}
