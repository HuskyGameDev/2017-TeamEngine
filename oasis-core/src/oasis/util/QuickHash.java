package oasis.util;

/*
 * Modified version of FNV hash
 */
public final class QuickHash {

    private static final long PRIME_OFFSET = 0xCBF29CE484222325L; 
    private static final long PRIME_MULTIPLY = 0x100000001B3L; 
    
    private QuickHash() {}
    
    public static int compute(byte... data) {
        long hash = PRIME_OFFSET;
        
        for (byte b : data) {
            hash ^= b;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(String data) {
        return compute(data.getBytes());
    }
    
    public static int compute(short... data) {
        long hash = PRIME_OFFSET;
        
        for (short i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(int... data) {
        long hash = PRIME_OFFSET;
        
        for (int i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(long... data) {
        long hash = PRIME_OFFSET;
        
        for (long i : data) {
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 36) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 42) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 50) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 58) & 0xFF;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(float... data) {
        long hash = PRIME_OFFSET;
        
        for (float f : data) {
            int i = Float.floatToRawIntBits(f);
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
    public static int compute(double... data) {
        long hash = PRIME_OFFSET;
        
        for (double d : data) {
            long i = Double.doubleToRawLongBits(d);
            hash ^= (i >>> 0) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 8) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 16) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 24) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 36) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 42) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 50) & 0xFF;
            hash *= PRIME_MULTIPLY; 
            hash ^= (i >>> 58) & 0xFF;
            hash *= PRIME_MULTIPLY; 
        }
        
        return (int) ((hash & 0xFFFFFFFF) ^ (hash >>> 32));
    }
    
}