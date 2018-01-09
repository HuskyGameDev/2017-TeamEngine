package oasis.graphics.ogl;

import oasis.graphics.BufferUsage;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;

public class OglVertexBuffer extends VertexBuffer {

    private Ogl ogl; 
    private HardwareBuffer hwBuffer; 
    
    public OglVertexBuffer(Ogl ogl, VertexFormat format, int vertices, BufferUsage usage) {
        super(format, vertices, usage);
        this.ogl = ogl; 
        this.hwBuffer = new HardwareBuffer(); 
    }

    protected int getId() {
        return hwBuffer.getId(); 
    }
    
    @Override
    public void upload() {
        hwBuffer.upload(
                ogl, 
                Ogl.GL_ARRAY_BUFFER, 
                getSizeInBytes(), 
                getAndFlipBuffer(), 
                OglConvert.getBufferUsage(getUsage())
        ); 
    }

    @Override
    public void release() {
        hwBuffer.release(ogl); 
    }

}
