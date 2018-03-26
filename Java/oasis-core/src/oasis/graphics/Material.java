package oasis.graphics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import oasis.core.OasisException;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

/**
 * 
 * Data about materials (diffuse, specular, etc.) 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Material {

    private FrontFace frontFace = FrontFace.CCW; 
    private RenderQueue renderQueue = RenderQueue.OPAQUE; 
    private float alpha = 1.0f; 
    private Vector3 diffuse = new Vector3(1); 
    private Vector3 specular = new Vector3(0); 
    private float specularPower = 20.0f; 
    private Vector3 emissive = new Vector3(0); 
    private Shader shader = null; 
    
    private Texture diffuseTex = null; 
    private Texture normalTex = null; 
    
    private Map<Integer, UniformValue> uniforms = new HashMap<>(); 
    
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
    
    public Texture getDiffuseTexture() {
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
    
    public Texture getNormalTexture() {
        return normalTex; 
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
    
    public void setDiffuseMap(Texture tex) {
        this.diffuseTex = tex; 
    }
    
    public void setNormalMap(Texture tex) {
        this.normalTex = tex; 
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

    // custom uniforms 
    
    public void applyCustomUniforms() {
        for (Entry<Integer, UniformValue> e : uniforms.entrySet()) {
            int id = e.getKey(); 
            UniformValue value = e.getValue(); 
            
            switch (value.getType()) 
            {
            default: throw new OasisException("Unknown uniform type: " + value.getType()); 
            case INT: 
                Shader.setInt(id, (Integer) value.getValue()); 
                break; 
            case FLOAT: 
                Shader.setFloat(id, (Float) value.getValue()); 
                break; 
            case VECTOR2: 
                Shader.setVector2(id, (Vector2) value.getValue()); 
                break; 
            case VECTOR3: 
                Shader.setVector3(id, (Vector3) value.getValue()); 
                break; 
            case VECTOR4: 
                Shader.setVector4(id, (Vector4) value.getValue()); 
                break; 
            case MATRIX3: 
                Shader.setMatrix3(id, (Matrix3) value.getValue()); 
                break; 
            case MATRIX4: 
                Shader.setMatrix4(id, (Matrix4) value.getValue()); 
                break; 
            case TEXTURE: 
                Shader.setTexture(id, (Texture) value.getValue()); 
            }
        }
    }
    
    public void unapplyCustomUniforms() {
        for (Entry<Integer, UniformValue> e : uniforms.entrySet()) {
            int id = e.getKey(); 
            Shader.clearValue(id); 
        }
    }
    
    private UniformValue getUniformValue(int id) {
        return uniforms.get(id); 
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getUniformValue(int id, UniformType type, Class<T> cast) {
        UniformValue uv = getUniformValue(id); 
        if (uv == null) return null; 
        
        if (uv.getType() == type) {
            return (T) uv.getValue(); 
        }
        else {
            return null; 
        }
    }
    
    public void clearValue(int id) {
        uniforms.remove(id); 
    }
    
    public void setInt(int id, Integer i) {
        getUniformValue(id).setInt(i); 
    }
    
    public void setFloat(int id, Float f) {
        getUniformValue(id).setFloat(f); 
    }
    
    public void setVector2(int id, Vector2 v) {
        getUniformValue(id).setVector2(v); 
    }
    
    public void setVector3(int id, Vector3 v) {
        getUniformValue(id).setVector3(v); 
    }
    
    public void setVector4(int id, Vector4 v) {
        getUniformValue(id).setVector4(v); 
    }
    
    public void setMatrix3(int id, Matrix3 m) {
        getUniformValue(id).setMatrix3(m); 
    }
    
    public void setMatrix4(int id, Matrix4 m) {
        getUniformValue(id).setMatrix4(m); 
    }
    
    public void setTexture(int id, Texture t) {
        getUniformValue(id).setTexture(t); 
    }
    
    public Integer getInt(int id) {
        return getUniformValue(id, UniformType.INT, Integer.class); 
    }
    
    public Float getFloat(int id) {
        return getUniformValue(id, UniformType.FLOAT, Float.class); 
    }
    
    public Vector2 getVector2(int id) {
        return getUniformValue(id, UniformType.VECTOR2, Vector2.class); 
    }
    
    public Vector3 getVector3(int id) {
        return getUniformValue(id, UniformType.VECTOR3, Vector3.class); 
    }
    
    public Vector4 getVector4(int id) {
        return getUniformValue(id, UniformType.VECTOR4, Vector4.class); 
    }
    
    public Matrix3 getMatrix3(int id) {
        return getUniformValue(id, UniformType.MATRIX3, Matrix3.class); 
    }
    
    public Matrix4 getMatrix4(int id) {
        return getUniformValue(id, UniformType.MATRIX4, Matrix4.class); 
    }
    
    public Texture getTexture(int id) {
        return getUniformValue(id, UniformType.TEXTURE, Texture.class); 
    }
    
}
