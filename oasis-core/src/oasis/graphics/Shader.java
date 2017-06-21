package oasis.graphics;

import oasis.graphics.internal.NativeShader;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class Shader {

    private NativeShader impl; 
    
    public Shader(GraphicsDevice graphics, String vs, String fs) { 
        impl = graphics.getResourceManager().createNativeShaderFromSource(vs, fs); 
    }
    
    public NativeShader getNativeShader() { 
        return impl; 
    }
    
    public String getVertexSource() {
        return impl.getVertexSource(); 
    }

    public String getFragmentSource() {
        return impl.getFragmentSource(); 
    }
    
    public void setFloat(String name, float value) { 
        impl.setFloat(name, value); 
    }

    public void setVector3(String name, Vector3 value) {
        impl.setVector3(name, value);
    }

    public void setMatrix4(String name, Matrix4 value) {
        impl.setMatrix4(name, value);
    }

}
