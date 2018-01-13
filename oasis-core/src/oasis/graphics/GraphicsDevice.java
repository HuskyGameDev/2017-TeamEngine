package oasis.graphics;

import oasis.graphics.internal.InternalBuffer;
import oasis.graphics.internal.InternalGeometry;
import oasis.graphics.internal.InternalRenderTexture;
import oasis.graphics.internal.InternalShader;
import oasis.graphics.internal.InternalTexture2D;
import oasis.math.Vector4;

public abstract class GraphicsDevice {

    public abstract void preRender();

    public abstract void postRender();

    public abstract void linkInternal(VertexBuffer vb); 
    
    public abstract void linkInternal(IndexBuffer ib); 
    
    public abstract void linkInternal(Geometry g); 
    
    public abstract void linkInternal(Shader s); 
    
    public abstract void linkInternal(Texture2D t); 
    
    public abstract void linkInternal(RenderTexture t); 
    
    public abstract void clearBuffers(Vector4 color, boolean depth); 
    
    public abstract void setState(GraphicsState state); 
    
    public abstract void setShader(Shader sp); 
    
    public abstract void setRenderTexture(RenderTexture rt); 
    
    public abstract void drawGeometry(Geometry geom); 
    
    protected final void linkInternal(VertexBuffer vb, InternalBuffer internal) {
        vb.setInternal(internal); 
    }
    
    protected final void linkInternal(IndexBuffer ib, InternalBuffer internal) {
        ib.setInternal(internal); 
    }
    
    protected final void linkInternal(Geometry g, InternalGeometry internal) {
        g.setInternal(internal); 
    }
    
    protected final void linkInternal(Texture2D t, InternalTexture2D internal) {
        t.setInternal(internal); 
    }
    
    protected final void linkInternal(RenderTexture t, InternalRenderTexture internal) {
        t.setInternal(internal); 
    }
    
    protected final void linkInternal(Shader s, InternalShader internal) {
        s.setInternal(internal); 
    }
    
    protected final InternalBuffer getInternal(VertexBuffer vb) {
        return vb.getInternal(); 
    }
    
    protected final InternalBuffer getInternal(IndexBuffer ib) {
        return ib.getInternal(); 
    }
    
    protected final InternalGeometry getInternal(Geometry g) {
        return g.getInternal(); 
    }
    
    protected final InternalShader getInternal(Shader s) {
        return s.getInternal(); 
    }
    
    protected final InternalTexture2D getInternal(Texture2D t) {
        return t.getInternal(); 
    }
    
    protected final InternalRenderTexture getInternal(RenderTexture rt) {
        return rt.getInternal(); 
    }
    
}
