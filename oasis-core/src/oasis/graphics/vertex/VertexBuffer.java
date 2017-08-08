package oasis.graphics.vertex;

import oasis.core.Disposable;

public interface VertexBuffer extends Disposable {

    BufferUsage getUsage(); 
    
    VertexFormat getFormat(); 
    
    int getVertexCount(); 
    int getFloatCount(); 
    float[] getData(float[] out); 
    float[] getData(); 
    
    void setData(float[] data); 
    
}
