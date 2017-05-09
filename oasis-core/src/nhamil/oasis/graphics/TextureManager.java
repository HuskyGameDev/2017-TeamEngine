package nhamil.oasis.graphics;

import java.awt.image.BufferedImage;

public interface TextureManager {

    Texture fromFile(String filename);
    
    Texture fromImage(BufferedImage image);
    
    Texture create(int width, int height);
    
}
