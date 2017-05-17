package nhamil.oasis.graphics;

import nhamil.oasis.math.Matrix4;

public interface Renderer {

    void dispose();
    
    void setProjection(Matrix4 proj);
    void setShaderProgram(ShaderProgram shader);
    
    void begin();
    void end();
    
    void draw(Mesh mesh, Material mat, Matrix4 transform);
    
}
