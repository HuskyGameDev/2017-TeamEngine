package nhamil.oasis.graphics.jogl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import nhamil.oasis.core.Oasis;
import nhamil.oasis.graphics.Texture;
import nhamil.oasis.graphics.TextureManager;

public class JoglTextureManager implements TextureManager {

    private List<String> paths;
    
    public JoglTextureManager() {
        paths = new ArrayList<>();
        paths.add(Oasis.DEFAULT_TEXTURE_FOLDER);
    }
    
    @Override
    public Texture getFromFile(String filename) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Texture getFromImage(BufferedImage image) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addSearchPath(String path) {
        if (path != null) paths.add(path);
    }

}
