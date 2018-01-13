package oasis.graphics.internal;

import oasis.graphics.UniformValue;

public interface InternalShader extends InternalResource {

    void uploadUniforms(); 
    
    boolean isValid(); 
    
    String getErrorMessage(); 
    
    UniformValue[] getUniformValues(); 
    
}