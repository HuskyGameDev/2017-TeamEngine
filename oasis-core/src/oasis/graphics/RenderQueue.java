package oasis.graphics;

/**
 * Determines what type of material a mesh is. 
 * This allows the model renderer to be more efficient. 
 * 
 * @author Nicholas Hamilton 
 *
 */
public enum RenderQueue {

    /** no transparency */ 
    OPAQUE, 
    
    /** fully opaque or fully transparent */
    TRANSPARENT, 
    
    /** 
     * partial transparency. This includes things like tinted glass, water, etc. 
     * Avoid this when you can, as it can be slower and less accurate 
     */
    TRANSLUCENT, 
    
}
