package nhamil.oasis.graphics;

public interface Shader {

    void setFloat(String var, float val);
    void setVector2(String var, float x, float y);
    void setVector3(String var, float x, float y, float z);
    void setVector4(String var, float x, float y, float z, float w);
    
}
