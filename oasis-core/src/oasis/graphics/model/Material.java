package oasis.graphics.model;

import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.math.Vector4f;

/**
 * Shader settings for a mesh
 * 
 * @author thinic
 *
 */
public class Material {

    public Texture2D diffuseTexture = null;
    public Vector4f diffuseColor = null;

    public Texture2D specularTexture = null;
    public Vector4f specularColor = null;
    public float specularPower = 1.0f;

    public Shader shader = null;

}
