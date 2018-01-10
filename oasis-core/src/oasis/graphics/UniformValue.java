package oasis.graphics;

public interface UniformValue {

    Uniform getUniform(); 
    
    Object getValue(); 
    
    void setValue(Object value); 
    
    void clear(); 
    
}
