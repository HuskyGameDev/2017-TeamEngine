package oasis.graphics.vertex;

public class VertexLayout {

    public static final VertexLayout LAYOUT_POSITION_3_COLOR_4 = new VertexLayout(
            new VertexElement(Attribute.POSITION, 3), 
            new VertexElement(Attribute.COLOR, 4)); 
    
    public static final VertexLayout LAYOUT_POSITION_3_NORMAL_3_COLOR_4 = new VertexLayout(
            new VertexElement(Attribute.POSITION, 3), 
            new VertexElement(Attribute.NORMAL, 3), 
            new VertexElement(Attribute.COLOR, 4));
    
    public static final VertexLayout LAYOUT_POSITION_3_NORMAL_3_COLOR_4_TEXTURE_2 = new VertexLayout(
            new VertexElement(Attribute.POSITION, 3), 
            new VertexElement(Attribute.NORMAL, 3), 
            new VertexElement(Attribute.COLOR, 4), 
            new VertexElement(Attribute.TEXTURE, 2)); 
    
    private final VertexElement[] elements; 
    private final int totalSize; 
    
    public VertexLayout(VertexElement... elements) {
        this.elements = elements.clone(); 
        int sum = 0; 
        for (VertexElement elem : elements) { 
            sum += elem.getSizeInBytes(); 
        }
        totalSize = sum; 
    }
    
    public VertexElement getElement(Attribute attribute) { 
        for (VertexElement elem : elements) { 
            if (elem.getAttribute() == attribute) { 
                return elem; 
            }
        }
        return null; 
    }
    
    public int getOffset(Attribute attribute) { 
        int off = 0; 
        for (VertexElement elem : elements) { 
            if (elem.getAttribute() == attribute) { 
                return off; 
            }
            else { 
                off += elem.getSizeInBytes(); 
            }
        }
        return -1; 
    }
    
    public int getTotalSizeInBytes() { 
        return totalSize; 
    }
    
    public int getVertexElementCount() { 
        return elements.length; 
    }
    
    public VertexElement getVertexElement(int index) { 
        return elements[index]; 
    }
    
    public boolean isSupportedBy(Vertex vert) { 
        for (VertexElement elem : elements) { 
            if (!vert.hasElement(elem)) { 
                return false; 
            }
        }
        return true; 
    }
    
}
