package oasis.graphics.internal;

public interface InternalShader extends InternalResource {

    void uploadUniforms(); 
    
    boolean isValid(); 
    
    String getErrorMessage(); 
    
}