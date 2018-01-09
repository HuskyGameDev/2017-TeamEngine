package oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Oasis;

public class Geometry extends GraphicsResource {

    private HardwareGeometryResource hwGeom; 
    
    private Primitive primitive = Primitive.TRIANGLE_LIST; 
    private IndexBuffer ib; 
    private List<VertexBuffer> vbs = new ArrayList<>(); 
    
    public Geometry() {
        hwGeom = Oasis.getGraphicsDevice().createHardwareGeometryResource(); 
    }
    
    public boolean isValid() {
        return vbs.size() > 0; 
    }
    
    public Primitive getPrimitive() {
        return primitive; 
    }
    
    public void setPrimitive(Primitive prim) {
        primitive = prim; 
    }
    
    public HardwareGeometryResource getHardwareGeometryResource() {
        return hwGeom; 
    }
    
    public VertexBuffer[] getVertexBuffers() {
        return vbs.toArray(new VertexBuffer[vbs.size()]); 
    }
    
    public int getVertexBufferCount() {
        return vbs.size(); 
    }
    
    public VertexBuffer getVertexBuffer(int index) {
        return vbs.get(index); 
    }
    
    public boolean hasIndexBuffer() {
        return ib != null; 
    }
    
    public IndexBuffer getIndexBuffer() {
        return ib; 
    }
    
    public void setIndexBuffer(IndexBuffer ib) {
        this.ib = ib; 
    }
    
    public void setVertexBuffer(VertexBuffer vb) {
        setVertexBuffers(vb); 
    }
    
    public void setVertexBuffers(VertexBuffer... vbs) {
        this.vbs.clear(); 
        
        if (vbs != null) {
            for (int i = 0; i < vbs.length; i++) {
                if (vbs[i] != null) this.vbs.add(vbs[i]); 
            }
        }
    }
    
    public void upload() {
        hwGeom.upload(ib, getVertexBuffers());
    }
    
    @Override
    public void release() {
        hwGeom.release(); 
    }

}
