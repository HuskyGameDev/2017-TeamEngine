package oasis.graphics.vertex;

public class VertexElement {

    private final Attribute attribute; 
    private final int components; 
    
    public VertexElement(Attribute attribute, int components) { 
        this.attribute = attribute; 
        this.components = components; 
    }
    
    public Attribute getAttribute() { 
        return attribute; 
    }
    
    public int getComponentCount() { 
        return components; 
    }
    
    public int getSizeInBytes() { 
        return components * 4; 
    }
    
}
