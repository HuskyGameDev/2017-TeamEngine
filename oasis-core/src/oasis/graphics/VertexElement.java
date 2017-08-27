package oasis.graphics;

/**
 * A component of a vertex format. Vertex elements are
 * what make up a format. 
 * 
 * @author Nicholas Hamilton
 *
 */
public final class VertexElement {

    /**
     * Element using Position attribute and has 3 floats 
     */
    public static final VertexElement POSITION_3 = new VertexElement(Attribute.POSITION, 3); 
    
    /**
     * Uses Position attribute and has 2 floats 
     */
    public static final VertexElement POSITION_2 = new VertexElement(Attribute.POSITION, 2); 
    
    /**
     * Uses Normal attribute and has 3 floats 
     */
    public static final VertexElement NORMAL_3 = new VertexElement(Attribute.NORMAL, 3); 
    
    /**
     * Uses Texture attribute and has 2 floats 
     */
    public static final VertexElement TEXTURE_2 = new VertexElement(Attribute.TEXTURE, 2); 
    
    /**
     * Uses Color attribute and has 4 floats 
     */
    public static final VertexElement COLOR_4 = new VertexElement(Attribute.COLOR, 4); 
    
    /**
     * Uses Color attribute and has 3 floats 
     */
    public static final VertexElement COLOR_3 = new VertexElement(Attribute.COLOR, 3); 
    
    private Attribute attr; 
    private int count; 
    
    /**
     * Constructor 
     * 
     * @param attribute Attribute type 
     * @param count Number of floats 
     */
    public VertexElement(Attribute attribute, int count) {
        this.attr = attribute; 
        this.count = count; 
    }
    
    /**
     * Gets attribute type 
     * 
     * @return Attribute 
     */
    public Attribute getAttribute() {
        return attr; 
    }
    
    /**
     * Gets number of components 
     * 
     * @return Number of components 
     */
    public int getCount() {
        return count; 
    }
    
    /**
     * Gets number of bytes in the element 
     * 
     * @return Number of bytes 
     */
    public int getByteCount() {
        return count * 4; // TODO currently only floats are available, this should change
    }
    
    /**
     * Checks if two vertex elements are equal 
     * 
     * @param e Other element 
     * @return If elements are equal 
     */
    public boolean equals(VertexElement e) {
        if (e == this) return true; 
        if (e.attr == attr && e.count == count) return true; 
        return false; 
    }
    
    /**
     * Checks if two objects are equal 
     * 
     * @return If objects are equal 
     */
    public boolean equals(Object obj) {
        if (obj instanceof VertexElement) {
            return equals((VertexElement) obj); 
        }
        return false; 
    }
    
}
