package oasis.graphics;

import oasis.math.Vector4;

public interface GraphicsDevice {

    void preRender();

    void postRender();

    NativeBuffer createNativeBuffer(NativeBuffer.Type type); 
    
    NativeGeometry createNativeGeometry(); 
    
    NativeShader createNativeShader(String vs, String fs); 
    
    NativeTexture createNativeTexture(Texture.Type type, Texture.Format format, int width, int height, int depth); 
    
    void clearBuffers(Vector4 color, boolean depth); 
    
    void setState(GraphicsState state); 
    
    void setShader(Shader sp); 
    
    void drawGeometry(Geometry geom); 
    
}
