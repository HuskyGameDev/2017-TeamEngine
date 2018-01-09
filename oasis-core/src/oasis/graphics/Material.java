package oasis.graphics;

import oasis.math.Vector3;
import oasis.math.Vector4;

public class Material {

    private RenderQueue renderQueue = RenderQueue.OPAQUE; 
    private float alpha = 1.0f; 
    private Vector3 diffuse = new Vector3(); 
    private Vector3 specular = new Vector3(); 
    private float specularPower = 20.0f; 
    private Vector3 emissive = new Vector3(); 
    private Shader shader = null; 
    
    public Material() {}

    public void apply(Shader shader) {
        shader.setVector4("oasis_DiffuseColor", new Vector4(diffuse, alpha)); 
        shader.setVector4("oasis_SpecularColor", new Vector4(specular, specularPower));
        shader.setVector3("oasis_EmissiveColor", emissive); 
    }
    
    public RenderQueue getRenderQueue() {
        return renderQueue; 
    }
    
    public float getAlpha() {
        return alpha; 
    }
    
    public Vector3 getDiffuse() {
        return new Vector3(diffuse);
    }

    public Vector3 getSpecular() {
        return new Vector3(specular);
    }

    public Vector3 getEmissive() {
        return new Vector3(emissive);
    }

    public Shader getShader() {
        return shader;
    }
    
    public void setRenderQueue(RenderQueue rq) {
        renderQueue = rq; 
    }
    
    public void setAlpha(float a) {
        this.alpha = a; 
    }

    public void setDiffuse(Vector3 diffuse) {
        this.diffuse.set(diffuse);
    }

    public void setSpecular(Vector3 specular) {
        this.specular.set(specular);
    }

    public void setEmissive(Vector3 emissive) {
        this.emissive.set(emissive);
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    } 
    
}
