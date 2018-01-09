package oasis.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DefaultDirectBufferAllocator implements DirectBufferAllocator {

    @Override
    public ByteBuffer allocate(int bytes) {
        return ByteBuffer.allocateDirect(bytes).order(ByteOrder.nativeOrder()); 
    }
    
    @Override
    public void free(Buffer buffer) {
        // Default allocator does not free direct buffers  
        // it instead lets the garbage collector clean it 
    }

}
