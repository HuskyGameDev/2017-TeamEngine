package oasis.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import oasis.core.Oasis;

public class Texture2D extends Texture {

    private ByteBuffer buffer; 
    
    public Texture2D(Format format, int width, int height) {
        super(format, width, height); 
        
        buffer = Oasis.getDirectBufferAllocator().allocate(getSizeInBytes()); 
        Oasis.getGraphicsDevice().assignNativeResource(this); 
    }
    
    public GraphicsResource.Type getResourceType() {
        return GraphicsResource.Type.TEXTURE_2D; 
    }
    
    public Buffer getAndFlipBuffer() {
        buffer.position(getWidth() * getHeight() * 4); 
        buffer.flip(); 
        return buffer; 
    }
    
    public void release() {
        super.release(); 
        needsUpdate = true; 
        needsParamUpdate = true; 
    }
    
    public int getSizeInBytes() {
        return 4 * getWidth() * getHeight(); 
    }
    
    public void getPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        int k = offset; 
        
        for (int i = 0; i < height; i++) {
            buffer.position((x + (y + i) * getWidth()) * 4); 
            for (int j = 0; j < width; j++) {
                rgba[k++] = buffer.getInt(); 
            }
        }
    }
    
    public void setPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        int k = offset; 
        
        for (int i = 0; i < height; i++) {
            buffer.position((x + (y + i) * getWidth()) * 4);  
            for (int j = 0; j < width; j++) {
                buffer.putInt(rgba[k++]);
            }
        }
        
        needsUpdate = true; 
    }
    
}
