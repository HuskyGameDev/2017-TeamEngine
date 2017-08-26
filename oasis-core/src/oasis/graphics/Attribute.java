package oasis.graphics;

/**
 * Supported shader attribute types and GLSL default names  
 * 
 * @author Nicholas Hamilton
 *
 */
public enum Attribute {
    
    /**
     * Vertex position, typically a 3-vector 
     */
    POSITION("aPosition", 0), 
    
    /**
     * Vertex normal, typically a 3-vector 
     */
    NORMAL("aNormal", 1), 
    
    /**
     * Vertex color, typically a 3- or 4-vector 
     */
    COLOR("aColor", 2), 
    
    /**
     * Vertex tangent, typically a 3-vector
     */
    TANGENT("aTangent", 3), 
    
    /**
     * Vertex bone indices, vector size should typically be the same as BONE_WEIGHT
     */
    BONE_INDEX("aBoneIndex", 4),
    
    /**
     * Vertex bone weights, vector size should typically be the same as BONE_INDEX 
     */
    BONE_WEIGHT("aBoneWeight", 5), 
    
    /**
     * Vertex UV coordinates, typically a 2-vector 
     */
    TEXTURE("aTexCoord", 6), 
    
    /**
     * Extra vertex UV coordinates, typically a 2-vector 
     */
    TEXTURE2("aTexCoord2", 7), 
    
    /**
     * Extra vertex UV coordinates, typically a 2-vector 
     */
    TEXTURE3("aTexCoord3", 8), 
    
    /**
     * Extra vertex UV coordinates, typically a 2-vector 
     */
    TEXTURE4("aTexCoord4", 9); 
    
    private final String alias; 
    private final int index;
    
    private Attribute(String alias, int index) { 
        this.alias = alias; 
        this.index = index; 
    }
    
    /**
     * GLSL name. Use this unless you are using GLSL 330, where you would instead 
     * set the attribute location to getIndex() 
     * 
     * @return Default GLSL name 
     */
    public String getGlslName() { 
        return alias; 
    }
    
    /**
     * GLSL index. This is the index used by shaders. 
     * If you are not using the default GLSL name, you should 
     * set the layout location to this value 
     * 
     * @return Index of attribute 
     */
    public int getIndex() { 
        return index; 
    }
    
}
