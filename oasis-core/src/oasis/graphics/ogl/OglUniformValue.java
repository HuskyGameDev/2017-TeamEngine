package oasis.graphics.ogl;

public class OglUniformValue {

    private int location; 
    private int uniformId; 
    private OglUniformType type; 
    private Object value; 
    private boolean needsUpdate; 
    private String name; 
    
    public OglUniformValue(String name, int location, int uniformId, OglUniformType type, Object value) {
        this.name = name; 
        this.type = type; 
        this.value = value; 
        this.location = location; 
        this.uniformId = uniformId; 
        this.needsUpdate = true; 
    }
    
    protected void setLocation(int loc) {
        location = loc; 
    }
    
    protected int getLocation() {
        return location; 
    }
    
    protected int getUniformId() {
        return uniformId; 
    }
    
    public String getName() {
        return name; 
    }
    
    public OglUniformType getType() {
        return type; 
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

    public void clear() {
        setValue(null); 
    }
    
}
