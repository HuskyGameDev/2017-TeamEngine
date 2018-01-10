package oasis.graphics;

import oasis.math.Vector3;

public class Material {

    private FrontFace frontFace = FrontFace.CCW; 
    private RenderQueue renderQueue = RenderQueue.OPAQUE; 
    private float alpha = 1.0f; 
    private Vector3 diffuse = new Vector3(); 
    private Vector3 specular = new Vector3(); 
    private float specularPower = 20.0f; 
    private Vector3 emissive = new Vector3(); 
    private Shader shader = null; 
    
    private Texture2D diffuseTex = null; 
    
    public Material() {}

    public RenderQueue getRenderQueue() {
        return renderQueue; 
    }
    
    public FrontFace getFrontFace() {
        return frontFace; 
    }
    
    public float getAlpha() {
        return alpha; 
    }
    
    public Vector3 getDiffuseColor() {
        return new Vector3(diffuse);
    }
    
    public Texture2D getDiffuseTexture() {
        return diffuseTex; 
    }

    public Vector3 getSpecularColor() {
        return new Vector3(specular);
    }
    
    public float getSpecularPower() {
        return specularPower; 
    }

    public Vector3 getEmissiveColor() {
        return new Vector3(emissive);
    }

    public Shader getShader() {
        return shader;
    }
    
    public void setFrontFace(FrontFace face) {
        this.frontFace = face; 
    }
    
    public void setRenderQueue(RenderQueue rq) {
        renderQueue = rq; 
    }
    
    public void setAlpha(float a) {
        this.alpha = a; 
    }

    public void setDiffuseColor(Vector3 diffuse) {
        this.diffuse.set(diffuse);
    }
    
    public void setDiffuseTexture(Texture2D tex) {
        this.diffuseTex = tex; 
    }

    public void setSpecularColor(Vector3 specular) {
        this.specular.set(specular);
    }

    public void setEmissiveColor(Vector3 emissive) {
        this.emissive.set(emissive);
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void setSpecularPower(float pow) {
        this.specularPower = pow; 
    } 
    
}
