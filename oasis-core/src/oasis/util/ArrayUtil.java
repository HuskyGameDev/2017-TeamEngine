package oasis.util;

import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public final class ArrayUtil {

    private ArrayUtil() {}
    
    public static int[] toIntArray(Integer[] ints) { 
        int[] a = new int[ints.length]; 
        
        for (int i = 0; i < ints.length; i++) { 
            a[i] = ints[i].intValue(); 
        }
        
        return a; 
    }
    
    public static float[] toFloatArray(Vector2[] list) {
        float[] out = new float[list.length * 2]; 
        
        for (int i = 0; i < list.length; i++) {
            out[i*2 + 0] = list[i].getX(); 
            out[i*2 + 1] = list[i].getY(); 
        }
        
        return out; 
    }
    
    public static float[] toFloatArray(Vector3[] list) {
        float[] out = new float[list.length * 3]; 
        
        for (int i = 0; i < list.length; i++) {
            out[i*3 + 0] = list[i].getX(); 
            out[i*3 + 1] = list[i].getY(); 
            out[i*3 + 2] = list[i].getZ(); 
        }
        
        return out; 
    }
    
    public static float[] toFloatArray(Vector4[] list) {
        float[] out = new float[list.length * 4]; 
        
        for (int i = 0; i < list.length; i++) {
            out[i*4 + 0] = list[i].getX(); 
            out[i*4 + 1] = list[i].getY(); 
            out[i*4 + 2] = list[i].getZ(); 
            out[i*4 + 3] = list[i].getW(); 
        }
        
        return out; 
    }
    
}
