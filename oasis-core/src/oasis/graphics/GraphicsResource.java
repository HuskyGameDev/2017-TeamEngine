package oasis.graphics;

import oasis.core.OasisException;
import oasis.graphics.internal.InternalResource;

public abstract class GraphicsResource<T extends InternalResource> {

    protected T internal = null; 
    
    protected void onAddInternalResource() {}
    
    protected void onRemoveInternalResource() {} 
    
    public final T getInternalResource() {
        return internal; 
    }
    
    public final void setInternalResource(T res) {
        if (internal != null) onRemoveInternalResource(); 
        
        internal = res; 
        
        onAddInternalResource(); 
    }
    
    public void release() {
        if (internal == null) {
            throw new OasisException("Resource cannot be released because there is no native resource associated with it"); 
        }
        internal.release(); 
    }
    
}
