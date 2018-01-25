package oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Oasis;
import oasis.core.OasisException;

public abstract class RenderTarget {

    public static RenderTarget create() {
        return Oasis.getGraphicsDevice().createRenderTarget(); 
    }
    
    public static RenderTarget create(RenderTexture depth, RenderTexture... colors) {
        RenderTarget rt = create(); 
        rt.setDepthBuffer(depth); 
        rt.setColorBuffers(colors);
        return rt; 
    }
    
    protected RenderTexture depthBuffer; 
    protected final List<RenderTexture> colorBuffers = new ArrayList<>(); 
    
    public RenderTarget() {}
    
    public abstract void dispose(); 
    
    public void dispose(boolean renderTextures) {
        dispose(); 
        
        if (renderTextures) {
            if (depthBuffer != null) {
                depthBuffer.dispose(); 
            }
            
            for (int i = 0; i < colorBuffers.size(); i++) {
                colorBuffers.get(i).dispose(); 
            }
        }
    }
    
    public boolean isValid() {
        return depthBuffer != null || colorBuffers.size() > 0; 
    }
    
    public int getWidth() {
        if (!isValid()) return 0; 
        
        int min = Integer.MAX_VALUE; 
        
        if (depthBuffer != null) min = depthBuffer.getWidth(); 
        
        for (RenderTexture rt : colorBuffers) {
            min = Math.min(rt.getWidth(), min); 
        }
        
        return min; 
    }
    
    public int getHeight() {
        if (!isValid()) return 0; 
        
        int min = Integer.MAX_VALUE; 
        
        if (depthBuffer != null) min = depthBuffer.getHeight(); 
        
        for (RenderTexture rt : colorBuffers) {
            min = Math.min(rt.getHeight(), min); 
        }
        
        return min; 
    }
    
    public RenderTexture getDepthBuffer() {
        return depthBuffer; 
    }
    
    public int getColorBufferCount() {
        return colorBuffers.size(); 
    }
    
    public RenderTexture getColorBuffer(int index) {
        return colorBuffers.get(index); 
    }
    
    public void clear() {
        setDepthBuffer(null); 
        setColorBuffer(null); 
    }
    
    public void setDepthBuffer(RenderTexture depth) {
        if (depth == null) {
            depthBuffer = depth; 
        }
        else {
            if (!depth.getFormat().isDepthFormat()) {
                throw new OasisException("Depth buffer texture must have a depth format"); 
            }
            
            depthBuffer = depth; 
        }
    }
    
    public void setColorBuffer(RenderTexture color) {
        setColorBuffers(color); 
    }
    
    public void setColorBuffers(RenderTexture... colors) {
        if (colors == null) {
            colorBuffers.clear(); 
        }
        
        List<RenderTexture> newList = new ArrayList<>(); 
        
        for (RenderTexture rt : colors) {
            if (rt != null) {
                if (rt.getFormat().isDepthFormat()) {
                    throw new OasisException("Color buffer texture must have a color format"); 
                }
                
                newList.add(rt); 
            }
        }
        
        colorBuffers.clear(); 
        colorBuffers.addAll(newList); 
    }
    
}
