package oasis.graphics.vertex;

import oasis.core.Disposable;

public interface IndexBuffer extends Disposable {

    BufferUsage getUsage(); 

    int getIndexCount(); 
    int[] getIndices(int[] out); 
    int[] getIndices(); 
    
    void setData(int[] inds); 
    
}
