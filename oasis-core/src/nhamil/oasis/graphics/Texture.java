package nhamil.oasis.graphics;

import nhamil.oasis.core.Disposable;

public interface Texture extends Disposable {

    public enum Filter {
        Nearest, 
        Linear;
    }
    
    public enum Wrap {
        Repeat, 
        MirroredRepeat, 
        Clamp, 
    }
    
    int getWidth();
    int getHeight();
    float getAspectRatio();
    
    Filter getMinFilter();
    Filter getMaxFilter();
    void setMinFilter(Filter filter);
    void setMaxFilter(Filter filter);
    void setFilter(Filter both);
    void setFilter(Filter min, Filter max);
    
    Wrap getWrap();
    void setWrap(Wrap wrap);
    
    void setPixelData(Bitmap data);
    
}
