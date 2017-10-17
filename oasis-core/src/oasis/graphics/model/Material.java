package oasis.graphics.model;

import oasis.graphics.GraphicsDevice;
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

    public RenderQueue renderQueue = RenderQueue.OPAQUE; 
    
    public Texture2D diffuseTexture = null;
    public Vector4f diffuseColor = new Vector4f(1);

    public Texture2D specularTexture = null;
    public Vector4f specularColor = new Vector4f(0);
    public float specularPower = 1.0f;

    public Shader shader = null;

    public void apply(GraphicsDevice gd, Shader shader) {
        shader.setInt("HasDiffuseTexture", diffuseTexture == null ? 0 : 1);
        shader.setInt("HasSpecularTexture", specularTexture == null ? 0 : 1);
        shader.setVector4f("DiffuseColor", diffuseColor);
        shader.setVector4f("SpecularColor", specularColor);
        shader.setFloat("SpecularPower", specularPower);
        
        gd.setTexture(0, diffuseTexture);
        gd.setTexture(1, specularTexture);
        
        shader.setInt("DiffuseTexture", 0);
        shader.setInt("SpecularTexture", 1);
    }
    
}
