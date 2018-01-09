package oasis.graphics.ogl;

import oasis.graphics.Uniform;

public class UniformValue {

    private int location; 
    private Uniform uniform; 
    private Object value; 
    private boolean needsUpdate; 
    
    public UniformValue(int location, Uniform uniform, Object value) {
        this.uniform = uniform; 
        this.value = value; 
        this.location = location; 
        this.needsUpdate = true; 
    }
    
    public int getLocation() {
        return location; 
    }
    
    public Uniform getUniform() {
        return uniform; 
    }
    
    public Object getValue() {
        return value; 
    }
    
    public void setValue(Object val) {
        if (value == null || !value.equals(val)) {
            this.value = val; 
            this.needsUpdate = true; 
        }
    }
    
    public boolean needsUpdate() {
        return needsUpdate; 
    }
    
    public void clearNeedsUpdate() {
        needsUpdate = false; 
    }
    
}
