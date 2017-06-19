package oasis.graphics.vertex;

import oasis.graphics.Attribute;

public class VertexElement {

    private final Attribute attribute; 
    private final int index; 
    private final int components; 
    
    public VertexElement(Attribute attribute, int index, int components) { 
        this.attribute = attribute; 
        this.index = index; 
        this.components = components; 
    }
    
    public Attribute getAttribute() { 
        return attribute; 
    }
    
    public int getIndex() { 
        return index; 
    }
    
    public int getComponentCount() { 
        return components; 
    }
    
    public int getSizeInBytes() { 
        return components * 4; 
    }
    
}
