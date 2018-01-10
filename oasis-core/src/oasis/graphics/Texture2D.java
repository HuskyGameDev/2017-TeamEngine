package oasis.graphics;

import java.nio.ByteBuffer;

import oasis.core.Oasis;

public class Texture2D extends Texture {

    private ByteBuffer buffer; 
    private NativeTexture hwTexture; 
    
    public Texture2D(Format format, int width, int height) {
        super(format, width, height); 
        
        buffer = Oasis.getDirectBufferAllocator().allocate(getSizeInBytes()); 
        hwTexture = Oasis.getGraphicsDevice().createNativeTexture(Type.TEXTURE_2D, format, width, height, 1); 
    }
    
    public NativeTexture getNativeTexture() {
        return hwTexture; 
    }
    
    public void upload() {
        buffer.position(getSizeInBytes()); 
        buffer.flip(); 
        hwTexture.upload(0, 0, 0, getWidth(), getHeight(), 1, buffer); 
    }
    
    public void release() {
        hwTexture.release(); 
    }
    
    public int getSizeInBytes() {
        return 4 * getWidth() * getHeight(); 
    }
    
    public void getPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        int k = offset; 
        
        buffer.position(0); 
        for (int i = 0; i < rgba.length; i++) {
            rgba[i] = buffer.getInt();  
        }
        
//        for (int i = 0; i < height; i++) {
//            buffer.position((x + (y + i) * getWidth()) * 4); 
//            for (int j = 0; j < width; j++) {
//                rgba[k++] = buffer.getInt(); 
//            }
//        }
    }
    
    public void setPixels(int x, int y, int width, int height, int[] rgba, int offset) {
        int k = offset; 
        
        buffer.position(0); 
        for (int i = 0; i < rgba.length; i++) {
            buffer.putInt(rgba[i]); 
        }
        
//        for (int i = 0; i < height; i++) {
//            buffer.position((x + (y + i) * getWidth()) * 4); 
//            for (int j = 0; j < width; j++) {
//                buffer.putInt(rgba[k++]);
//            }
//        }
    }
    
}
