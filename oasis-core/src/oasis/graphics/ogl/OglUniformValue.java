package oasis.graphics.ogl;

import oasis.graphics.Uniform;
import oasis.graphics.UniformValue;

public class OglUniformValue implements UniformValue {

    private int location; 
    private Uniform uniform; 
    private Object value; 
    private boolean needsUpdate; 
    
    public OglUniformValue(int location, Uniform uniform, Object value) {
        this.uniform = uniform; 
        this.value = value; 
        this.location = location; 
        this.needsUpdate = true; 
    }
    
    protected void setLocation(int loc) {
        location = loc; 
    }
    
    protected int getLocation() {
        return location; 
    }
    
    public Uniform getUniform() {
        return uniform; 
    }
    
    public Object getValue() {
        return value; 
    }
    
    public void setValue(Object val) {
        if (value == val) return; 
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

    @Override
    public void clear() {
        setValue(null); 
    }
    
}
