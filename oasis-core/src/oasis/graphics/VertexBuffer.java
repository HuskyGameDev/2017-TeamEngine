package oasis.graphics;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import oasis.core.Oasis;

public class VertexBuffer extends GraphicsResource<NativeResource> {
    
    private VertexFormat format; 
    private BufferUsage usage; 
    private FloatBuffer buffer; 
    private int size; 
    private boolean needsUpdate = true; 
    
    public VertexBuffer(VertexFormat format, int vertices, BufferUsage usage) {
        this.format = format; 
        this.size = vertices * format.getFloatsPerElement(); 
        this.usage = usage; 
        this.buffer = Oasis.getDirectBufferAllocator().allocate(size * 4).asFloatBuffer(); 
        Oasis.getGraphicsDevice().assignNativeResource(this); 
    }

    public void upload() {
        if (needsUpdate) {
            super.upload(); 
            needsUpdate = false; 
        }
    }
    
    public void release() {
        super.release(); 
        needsUpdate = true; 
    }
    
    public Buffer getAndFlipBuffer() {
        buffer.position(getSizeInFloats()); 
        buffer.flip(); 
        return buffer; 
    }
    
    public Type getResourceType() {
        return Type.TEXTURE_2D; 
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

    public void getVertices(int start, int count, float[] out, int outOffset) {
        int numFloats = format.getFloatsPerElement(); 
        
        buffer.position(start * numFloats); 
        buffer.get(out, outOffset, count * numFloats); 
    }

    public void setVertices(int start, int count, float[] in, int inOffset) {
        int numFloats = format.getFloatsPerElement(); 
        
        buffer.position(start * numFloats); 
        buffer.put(in, inOffset, count * numFloats); 
        
        needsUpdate = true; 
    }

}
