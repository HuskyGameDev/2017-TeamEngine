package oasis.graphics;

import java.nio.FloatBuffer;

import oasis.core.Oasis;

public class VertexBuffer extends GraphicsResource {
    
    private VertexFormat format; 
    private BufferUsage usage; 
    private FloatBuffer buffer; 
    private HardwareBufferResource hwBuffer; 
    private int size; 
    
    public VertexBuffer(VertexFormat format, int vertices, BufferUsage usage) {
        this.format = format; 
        this.size = vertices * format.getFloatsPerElement(); 
        this.usage = usage; 
        this.buffer = Oasis.getDirectBufferAllocator().allocate(size * 4).asFloatBuffer(); 
        this.hwBuffer = Oasis.getGraphicsDevice().createHardwareBufferResource(HardwareBufferResource.Type.VERTEX); 
    }

    public void upload() {
        buffer.position(getSizeInFloats()); 
        buffer.flip(); 
        hwBuffer.upload(getSizeInBytes(), buffer, usage); 
    }

    public void release() {
        this.hwBuffer.release(); 
    }
    
    public HardwareBufferResource getHardwareBuffer() {
        return hwBuffer; 
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
    }

}
