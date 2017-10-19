package oasis.util;

/**
 * Thread-safe unique ID generator
 * 
 * @author Nicholas Hamilton 
 *
 */
public final class UidGenerator {
    
    private static volatile long next = 1L;
    
    private UidGenerator() {}
    
    public static synchronized long next() {
        next++;
        
        if (next < 0L) {
            throw new RuntimeException("ID Overflow");
        }
        
        return next;
    }
    
}
