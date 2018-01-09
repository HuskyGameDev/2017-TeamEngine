package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    HardwareBufferResource createVertexHardwareBufferResource(); 
    
    HardwareBufferResource createIndexHardwareBufferResource(); 
    
    HardwareGeometryResource createHardwareGeometryResource(); 
    
    Shader createShader(String vs, String fs); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void drawGeometry(Geometry geom); 
    
}
