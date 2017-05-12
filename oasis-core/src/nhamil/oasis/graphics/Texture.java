package nhamil.oasis.graphics;

import java.awt.image.BufferedImage;

import nhamil.oasis.core.Asset;

public class Texture extends Asset {

    public enum Type {
        Color, 
        Depth;
    }
    
    private int width, height;
    private Type type;
    private Bitmap data;
    
    public Texture() {
        this(Type.Color);
        setNeedsUpdate();
    }
    
    public Texture(int width, int height) {
        this(Type.Color);
        this.width = width;
        this.height = height;
        setNeedsUpdate();
    }
    
    public Texture(Type type) {
        this.type = type;
        setNeedsUpdate();
    }
    
    public Texture(String filename) {
        this.type = Type.Color;
        setNeedsUpdate();
    }
    
    public Texture(Bitmap bmp) {
        this.type = Type.Color;
        setPixelData(bmp);
        setNeedsUpdate();
    }
    
    public Texture(BufferedImage img) {
        setNeedsUpdate();
    }

    public Type getType() {
        return type;
    }
    
    public int getWidth() { 
        return width; 
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setPixelData(Bitmap data) {
        this.data = data;
        setNeedsUpdate();
    }
    
    public Bitmap getPixelData() {
        return data;
    }
    
}
