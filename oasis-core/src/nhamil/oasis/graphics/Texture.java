package nhamil.oasis.graphics;

public interface Texture {

    Texture copy();
    
    TextureRegion getRegion(int x, int y, int w, int h);
    TextureRegion getRegion(float x, float y, float w, float h);
    
}
