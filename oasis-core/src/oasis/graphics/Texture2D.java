package oasis.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import oasis.core.Oasis;

public class Texture2D extends Texture {

    private ByteBuffer buffer; 
    private HardwareBufferResource hwBuffer; 
    
    public Texture2D(Format format, int width, int height) {
        super(format, width, height); 
        
        buffer = Oasis.getDirectBufferAllocator().allocate(getSizeInBytes()); 
        hwBuffer = Oasis.getGraphicsDevice().createHardwareBufferResource(HardwareBufferResource.Type.TEXTURE); 
    }
    
    public void upload() {
        hwBuffer.upload(getSizeInBytes(), buffer, BufferUsage.DYNAMIC); 
    }
    
    public void release() {
        hwBuffer.release(); 
    }
    
    public int getSizeInBytes() {
        return getFormat().getBytesPerPixel() * getWidth() * getHeight(); 
    }
    
    protected Buffer getBuffer() {
        return buffer; 
    }
    
    public void getPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        Codec c = getFormat().getCodec(); 
        
        int k = offset; 
        
        for (int i = 0; i < height; i++) {
            buffer.position((x + (y + i) * getWidth()) * getFormat().getBytesPerPixel()); 
            for (int j = 0; j < width; j++) {
                rgba[k++] = c.getPixelInt(buffer); 
            }
        }
    }
    
    public void setPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        Codec c = getFormat().getCodec(); 
        
        int k = offset; 
        
        for (int i = 0; i < height; i++) {
            buffer.position((x + (y + i) * getWidth()) * getFormat().getBytesPerPixel()); 
            for (int j = 0; j < width; j++) {
                c.setPixelInt(buffer, rgba[k++]);
            }
        }
    }
    
}
