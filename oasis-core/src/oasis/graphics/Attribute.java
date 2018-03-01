package oasis.graphics;

/**
 * 
 * Type of vertex data 
 * 
 * @author Nicholas Hamilton 
 *
 */
public enum Attribute {

    /**
     * Positional data, 3 floats (x, y, z) 
     */
    POSITION(3), 
    
    /**
     * Normal data, 3 floats (x, y, z). Should be normalized  
     */
    NORMAL(3), 
    
    /**
     * Color data, 4 floats (r, g, b, a)
     */
    COLOR(4), 
    
    /**
     * Texture coordinates, 2 floats (u, v) 
     */
    TEX_COORD(2), 
    
    /**
     * Tangent coordinates, used for normal mapping, 3 floats (x, y, z) 
     */
    TANGENT(3); 
    
    private final int comps; 
    
    private Attribute(int comps) {
        this.comps = comps; 
    }
    
    public int getFloatCount() {
        return comps; 
    }
    
}
