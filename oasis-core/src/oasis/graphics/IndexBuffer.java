package oasis.graphics;

import oasis.core.Disposable;

/**
 * Holds indices for geometry. 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface IndexBuffer extends Disposable {

    /**
     * Get the buffer usage type of this buffer 
     * 
     * @return Buffer usage 
     */
    BufferUsage getUsage(); 

    /** 
     * Get number of indices in the buffer 
     * 
     * @return Number of indices 
     */
    int getIndexCount(); 
    
    /** 
     * Get the indices and fill [out] with them 
     * 
     * @param out Array to fill with index values 
     * @return Reference to [out] 
     */
    int[] getIndices(int[] out); 
    
    /**
     * Get a copy of index values 
     * 
     * @return Index values 
     */
    int[] getIndices(); 
    
    /**
     * Set the index data of the buffer 
     * 
     * @param inds New index data 
     */
    void setData(int[] inds); 
    
}
