package oasis.graphics.vertex;

import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsResource;

public class IndexBuffer extends GraphicsResource {

    private int[] inds = null; 
    private BufferUsage usage = BufferUsage.DYNAMIC; 
    
    public IndexBuffer(GraphicsDevice graphics) { 
        graphics.update(this); 
    }
    
    // getters 
    
    public int getLength() { 
        return inds == null ? 0 : inds.length; 
    }
    
    public int[] getIndices() { 
        return inds; 
    }
    
    public BufferUsage getUsage() { 
        return usage; 
    }
    
    // setters 
    
    public void setIndices(int[] inds) { 
        this.inds = inds; 
        dirty = true;
    }
    
    public void setUsage(BufferUsage usage) { 
        this.usage = usage; 
        dirty = true; 
    }
    
}
