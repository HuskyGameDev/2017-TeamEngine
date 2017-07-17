package oasis.graphics.texture;

public interface Texture2D extends Texture { 
    
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
