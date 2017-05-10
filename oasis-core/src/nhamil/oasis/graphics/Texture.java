package nhamil.oasis.graphics;

import java.awt.image.BufferedImage;

public interface Texture {
    
    TextureRegion getRegionStandardCoords(int x, int y, int w, int h);
    TextureRegion getRegion(float x, float y, float w, float h);
    
    BufferedImage toBufferedImage();
    void setContents(BufferedImage img);
    
}
