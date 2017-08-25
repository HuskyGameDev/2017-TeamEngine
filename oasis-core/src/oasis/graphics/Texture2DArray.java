package oasis.graphics;

public interface Texture2DArray extends Texture {

    int getLayerCount(); 
    
    ColorRgba[] getPixels(int layer); 
    int[] getPixelsRgba(int layer); 
    int[] getPixelsArgb(int layer); 
    
    void setPixels(int layer, ColorRgba[] data); 
    void setPixelsRgba(int layer, int[] rgba); 
    void setPixelsArgb(int layer, int[] argb); 
    
}
