package nhamil.oasis.graphics;

import nhamil.oasis.core.Disposable;

public interface Texture extends Disposable {

    public enum Filter {
        Nearest, 
        Linear;
    }
    
    public int getWidth();
    public int getHeight();
    public float getAspectRatio();
    
    public void setPixelData(Bitmap data);
    
}
