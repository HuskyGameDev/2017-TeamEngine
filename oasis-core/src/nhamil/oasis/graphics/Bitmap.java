package nhamil.oasis.graphics;

import java.nio.IntBuffer;

public class Bitmap {

    private int width, height;
    private IntBuffer pixels;
    
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = IntBuffer.allocate(width * height);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getPixel(int x, int y) {
        return pixels.get(x + y * width);
    }
    
    public void setPixel(int x, int y, int hex) {
        pixels.put(x + y * width, hex);
    }
    
}
