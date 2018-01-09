package oasis.graphics;

import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public abstract class Shader extends GraphicsResource {

    private String vs; 
    private String fs; 
    
    public Shader(String vs, String fs) {
        this.vs = vs; 
        this.fs = fs; 
    }
    
    public abstract void upload(); 
    
    public abstract boolean isValid(); 
    public abstract String getErrorMessage(); 
    
    public abstract Uniform[] getUniforms(); 
    public abstract boolean isUniform(String name); 
    public abstract Uniform getUniform(String name); 
    
    public abstract void clearUniform(String name); 
    public abstract void setInt(String name, int value); 
    public abstract void setFloat(String name, float value); 
    public abstract void setVector2(String name, Vector2 value); 
    public abstract void setVector3(String name, Vector3 value); 
    public abstract void setVector4(String name, Vector4 value); 
    public abstract void setMatrix3(String name, Matrix3 value); 
    public abstract void setMatrix4(String name, Matrix4 value);
    
    public void reset() {
        if (!isValid()) return; 
        
        Uniform[] list = getUniforms();
        
        for (int i = 0; i < list.length; i++) {
            clearUniform(list[i].getName()); 
        }
    }
    
    public String getVertexSource() {
        return vs; 
    }
    
    public String getFragmentSource() {
        return fs; 
    }
    
}
