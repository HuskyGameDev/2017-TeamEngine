package nhamil.oasis.util;

public final class IDGenerator {
    
    private static volatile long next = 1L;
    
    private IDGenerator() {}
    
    public static synchronized long next() {
        next++;
        
        if (next < 0L) {
            throw new RuntimeException("ID Overflow");
        }
        
        return next;
    }
    
}
