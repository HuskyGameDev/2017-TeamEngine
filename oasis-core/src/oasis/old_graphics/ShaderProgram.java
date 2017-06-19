package oasis.old_graphics;

import oasis.graphics.ColorRgba;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public interface ShaderProgram {

    void dispose();
    
    void bind();
    void unbind();
    
    boolean isValid();
    
    void setUniform(String name, int i);
    void setUniform(String name, float f);
    void setUniform(String name, Vector2 r);
    void setUniform(String name, Vector3 r);
    void setUniform(String name, Vector4 r);
    void setUniform(String name, ColorRgba col, boolean justRgb);
    void setUniform(String name, Matrix4 r);
    void setTextureUnit(String name, int unit);
    
}