package oasis.old_graphics;

public interface Texture {

    public enum Format {
        Rgba,
        RgbaFloat,
        Depth;
    }

    public enum Filter {
        Nearest, 
        Linear;
    }
    
    public enum Wrap {
        Repeat, 
        MirroredRepeat, 
        Clamp, 
    }
    
    void dispose();
    
    void clear();
    
    void bind(int unit);
    void bind();
    void unbind();
    
    int getWidth();
    int getHeight();
    float getAspectRatio();
    
    Format getFormat();
    
    Filter getMinFilter();
    Filter getMaxFilter();
    
    void setFilter(Filter min, Filter max);
    void setMinFilter(Filter min);
    void setMaxFilter(Filter max);
    
    Wrap getUWrap();
    Wrap getVWrap();
    
    void setWrap(Wrap u, Wrap v);
    void setUWrap(Wrap wrap);
    void setVWrap(Wrap wrap);
    
    void setPixelData(Bitmap data);
    Bitmap getPixelData();
    
}