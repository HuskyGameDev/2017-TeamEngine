package oasis.graphics;

import oasis.core.OasisException;
import oasis.graphics.Attribute;

public class VertexFormat {

    public static final VertexFormat POSITION = new VertexFormat(Attribute.POSITION); 
    public static final VertexFormat NORMAL = new VertexFormat(Attribute.NORMAL); 
    public static final VertexFormat COLOR = new VertexFormat(Attribute.COLOR); 
    public static final VertexFormat TEX_COORD = new VertexFormat(Attribute.TEX_COORD); 
    
    private Attribute[] attribs; 
    private int floatsPerElem; 
    
    public VertexFormat(Attribute... attribs) {
        if (attribs == null || attribs.length == 0) {
            throw new OasisException("VertexFormat must have at least one attribute"); 
        }
        
        boolean[] used = new boolean[Attribute.values().length]; 
        
        for (int i = 0; i < attribs.length; i++) {
            if (attribs[i] == null) {
                throw new OasisException("Attribute cannot be null"); 
            }
            if (used[attribs[i].ordinal()]) {
                throw new OasisException("Attribute has already been used: " + attribs[i]); 
            }
            
            floatsPerElem += attribs[i].getFloatCount(); 
            used[attribs[i].ordinal()] = true; 
        }
        
        this.attribs = attribs.clone(); 
    }
    
    public boolean isInterleaved() {
        return getAttributeCount() > 1; 
    }
    
    public int getAttributeCount() {
        return attribs.length; 
    }
    
    public Attribute getAttribute(int index) {
        return attribs[index]; 
    }
    
    public Attribute[] getAttributes() {
        return attribs.clone(); 
    }
    
    public int getFloatsPerElement() {
        return floatsPerElem; 
    }
    
}
