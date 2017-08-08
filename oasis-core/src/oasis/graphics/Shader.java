package oasis.graphics;

import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public interface Shader {

    String getVertexSource(); 
    String getFragmentSource(); 
    
    void setFloat(String name, float value); 
    void setVector2(String name, Vector2 value); 
    void setVector3(String name, Vector3 value); 
    void setVector4(String name, Vector4 value); 
    void setMatrix4(String name, Matrix4 value);

}
