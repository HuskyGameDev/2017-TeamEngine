package oasis.util;

public final class ArrayUtil {

    private ArrayUtil() {}
    
    public static int[] convertIntArray(Integer[] ints) { 
        int[] a = new int[ints.length]; 
        
        for (int i = 0; i < ints.length; i++) { 
            a[i] = ints[i].intValue(); 
        }
        
        return a; 
    }
    
}
