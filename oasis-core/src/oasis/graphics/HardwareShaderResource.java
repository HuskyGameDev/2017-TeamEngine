package oasis.graphics;

public interface HardwareShaderResource {

    boolean isValid(); 
    
    String getErrorMessage(); 
    
    UniformValue[] getUniformValues(); 
    
    void release(); 
    
}
