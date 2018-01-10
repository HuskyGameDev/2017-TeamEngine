package oasis.graphics.ogl;

import oasis.core.Logger;
import oasis.core.OasisException;
import oasis.graphics.Attribute;
import oasis.graphics.NativeShader;
import oasis.graphics.Uniform;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class OglShader implements NativeShader {

    private static final Logger log = new Logger(OglShader.class); 
    
    private static int currentId = 0; 
    
    private String vs, fs; 
    private Ogl ogl; 
    private int id;
    private boolean valid = false; 
    private boolean compiled = false; 
    private String error = ""; 
    
    private boolean foundUniforms = false; 
    private OglUniformValue[] oglUniformValues; 
    
    public OglShader(Ogl ogl, String vs, String fs) {
        this.vs = vs; 
        this.fs = fs; 
        this.ogl = ogl; 
        
        compileAndLink(); 
    }
    
    protected static void bind(Ogl ogl, OglShader shader) {
        if (shader == null && currentId != 0) {
            ogl.glUseProgram(0); 
        }
        else if (currentId != shader.getId()) {
            shader.compileAndLink(); 
            ogl.glUseProgram(shader.getId()); 
        }
    }

    protected int getId() {
        compileAndLink(); 
        return id; 
    }
    
    private void compileAndLink() {
        if (compiled) return; 
        
        compiled = true; 
        
        int[] status = new int[1]; 
        int[] length = new int[1]; 
        byte[] text = new byte[512]; 
        
        boolean validSoFar = true; 
        
        int vert, frag; 
        id = ogl.glCreateProgram(); 
        vert = ogl.glCreateShader(Ogl.GL_VERTEX_SHADER); 
        frag = ogl.glCreateShader(Ogl.GL_FRAGMENT_SHADER); 
        
        ogl.glShaderSource(vert, 1, new String[] { vs }, new int[] { vs.length() }, 0);
        ogl.glCompileShader(vert);
        ogl.glGetShaderiv(vert, Ogl.GL_COMPILE_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
            error = new String(text).trim(); 
            log.warning("Vertex Shader Compile Error: " + error);
            validSoFar = false; 
        }
        else {
            log.debug("Vertex Shader Compile Success!");
        }
        
        ogl.glShaderSource(frag, 1, new String[] { fs }, new int[] { fs.length() }, 0);
        ogl.glCompileShader(frag);
        ogl.glGetShaderiv(frag, Ogl.GL_COMPILE_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetShaderInfoLog(frag, 512, length, 0, text, 0);
            error = new String(text).trim(); 
            log.warning("Fragment Shader Compile Error: " + error);
            validSoFar = false; 
        }
        else {
            log.debug("Fragment Shader Compile Success!");
        }
        
        ogl.glAttachShader(id, vert);
        ogl.glAttachShader(id, frag);
        
        bindAttributes(); 
        
        ogl.glLinkProgram(id);
        ogl.glGetProgramiv(id, Ogl.GL_LINK_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
            error = new String(text).trim(); 
            log.warning("Link Error: " + error);
            validSoFar = false; 
        }
        else {
            log.debug("Shader Link Success!");
        }
        
        ogl.glDeleteShader(vert);
        ogl.glDeleteShader(frag);
        
        if (validSoFar) {
            valid = true; 
        }
        else {
            throw new OasisException("Shader compilation failed!"); 
        }
    }
    
    private void bindAttributes() { 
        for (Attribute a : Attribute.values()) { 
            ogl.glBindAttribLocation(
                    id,
                    OglConvert.getAttributeId(a), 
                    OglConvert.getAttributeName(a)
            );
        }
    }
    
    private void findUniforms() {
        compileAndLink(); 
        
        bind(ogl, this); 
        
        if (isValid() && !foundUniforms) {
            foundUniforms = true; 
            
            int[] count = new int[1]; 
            
            ogl.glGetProgramiv(id, Ogl.GL_ACTIVE_UNIFORMS, count, 0);
            
            oglUniformValues = new OglUniformValue[count[0]]; 
            
            byte[] name = new byte[1024]; 
            int[] length = new int[1]; 
            int[] size = new int[1]; 
            int[] type = new int[1]; 
            
            for (int i = 0; i < count[0]; i++) {
                ogl.glGetActiveUniform(id, i, 1024, length, 0, size, 0, type, 0, name, 0);
                
                String realName = new String(name).substring(0, length[0]); 
                
                oglUniformValues[i] = new OglUniformValue(
                        ogl.glGetUniformLocation(id, realName), 
                        new Uniform(OglConvert.getUniformType(type[0]), realName), 
                        null
                ); 
            }
        }
    }
    
    protected void upload() {
        compileAndLink(); 
        
        if (isValid()) {
            findUniforms(); 
            
            bind(ogl, this); 
            
            for (int i = 0; i < oglUniformValues.length; i++) {
                if (oglUniformValues[i].needsUpdate()) {
                    OglUniformValue uv = oglUniformValues[i]; 
                    int location = uv.getLocation(); 
                    Uniform u = uv.getUniform(); 
                    Object v = uv.getValue(); 
                    
//                    log.debug("Setting uniform: " + u.getName() + ", " + location); 
                    
                    switch (u.getType()) {
                    default: 
                        throw new OasisException("Unknown uniform type: " + u.getType()); 
                    case INT: 
                        Integer integer = (Integer) v; 
//                        log.debug("Integer: " + integer); 
                        ogl.glUniform1i(location, integer == null ? 0 : integer);
                        break; 
                    case FLOAT: 
                        Float f = (Float) v; 
//                        log.debug("Float: " + f); 
                        ogl.glUniform1f(location, f == null ? 0.0f : f);
                        break; 
                    case VECTOR2: 
                        Vector2 v2 = (Vector2) v; 
//                        log.debug("Vector2: " + v2); 
                        if (v2 != null) {
                            ogl.glUniform2f(location, v2.x, v2.y);
                        }
                        else {
                            ogl.glUniform2f(location, 0, 0); 
                        }
                        break; 
                    case VECTOR3: 
                        Vector3 v3 = (Vector3) v; 
//                        log.debug("Vector3: " + v3);
                        if (v3 != null) {
                            ogl.glUniform3f(location, v3.x, v3.y, v3.z);
                        }
                        else {
                            ogl.glUniform3f(location, 0, 0, 0); 
                        }
                        break; 
                    case VECTOR4: 
                        Vector4 v4 = (Vector4) v; 
//                        log.debug("Vector4: " + v4); 
                        if (v4 != null) {
                            ogl.glUniform4f(location, v4.x, v4.y, v4.z, v4.w);
                        }
                        else {
                            ogl.glUniform4f(location, 0, 0, 0, 0); 
                        }
                        break; 
                    case MATRIX3: 
                        Matrix3 m3 = (Matrix3) v; 
//                        log.debug("Matrix3: " + m3); 
                        if (m3 != null) {
                            ogl.glUniformMatrix3fv(location, 1, false, m3.get(new float[9]), 0);
                        }
                        else {
                            ogl.glUniformMatrix3fv(location, 1, false, new float[9], 0);
                        }
                        break; 
                    case MATRIX4: 
                        Matrix4 m4 = (Matrix4) v; 
//                        log.debug("Matrix4: " + m4); 
                        if (m4 != null) {
                            ogl.glUniformMatrix4fv(location, 1, false, m4.get(new float[16]), 0);
                        }
                        else {
                            ogl.glUniformMatrix4fv(location, 1, false, new float[16], 0);
                        }
                        break; 
                    }
                    
                    oglUniformValues[i].clearNeedsUpdate(); 
                }
            }
        }
    }
    
    @Override
    public void release() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid() {
        compileAndLink(); 
        return valid; 
    }

    // empty if none, otherwise the error message
    @Override
    public String getErrorMessage() {
        compileAndLink(); 
        return error; 
    }

    @Override
    public oasis.graphics.UniformValue[] getUniformValues() {
        findUniforms(); 
        
        return oglUniformValues.clone();
    }

}
