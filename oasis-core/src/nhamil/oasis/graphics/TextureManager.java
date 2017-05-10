package nhamil.oasis.graphics;

import java.awt.image.BufferedImage;

public interface TextureManager {

    void addSearchPath(String path);
    
    Texture getFromFile(String filename);
    
    Texture getFromImage(BufferedImage image);
    
}
