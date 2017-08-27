package oasis.graphics;

/**
 * Describes the format of a vertex. Used for 
 * vertex buffers 
 * 
 * @author Nicholas Hamilton
 *
 */
public final class VertexFormat {

    /**
     * 3 position values, 3 normal values, 4 color values 
     */
    public static final VertexFormat POSITION_3_NORMAL_3_COLOR_4 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.NORMAL_3, 
            VertexElement.COLOR_4
    }); 
    
    /**
     * 3 position values, 3 normal values, 2 texture values 
     */
    public static final VertexFormat POSITION_3_NORMAL_3_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.NORMAL_3, 
            VertexElement.TEXTURE_2
    }); 
    
    /**
     * 3 position values, 4 color values, 2 texture values 
     */
    public static final VertexFormat POSITION_3_COLOR_4_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.COLOR_4, 
            VertexElement.TEXTURE_2
    }); 
    
    /**
     * 2 position values, 3 color values, 2 texture values 
     */
    public static final VertexFormat POSITION_2_COLOR_3_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2, 
            VertexElement.COLOR_3, 
            VertexElement.TEXTURE_2
    }); 
    
    /**
     * 2 position values, 3 color values 
     */
    public static final VertexFormat POSITION_2_COLOR_3 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2, 
            VertexElement.COLOR_3 
    }); 
    
    /**
     * 2 position values 
     */
    public static final VertexFormat POSITION_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2
    }); 
    
    /**
     * 3 position values 
     */
    public static final VertexFormat POSITION_3 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3
    }); 
    
    /**
     * 3 normal values 
     */
    public static final VertexFormat NORMAL_3 = new VertexFormat(new VertexElement[] {
            VertexElement.NORMAL_3
    }); 
    
    /**
     * 4 color values 
     */
    public static final VertexFormat COLOR_4 = new VertexFormat(new VertexElement[] {
            VertexElement.COLOR_4
    }); 
    
    /**
     * 2 texture values 
     */
    public static final VertexFormat TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.TEXTURE_2
    }); 
    
    private VertexElement[] elements; 
    private int size; 
    
    /**
     * Constructor, creates a format with [elements] 
     * 
     * @param elements Vertex elements in the format
     */
    public VertexFormat(VertexElement[] elements) {
        this.elements = elements.clone(); 
        size = 0; 
        for (VertexElement e : elements) {
            size += e.getByteCount(); 
        }
    }
    
    /**
     * Gets the number of elements in the format
     *  
     * @return Elements in format 
     */
    public int getElementCount() {
        return elements.length; 
    }
    
    /**
     * Gets the element at index [index] 
     * 
     * @param index Index in element array 
     * @return Element in format 
     */
    public VertexElement getElement(int index) {
        return elements[index]; 
    }
    
    /**
     * Get total number of bytes that one vertex takes 
     * with this format
     * 
     * @return Number of bytes in format 
     */
    public int getByteCount() {
        return size; 
    }
    
    /**
     * Get total number of floats that one vertex takes 
     * with this format 
     * 
     * @return Number of floats in format 
     */
    public int getFloatCount() {
        return size / 4; 
    }
    
    /**
     * Checks if two formats are equal 
     * 
     * @param f Other format 
     * @return If formats are equal 
     */
    public boolean equals(VertexFormat f) {
        if (f == this) return true; 
        if (f.elements.length != elements.length) return false; 
        
        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(f.elements[i])) return false; 
        }
        return true; 
    }
    
    @Override 
    public boolean equals(Object obj) {
        if (obj instanceof VertexFormat) {
            return equals((VertexFormat) obj); 
        }
        return false; 
    }
    
}
