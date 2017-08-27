package oasis.graphics;

import oasis.core.Disposable;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

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
    void setVector2(String name, Vector2 value); 
    
    /**
     * Set a 3-vector uniform 
     * 
     * @param name Uniform name 
     * @param value 3-vector value 
     */
    void setVector3(String name, Vector3 value);
    
    /**
     * Set a 4-vector uniform 
     * 
     * @param name Uniform name 
     * @param value 4-vector value 
     */
    void setVector4(String name, Vector4 value);
    
    /**
     * Set a 4x4 matrix uniform 
     * 
     * @param name Uniform name 
     * @param value 4x4 matrix value 
     */
    void setMatrix4(String name, Matrix4 value);

}
