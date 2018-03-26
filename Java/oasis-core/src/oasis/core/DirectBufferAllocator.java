package oasis.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 
 * Allocates and frees direct buffers
 * 
 * @author Nicholas Hamilton 
 *
 */
public interface DirectBufferAllocator {

    /**
     * Allocate a direct buffer. The buffer should have 
     * a native byte order. 
     * 
     * @param bytes Capacity of the buffer 
     * @return ByteBuffer with a capacity of [bytes] bytes
     */
    ByteBuffer allocate(int bytes); 
    
    /**
     * Frees a direct buffer. The buffer should have been 
     * allocated from allocate(). You should not use the 
     * buffer after it has been freed. Depending on the 
     * implementation, it may be a good idea to remove 
     * all references to the buffer so the garbage 
     * collector can clean it (also since you should not 
     * be using it after it is freed anyways) 
     * 
     * @param buffer The buffer to free. 
     */
    void free(Buffer buffer); 
    
}
