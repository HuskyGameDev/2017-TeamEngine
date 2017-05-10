package nhamil.oasis.util;

/*
 * Modified version of FNV hash
 */
public final class QuickHash {

    private static final long PRIME_1 = 722337194951L;
    private static final long PRIME_2 = 826337194183L;
    
    private QuickHash() {}
    
    public static int compute(byte... data) {
        long hash = PRIME_1;
        
        for (byte b : data) {
            hash ^= b;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(short... data) {
        long hash = PRIME_1;
        
        for (short i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(int... data) {
        long hash = PRIME_1;
        
        for (int i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(long... data) {
        long hash = PRIME_1;
        
        for (long i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 36) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 42) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 50) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 58) & 0xFF;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(float... data) {
        long hash = PRIME_1;
        
        for (float f : data) {
            int i = Float.floatToRawIntBits(f);
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(double... data) {
        long hash = PRIME_1;
        
        for (double d : data) {
            long i = Double.doubleToRawLongBits(d);
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 36) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 42) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 50) & 0xFF;
            hash *= PRIME_2; 
            hash ^= (i >>> 58) & 0xFF;
            hash *= PRIME_2; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
}
