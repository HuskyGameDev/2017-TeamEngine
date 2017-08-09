package oasis.graphics.jogl3;

import oasis.graphics.ColorRgba;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.WrapMode;

public class Jogl3Texture2D extends Jogl3Texture implements Texture2D {

    protected int id; 
    
    private int width; 
    private int height; 
    private WrapMode wrapS; 
    private WrapMode wrapT; 
    
    public Jogl3Texture2D(Jogl3GraphicsDevice gd, TextureFormat format, int width, int height) {
        super(gd, format); 
        
        this.gd = gd; 
        this.width = width; 
        this.height = height; 
        this.wrapS = WrapMode.CLAMP_EDGE; 
        this.wrapT = WrapMode.CLAMP_EDGE; 
    }
    
    @Override
    public int getWidth() {
        return width; 
    }

    @Override
    public int getHeight() {
        return height; 
    }

    @Override
    public WrapMode getWrapS() {
        return wrapS; 
    }

    @Override
    public WrapMode getWrapT() {
        return wrapT; 
    }

    @Override
    public void setWrapS(WrapMode s) {
        // TODO Auto-generated method stub
        wrapS = s; 
    }

    @Override
    public void setWrapT(WrapMode t) {
        // TODO Auto-generated method stub
        wrapT = t; 
    }

    @Override
    public void setWrap(WrapMode s, WrapMode t) {
        // TODO Auto-generated method stub
        wrapS = s; 
        wrapT = t; 
    }

    @Override
    public ColorRgba[] getPixels() {
        return getPixels(new ColorRgba[width * height]); 
    }

    @Override
    public ColorRgba[] getPixels(ColorRgba[] out) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPixels(ColorRgba[] data) {
        // TODO Auto-generated method stub

    }

}
