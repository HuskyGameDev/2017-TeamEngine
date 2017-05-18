package nhamil.oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import nhamil.oasis.core.EngineException;

// TODO REMOVE ???

public class VertexDefinition {

    public static final VertexDefinition COMMON = new VertexDefinition()
            .addAttribute("Position", Attribute.Vector3)
            .addAttribute("Normal", Attribute.Vector3)
            .addAttribute("Color", Attribute.Vector4)
            .addAttribute("TexCoord", Attribute.Vector2)
            .finish();
    
    public enum Attribute {
        Float(1), 
        Vector2(2), 
        Vector3(3), 
        Vector4(4);
        
        private final int numFloats;
        
        private Attribute(int numFloats) {
            this.numFloats = numFloats;
        }
        
        public int getFloatCount() {
            return numFloats;
        }
        
        public int getSize() {
            return numFloats * 4;
        }
    }
    
    public class AttributeBinding {
        private String name;
        private Attribute type;
        
        public AttributeBinding(String name, Attribute type) {
            this.name = name;
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public Attribute getAttribute() {
            return type;
        }
    }
    
    private List<AttributeBinding> attribs;
    private int size;
    private boolean finish;
    
    public VertexDefinition() {
        attribs = new ArrayList<>();
        finish = false;
        size = 0;
    }
    
    public VertexDefinition finish() {
        finish = true;
        return this;
    }
    
    public boolean isFinished() {
        return finish;
    }
    
    public VertexDefinition addAttribute(String name, Attribute attrib) {
        if (finish) {
            throw new EngineException("VertexDefinition is finalized, cannot add new attributes");
        }
        
        attribs.add(new AttributeBinding(name, attrib));
        size += attrib.getSize();
        return this;
    }
    
    public int getAttributeCount() {
        return attribs.size();
    }
    
    public AttributeBinding getAttribute(int index) {
        return attribs.get(index);
    }
    
    public int getSize() {
        return size;
    }
    
}
