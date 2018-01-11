package oasis.graphics;

import oasis.core.OasisException;

public abstract class GraphicsResource<T extends NativeResource> {

    public enum Type {
        
        VERTEX_BUFFER, 
        
        INDEX_BUFFER, 
        
        GEOMETRY, 
        
        SHADER, 
        
        TEXTURE_2D; 
        
    }
    
    private T nativeRes = null; 
    
    public abstract Type getResourceType(); 
    
    public final T getNativeResource() {
        return nativeRes; 
    }
    
    public final void setNativeResource(T res) {
        if (nativeRes != null) {
            throw new OasisException("Resource already has a native counterpart"); 
        }
        
        nativeRes = res; 
    }
    
    public void upload() {
        if (nativeRes == null) {
            throw new OasisException("Resource cannot upload data because there is no native resource associated with it"); 
        }
        nativeRes.update(); 
    }
    
    public void release() {
        if (nativeRes == null) {
            throw new OasisException("Resource cannot be released because there is no native resource associated with it"); 
        }
        nativeRes.release(); 
    }
    
}
