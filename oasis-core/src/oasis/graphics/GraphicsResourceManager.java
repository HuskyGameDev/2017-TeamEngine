package oasis.graphics;

import oasis.graphics.internal.NativeMesh;
import oasis.graphics.internal.NativeShader;

public interface GraphicsResourceManager {

    NativeShader createNativeShaderFromSource(String vertex, String fragment); 
    
    NativeMesh createNativeMesh(); 
    
}
