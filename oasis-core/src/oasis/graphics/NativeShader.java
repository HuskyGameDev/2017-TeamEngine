package oasis.graphics;

public interface NativeShader {

    boolean isValid(); 
    
    String getErrorMessage(); 
    
    UniformValue[] getUniformValues(); 
    
    void release(); 
    
}
