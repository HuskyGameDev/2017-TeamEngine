package oasis.graphics;

import oasis.core.Disposable;
import oasis.math.Matrix4f;
import oasis.math.Vector2f;
import oasis.math.Vector3f;
import oasis.math.Vector4f;

/**
 * Shader program for the graphics device 
 * 
 * Do NOT implement this yourself, instead only use
 * this interface through objects created by a 
 * GraphicsDevice 
 * 
 * @author Nicholas Hamilton
 * 
 */
public interface Shader extends Disposable {

    /**
     * Get the source code of the vertex shader 
     * 
     * @return Vertex shader code 
     */
    String getVertexSource(); 
    
    /**
     * Get the source code of the fragment shader 
     * 
     * @return Fragment shader code 
     */
    String getFragmentSource(); 
    
    /**
     * Set an int uniform 
     * 
     * @param name Uniform name 
     * @param value Int value 
     */
    void setInt(String name, int value); 
    
    /**
     * Set a float uniform 
     * 
     * @param name Uniform name 
     * @param value Float value 
     */
    void setFloat(String name, float value); 
    
    /**
     * Set a 2-vector uniform 
     * 
     * @param name Uniform name 
     * @param value 2-vector value 
     */
    void setVector2f(String name, Vector2f value); 
    
    /**
     * Set a 3-vector uniform 
     * 
     * @param name Uniform name 
     * @param value 3-vector value 
     */
    void setVector3f(String name, Vector3f value);
    
    /**
     * Set a 4-vector uniform 
     * 
     * @param name Uniform name 
     * @param value 4-vector value 
     */
    void setVector4f(String name, Vector4f value);
    
    /**
     * Set a 4x4 matrix uniform 
     * 
     * @param name Uniform name 
     * @param value 4x4 matrix value 
     */
    void setMatrix4f(String name, Matrix4f value);

}
