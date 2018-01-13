package oasis.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.graphics.internal.InternalTexture2D;

public class Texture2D extends Texture<InternalTexture2D> {

    private ByteBuffer buffer; 
    
    private boolean needsUpdate = true; 
    
    public Texture2D(Format format, int width, int height) {
        super(format, width, height); 
        
        if (format.isDepthFormat()) {
            throw new OasisException("Texture format cannot be a depth format"); 
        }
        
        buffer = Oasis.getDirectBufferAllocator().allocate(getSizeInBytes()); 
        Oasis.getGraphicsDevice().requestInternal(this); 
    }
    
    @Override
    public void upload() {
        if (needsUpdate) {
            internal.uploadPixelBuffer(); 
        }
        super.upload(); 
    }
    
    public Type getType() {
        return Type.TEXTURE_2D; 
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
