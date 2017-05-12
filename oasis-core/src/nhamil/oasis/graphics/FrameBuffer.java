package nhamil.oasis.graphics;

import nhamil.oasis.core.Asset;

public class FrameBuffer extends Asset {

    private int width, height;
    private Texture color;
    private Texture depth;
    
    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() { 
        return width; 
    }
    
    public int getHeight() { 
        return height;
    }
    
    public Texture getColorBuffer() { 
        return color;
    }
    
    public Texture getDepthBuffer() {
        return depth;
    }
    
    public void setColorBuffer(Texture tex) {
        color = tex;
        setNeedsUpdate();
    }
    
    public void setDepthBuffer(Texture tex) {
        depth = tex;
        setNeedsUpdate();
    }
    
}
