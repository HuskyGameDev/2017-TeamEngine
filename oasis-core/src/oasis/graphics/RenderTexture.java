package oasis.graphics;

import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.graphics.internal.InternalRenderTexture;

public class RenderTexture extends Texture {

    private Texture.Format depthFormat; 
    
    private InternalRenderTexture internal; 
    
    public RenderTexture(oasis.graphics.Texture.Format format, int width, int height, Texture.Format depthFormat) {
        super(format, width, height);

        Oasis.getGraphicsDevice().linkInternal(this);
        setDepthFormat(depthFormat); 
    }
    
    public RenderTexture(oasis.graphics.Texture.Format format, int width, int height) {
        this(format, width, height, null); 
    }

    void setInternal(InternalRenderTexture internal) {
        this.internal = internal; 
    }
    
    InternalRenderTexture getInternal() {
        return internal; 
    }
    
    public Type getType() {
        return Type.RENDER_TEXTURE; 
    }
    
    public Texture.Format getDepthFormat() {
        return depthFormat; 
    }
    
    public void setDepthFormat(Texture.Format format) {
        if (format != null && !format.isDepthFormat()) {
            throw new OasisException("Texture format must be a depth format or null"); 
        }
        
        this.depthFormat = format; 
        internal.onNewDepthFormat(); 
    }

}
