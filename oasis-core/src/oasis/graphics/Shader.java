package oasis.graphics;

import java.util.HashMap;
import java.util.Map;

import oasis.core.Oasis;

public class Shader extends GraphicsResource<NativeShaderResource> {

    private String vs; 
    private String fs; 
    
    private UniformValue[] uniformValues; 
    private Uniform[] uniforms; 
    private Map<String, UniformValue> uniformMap; 
    private boolean isCompiled = false; 
    
    public Shader(String vs, String fs) {
        this.vs = vs; 
        this.fs = fs; 
        
        Oasis.getGraphicsDevice().assignNativeResource(this); 
    }
    
    public Type getResourceType() {
        return Type.SHADER;  
    }
    
    private void checkResources() {
        if (!isCompiled) {
            uniformValues = getNativeResource().getUniformValues(); 
            
            uniforms = new Uniform[uniformValues.length]; 
            uniformMap = new HashMap<>(); 
            
            for (int i = 0; i < uniformValues.length; i++) {
                uniforms[i] = uniformValues[i].getUniform(); 
                uniformMap.put(uniformValues[i].getUniform().getName(), uniformValues[i]); 
            }
            
        }
    }
    
    public void upload() {
        super.upload(); 
        checkResources(); 
    }
    
    public boolean isValid() {
        return getNativeResource().isValid(); 
    }
    
    public String getErrorMessage() {
        return getNativeResource().getErrorMessage(); 
    }
    
    public Uniform[] getUniforms() {
        checkResources(); 
        return uniforms.clone(); 
    }
    
    public UniformValue[] getUniformValues() {
        checkResources(); 
        return uniformValues.clone(); 
    }
    
    public boolean isUniform(String name) {
        checkResources(); 
        return uniformMap.containsKey(name); 
    }
    
    public UniformValue getUniformValue(String name) {
        checkResources(); 
        return uniformMap.get(name); 
    }
    
    public UniformValue getSafeUniformValue(String name) {
        UniformValue value = getUniformValue(name); 
        
        return value == null ? NullUniformValue.VALUE : value; 
    }
    
    public Uniform getUniform(String name) {
        UniformValue value = getUniformValue(name); 
        
        return value == null ? null : value.getUniform(); 
    }
    
    public void reset() {
        checkResources(); 
        
        for (int i = 0; i < uniformValues.length; i++) {
            uniformValues[i].clear(); 
        }
    }
    
    public String getVertexSource() {
        return vs; 
    }
    
    public String getFragmentSource() {
        return fs; 
    }
    
}
