package oasis.graphics;

import java.nio.Buffer;
import java.nio.ShortBuffer;

import oasis.core.Oasis;

public abstract class IndexBuffer extends GraphicsResource {

    private ShortBuffer buffer; 
    private int size;
    private BufferUsage usage; 
    
    public IndexBuffer(int indices, BufferUsage usage) {
        this.size = indices; 
        this.usage = usage; 
        this.buffer = Oasis.getDirectBufferAllocator().allocate(size * 2).asShortBuffer(); 
    }
    
    public abstract void upload(); 
    
    protected Buffer getBuffer() {
        return buffer; 
    }
    
    protected Buffer getAndFlipBuffer() {
        buffer.position(size); 
        buffer.flip(); 
        return buffer; 
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

    public void getIndices(int start, int count, int outOffset, short[] out) {
        buffer.position(start); 
        buffer.get(out, outOffset, count); 
    }

    public void setIndices(int start, int count, int inOffset, short[] in) {
        buffer.position(start); 
        buffer.put(in, inOffset, count); 
    }
    
}
