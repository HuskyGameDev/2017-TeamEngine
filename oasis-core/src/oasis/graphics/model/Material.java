package oasis.graphics.model;

import oasis.graphics.FrontFace;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.math.Vector4f;

/**
 * Shader settings for a mesh
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Material {

    /**
     * Determines which queue to put a mesh in
     */
    public RenderQueue renderQueue = RenderQueue.OPAQUE; 
    
    /**
     * If a mesh uses a texture, put it here 
     */
    public Texture2D diffuseTexture = null;
    /**
     * If a mesh has a tint, put it here 
     */
    public Vector4f diffuseColor;

    /**
     * If a mesh has a specular texture, put it here 
     */
    public Texture2D specularTexture = null;
    /**
     * If a mesh has a specular tint, put it here
     * All 1's means the mesh is fully specular
     * All 0's means the mesh is not specular at all 
     */
    public Vector4f specularColor;
    /**
     * How shiny a mesh is. 
     * Smaller numbers mean more spread out highlights 
     */
    public float specularPower = 20.0f;

    /**
     * Texture shown even without any light
     */
    public Texture2D emissiveTexture = null; 
    /**
     * Color shown even without any light
     */
    public Vector4f emissiveColor = null; 
    
    /**
     * Uses the model renderer's default shader otherwise 
     */
    public Shader shader = null;

    /**
     * Front face
     */
    public FrontFace frontFace = FrontFace.BOTH; 
    
    /**
     * Applies material values as uniforms to a shader 
     * 
     * @param gd graphics device 
     * @param shader shader to apply uniforms to 
     */
    public void apply(GraphicsDevice gd, Shader shader, boolean firstPass) {
        shader.setInt("Material.HasDiffuseTexture", diffuseTexture == null ? 0 : 1);
        shader.setInt("Material.HasSpecularTexture", specularTexture == null ? 0 : 1);
        shader.setInt("Material.HasEmissiveTexture", emissiveTexture == null ? 0 : 1);
        shader.setVector4f("Material.DiffuseColor", diffuseColor == null ? new Vector4f(1, 0, 1, 1) : diffuseColor);
        shader.setVector4f("Material.SpecularColor", specularColor == null ? new Vector4f(0, 0, 0, 1) : specularColor);
        shader.setVector4f("Material.EmissiveColor", (!firstPass || emissiveColor == null) ? new Vector4f(0, 0, 0, 1) : emissiveColor);
        shader.setFloat("Material.SpecularPower", specularPower);
        
        gd.setFrontFace(frontFace); 
        
        gd.setTexture(0, diffuseTexture);
        gd.setTexture(1, specularTexture);
        gd.setTexture(2, emissiveTexture); 
        shader.setInt("Material.DiffuseTexture", 0);
        shader.setInt("Material.SpecularTexture", 1);
        shader.setInt("Material.EmissiveTexture", 2);
    }
    
}
