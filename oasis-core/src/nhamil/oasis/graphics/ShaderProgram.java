package nhamil.oasis.graphics;

import nhamil.oasis.core.Disposable;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;

public interface ShaderProgram extends Disposable {

    void setInt(String name, int i);
    void setFloat(String name, float f);
    
    void setVector2(String name, Vector2 r);
    void setVector3(String name, Vector3 r);
    void setVector3(String name, ColorRgba col);
    
    void setVector4(String name, ColorRgba col);
    
    void setMatrix4(String name, Matrix4 r);
    
    void setTextureUnit(String name, int i);
    
    boolean isValid();
    
}
