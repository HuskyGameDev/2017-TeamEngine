package oasis.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Creates native buffers
 * 
 * @author Nicholas Hamilton 
 *
 */
public final class BufferUtil {

    private BufferUtil() {} 
    
    public static ByteBuffer createNativeByteBuffer(int cap) { 
        ByteBuffer buffer = ByteBuffer.allocateDirect(cap); 
        buffer.order(ByteOrder.nativeOrder()); 
        return buffer; 
    }
    
    public static ShortBuffer createNativeShortBuffer(int cap) { 
        return createNativeByteBuffer(cap * 2).asShortBuffer(); 
    }
    
    public static IntBuffer createNativeIntBuffer(int cap) { 
        return createNativeByteBuffer(cap * 4).asIntBuffer(); 
    }
    
    public static FloatBuffer createNativeFloatBuffer(int cap) { 
        return createNativeByteBuffer(cap * 4).asFloatBuffer(); 
    }
    
}
