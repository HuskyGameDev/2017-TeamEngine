package oasis.graphics.model;

public enum RenderQueue {

    /** no transparency */ 
    OPAQUE, 
    
    /** fully opaque or fully transparent */
    TRANSLUCENT, 
    
    /** 
     * partial transparency. 
     * Avoid this whenever you can, as it can be slower and less accurate 
     */
    TRANSPARENT, 
    
}
