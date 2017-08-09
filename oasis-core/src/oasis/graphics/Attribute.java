package oasis.graphics;

public enum Attribute {
    POSITION("aPosition", 0), 
    NORMAL("aNormal", 1), 
    COLOR("aColor", 2), 
    TANGENT("aTangent", 3), 
    BONE_INDEX("aBoneIndex", 4), 
    BONE_WEIGHT("aBoneWeight", 5), 
    TEXTURE("aTexCoord", 6), 
    TEXTURE2("aTexCoord2", 7), 
    TEXTURE3("aTexCoord3", 8), 
    TEXTURE4("aTexCoord4", 9); 
    
    private final String alias; 
    private final int index;
    
    private Attribute(String alias, int index) { 
        this.alias = alias; 
        this.index = index; 
    }
    
    public String getGlslName() { 
        return alias; 
    }
    
    public int getIndex() { 
        return index; 
    }
    
}
