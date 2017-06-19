package oasis.graphics;

import oasis.math.Matrix4;
import oasis.math.Vector3;

public interface Shader {

    String getVertexSource(); 
    
    String getFragmentSource(); 
    
    void setVector3(String name, Vector3 value); 
    
    void setMatrix4(String name, Matrix4 value); 
    
}
