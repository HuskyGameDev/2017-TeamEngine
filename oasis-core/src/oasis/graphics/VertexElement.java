package oasis.graphics;

public final class VertexElement {

    public static final VertexElement POSITION_3 = new VertexElement(Attribute.POSITION, 3); 
    public static final VertexElement POSITION_2 = new VertexElement(Attribute.POSITION, 2); 
    public static final VertexElement NORMAL_3 = new VertexElement(Attribute.NORMAL, 3); 
    public static final VertexElement TEXTURE_2 = new VertexElement(Attribute.TEXTURE, 2); 
    public static final VertexElement COLOR_4 = new VertexElement(Attribute.COLOR, 4); 
    public static final VertexElement COLOR_3 = new VertexElement(Attribute.COLOR, 3); 
    
    private Attribute attr; 
    private int count; 
    
    public VertexElement(Attribute attribute, int count) {
        this.attr = attribute; 
        this.count = count; 
    }
    
    public Attribute getAttribute() {
        return attr; 
    }
    
    public int getCount() {
        return count; 
    }
    
    public int getByteCount() {
        return count * 4; // TODO currently only floats are available, this should change
    }
    
    public boolean equals(VertexElement e) {
        if (e == this) return true; 
        if (e.attr == attr && e.count == count) return true; 
        return false; 
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof VertexElement) {
            return equals((VertexElement) obj); 
        }
        return false; 
    }
    
}
