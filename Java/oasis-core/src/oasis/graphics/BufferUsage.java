package oasis.graphics;

/**
 * Hint for the GPU on the type of buffer information 
 * 
 * @author Nicholas Hamilton
 *
 */
public enum BufferUsage {
    
    /**
     * Buffer will not be changed 
     */
    STATIC, 
    
    /**
     * Buffer will be infrequently be changed 
     */
    DYNAMIC, 
    
    /**
     * Buffer will be changed every frame 
     */
    STREAM; 
    
}
