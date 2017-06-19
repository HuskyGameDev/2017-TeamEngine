package oasis.graphics;

public enum Attribute {
    POSITION("inPosition"), 
    NORMAL("inNormal"), 
    COLOR("inColor"), 
    TEXTURE("inTexCoord"), 
    TANGENT("inTangent"), 
    BONE_INDEX("inBoneIndex"), 
    BONE_WEIGHT("inBoneWeight"); 
    
    private final String alias; 
    
    private Attribute(String alias) { 
        this.alias = alias; 
    }
    
    public String getGlslName() { 
        return alias; 
    }
    
    public String getGlslName(int index) { 
        return alias + index; 
    }
}
