package oasis.graphics;

public interface NativeShaderResource extends NativeResource {

    boolean isValid(); 
    
    String getErrorMessage(); 
    
    UniformValue[] getUniformValues(); 
    
}