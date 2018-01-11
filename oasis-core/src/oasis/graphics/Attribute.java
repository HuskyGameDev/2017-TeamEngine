package oasis.graphics;

public enum Attribute {

    POSITION(3), 
    
    NORMAL(3), 
    
    COLOR(4), 
    
    TEX_COORD(2), 
    
    TANGENT(3); 
    
    private final int comps; 
    
    private Attribute(int comps) {
        this.comps = comps; 
    }
    
    public int getFloatCount() {
        return comps; 
    }
    
}
