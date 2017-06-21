package oasis.graphics.internal;

import oasis.math.Matrix4;
import oasis.math.Vector3;

public interface NativeShader {

    String getVertexSource(); 
    
    String getFragmentSource(); 
    
    void setFloat(String name, float value); 
    
    void setVector3(String name, Vector3 value); 
    
    void setMatrix4(String name, Matrix4 value);

}
