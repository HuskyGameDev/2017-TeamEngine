package oasis.graphics;

import java.nio.Buffer;

public interface HardwareBufferResource {

    public enum Type {
        
        VERTEX, 
        
        INDEX, 
        
        TEXTURE; 
        
    }
    
    Type getType(); 
    
    void upload(int bytes, Buffer data, BufferUsage usage); 
    
    void release(); 
    
}
