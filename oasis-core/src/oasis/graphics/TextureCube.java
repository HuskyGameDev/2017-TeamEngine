package oasis.graphics;

public interface TextureCube extends Texture {

    public enum Face {
        POSITIVE_X, 
        NEGATIVE_X, 
        POSITIVE_Y, 
        NEGATIVE_Y, 
        POSITIVE_Z, 
        NEGATIVE_Z; 
    }
    
    ColorRgba[] getPixels(Face face); 
    int[] getPixelsRgba(Face face); 
    int[] getPixelsArgb(Face face); 
    
    void setPixels(Face face, ColorRgba[] data); 
    void setPixelsRgba(Face face, int[] rgba); 
    void setPixelsArgb(Face face, int[] argb); 
    
}
