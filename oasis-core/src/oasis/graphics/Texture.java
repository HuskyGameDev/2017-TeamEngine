package oasis.graphics;

import oasis.core.Disposable;

public interface Texture extends Disposable {

    public enum Format {
        RGBA8(false), 
        RGBA16F(false), 
        RGBA32F(false), 
        DEPTH24(true), 
        DEPTH32(true);

        private final boolean depth;

        private Format(boolean depth) {
            this.depth = depth;
        }

        public boolean isDepthFormat() {
            return depth;
        }
    }
    
    public enum Type {
        TEXTURE_2D, 
        TEXTURE_CUBE, 
        TEXTURE_2D_ARRAY, 
        TEXTURE_3D, 
    }
    
    // getters 
    
    boolean isLost(); 
    
    int getWidth(); 
    int getHeight(); 
    
    Type getType(); 
    Format getFormat(); 
    
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
