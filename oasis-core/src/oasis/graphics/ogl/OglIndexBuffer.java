package oasis.graphics.ogl;

import oasis.graphics.BufferUsage;
import oasis.graphics.IndexBuffer;

public class OglIndexBuffer extends IndexBuffer {

    private Ogl ogl; 
    private HardwareBuffer hwBuffer; 
    
    public OglIndexBuffer(Ogl ogl, int indices, BufferUsage usage) {
        super(indices, usage);
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
                Ogl.GL_ELEMENT_ARRAY_BUFFER, 
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
