package oasis.graphics;

import java.nio.ShortBuffer;

import oasis.core.Oasis;

public class IndexBuffer extends GraphicsResource {

    private ShortBuffer buffer; 
    private int size;
    private BufferUsage usage; 
    private HardwareBufferResource hwBuffer; 
    
    public IndexBuffer(int indices, BufferUsage usage) {
        this.size = indices; 
        this.usage = usage; 
        this.buffer = Oasis.getDirectBufferAllocator().allocate(size * 2).asShortBuffer(); 
        this.hwBuffer = Oasis.getGraphicsDevice().createIndexHardwareBufferResource(); 
    }
    
    public void upload() {
        buffer.position(getSizeInShorts()); 
        buffer.flip(); 
        hwBuffer.upload(getSizeInBytes(), buffer, usage); 
    }
    
    public void release() {
        hwBuffer.release(); 
    }
    
    public HardwareBufferResource getHardwareBuffer() {
        return hwBuffer; 
    }
    
    public BufferUsage getUsage() {
        return usage; 
    }

    public int getSizeInBytes() {
        return size * 2; 
    }
    
    public int getSizeInShorts() {
        return size; 
    }
    
    public int getIndexCount() {
        return size; 
    }

    public void resize(int indices) {
        if (indices > buffer.capacity()) {
            Oasis.getDirectBufferAllocator().free(buffer); 
            buffer = Oasis.getDirectBufferAllocator().allocate(indices * 2).asShortBuffer(); 
        }
        size = indices; 
        buffer.limit(size); 
    }

    public void getIndices(int start, int count, short[] out, int outOffset) {
        buffer.position(start); 
        buffer.get(out, outOffset, count); 
    }

    public void setIndices(int start, int count, short[] in, int inOffset) {
        buffer.position(start); 
        buffer.put(in, inOffset, count); 
    }
    
}
