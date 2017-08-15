package oasis.graphics;

public final class VertexFormat {

    public static final VertexFormat POSITION_3_NORMAL_3_COLOR_4 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.NORMAL_3, 
            VertexElement.COLOR_4
    }); 
    
    public static final VertexFormat POSITION_3_NORMAL_3_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.NORMAL_3, 
            VertexElement.TEXTURE_2
    }); 
    
    public static final VertexFormat POSITION_3_COLOR_4_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3, 
            VertexElement.COLOR_4, 
            VertexElement.TEXTURE_2
    }); 
    
    public static final VertexFormat POSITION_2_COLOR_3_TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2, 
            VertexElement.COLOR_3, 
            VertexElement.TEXTURE_2
    }); 
    
    public static final VertexFormat POSITION_2_COLOR_3 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2, 
            VertexElement.COLOR_3 
    }); 
    
    public static final VertexFormat POSITION_2 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_2
    }); 
    
    public static final VertexFormat POSITION_3 = new VertexFormat(new VertexElement[] {
            VertexElement.POSITION_3
    }); 
    
    public static final VertexFormat NORMAL_3 = new VertexFormat(new VertexElement[] {
            VertexElement.NORMAL_3
    }); 
    
    public static final VertexFormat COLOR_4 = new VertexFormat(new VertexElement[] {
            VertexElement.COLOR_4
    }); 
    
    public static final VertexFormat TEXTURE_2 = new VertexFormat(new VertexElement[] {
            VertexElement.TEXTURE_2
    }); 
    
    private VertexElement[] elements; 
    private int size; 
    
    public VertexFormat(VertexElement[] elements) {
        this.elements = elements.clone(); 
        size = 0; 
        for (VertexElement e : elements) {
            size += e.getByteCount(); 
        }
    }
    
    public int getElementCount() {
        return elements.length; 
    }
    
    public VertexElement getElement(int index) {
        return elements[index]; 
    }
    
    public int getByteCount() {
        return size; 
    }
    
    public int getFloatCount() {
        return size / 4; 
    }
    
    public boolean equals(VertexFormat d) {
        if (d == this) return true; 
        if (d.elements.length != elements.length) return false; 
        
        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(d.elements[i])) return false; 
        }
        return true; 
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof VertexFormat) {
            return equals((VertexFormat) obj); 
        }
        return false; 
    }
    
}
