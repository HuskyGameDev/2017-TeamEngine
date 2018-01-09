package oasis.graphics;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import oasis.core.Oasis;

public abstract class VertexBuffer extends GraphicsResource {
    
    private VertexFormat format; 
    private BufferUsage usage; 
    private FloatBuffer buffer; 
    private int size; 
    
    public VertexBuffer(VertexFormat format, int vertices, BufferUsage usage) {
        this.format = format; 
        this.size = vertices * format.getFloatsPerElement(); 
        this.usage = usage; 
        this.buffer = Oasis.getDirectBufferAllocator().allocate(size * 4).asFloatBuffer(); 
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
    
    public VertexFormat getFormat() {
        return format; 
    }

    public BufferUsage getUsage() {
        return usage;
    }
    
    public int getSizeInBytes() {
        return size * 4; 
    }

    public int getSizeInFloats() {
        return size; 
    }

    public int getVertexCount() {
        return size / format.getFloatsPerElement(); 
    }

    public void resize(int vertices) {
        int sizeInFloats = vertices * format.getFloatsPerElement(); 
        
        if (sizeInFloats > buffer.capacity()) {
            Oasis.getDirectBufferAllocator().free(buffer); 
            buffer = Oasis.getDirectBufferAllocator().allocate(sizeInFloats * 4).asFloatBuffer(); 
        }
        size = sizeInFloats; 
        buffer.limit(size * sizeInFloats); 
    }

    public void getVertices(int start, int count, int outOffset, float[] out) {
        int numFloats = format.getFloatsPerElement(); 
        
        buffer.position(start * numFloats); 
        buffer.get(out, outOffset, count * numFloats); 
    }

    public void setVertices(int start, int count, int inOffset, float[] in) {
        int numFloats = format.getFloatsPerElement(); 
        
        buffer.position(start * numFloats); 
        buffer.put(in, inOffset, count * numFloats); 
    }
    
}
