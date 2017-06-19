package oasis.graphics;

public abstract class GraphicsResource {

    protected Object internal = null; 
    protected boolean dirty = true; 
    
    public boolean isDirty() { 
        return dirty; 
    }
    
    public Object getInternalResource() { 
        return internal; 
    }
    
    public void setDirty(boolean dirty) { 
        this.dirty = dirty; 
    }
    
    public void setInternalResource(Object internal) { 
        this.internal = internal; 
    }
    
}
