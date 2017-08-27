package oasis.graphics;

import oasis.core.Disposable;

/**
 * Holds a list of vertices.  
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface VertexBuffer extends Disposable {

    /**
     * Gets the buffer usage type of the vertex buffer 
     * 
     * @return Buffer usage 
     */
    BufferUsage getUsage(); 
    
    /**
     * Gets the vertex format of the vertex buffer 
     * 
     * @return Vertex format 
     */
    VertexFormat getFormat(); 
    
    /**
     * Gets the number of vertices in the buffer. 
     * This is equivalent to the number of floats 
     * divided by VertexFormat.getFloatCount()
     * 
     * @return Number of vertices
     */
    int getVertexCount(); 
    
    /**
     * Gets the number of floats in the buffer 
     * 
     * @return Number of floats 
     */
    int getFloatCount(); 
    
    /**
     * Gets the data of the buffer and puts it in
     * [out] 
     * 
     * @param out Array that a copy of data will be stored 
     * @return Reference to [out] 
     */
    float[] getVertices(float[] out); 
    
    /**
     * Gets the data of the buffer 
     * 
     * @return Copy of buffer data 
     */
    float[] getVertices(); 
    
    /**
     * Sets the data of the buffer 
     * 
     * @param data Buffer data 
     */
    void setVertices(float[] data); 
    
}
