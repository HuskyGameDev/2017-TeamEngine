package oasis.graphics;

import java.nio.Buffer;

public interface NativeBuffer {

    public enum Type {
        
        VERTEX, 
        
        INDEX; 
        
    }
    
    Type getType(); 
    
    void upload(int bytes, Buffer data, BufferUsage usage); 
    
    void release(); 
    
}
