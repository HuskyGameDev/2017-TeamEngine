package oasis.graphics;

import oasis.core.Disposable;

/**
 * Holds vertex and index buffers.  
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface VertexArray extends Disposable {

    // vertex buffer
    
    /**
     * Gets the current number of vertex buffers 
     * attached the the vertex array 
     * 
     * @return Number of attached vertex buffers 
     */
    int getVertexBufferCount(); 
    
    /**
     * Gets the vertex buffer at [index] in the array 
     * 
     * @param index Index in vertex buffer array 
     * @return Vertex buffer 
     */
    VertexBuffer getVertexBuffer(int index); 
    
    /**
     * Sets the array of vertex buffers for the 
     * vertex array 
     * 
     * @param list List of vertex buffers 
     */
    void setVertexBuffers(VertexBuffer[] list); 
    
    /**
     * Sets the array of vertex buffers as just 
     * the one [vbo]
     * 
     * @param vbo Vertex buffer to set 
     */
    void setVertexBuffer(VertexBuffer vbo); 
    
    // index buffer
    
    /**
     * Gets the index buffer attached to the vertex
     * array. There can only be up to one index 
     * buffer in a vertex array 
     * 
     * @return Index buffer 
     */
    IndexBuffer getIndexBuffer(); 
    
    /**
     * Sets the index buffer attached to the vertex
     * array. There can only be up to one index 
     * buffer in a vertex array 
     * 
     * @param ibo Index buffer to set 
     */
    void setIndexBuffer(IndexBuffer ibo); 
    
}
