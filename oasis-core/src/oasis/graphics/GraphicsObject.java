package oasis.graphics;

public abstract class GraphicsObject {

    protected GraphicsDevice graphicsDevice;
    private boolean dirty = true; 
    
    public GraphicsObject(GraphicsDevice graphics) {
        graphicsDevice = graphics; 
    }
    
    // getters
    
    public boolean isDirty() { 
        return dirty; 
    }
    
    // setters
    
    public void setDirtyFlag() { 
        dirty = true;
    }
    
    public void clearDirtyFlag() { 
        dirty = false; 
    }
    
}
