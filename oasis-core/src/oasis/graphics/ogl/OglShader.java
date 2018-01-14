package oasis.graphics.ogl;

import java.util.HashMap;
import java.util.Map;

import oasis.core.Logger;
import oasis.core.OasisException;
import oasis.graphics.Attribute;
import oasis.graphics.Shader;
import oasis.graphics.Texture;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class OglShader extends Shader {

    private static final Logger log = new Logger(OglShader.class);

    private static int currentId = -1; 
    
    private Ogl ogl;

    private int id;
    private boolean valid = false;
    private String error = "";
    
    private boolean foundUniforms = false; 
    private OglUniformValue[] oglUniformValues; 

    public OglShader(Ogl ogl, String vs, String fs) {
        super(vs, fs);
        this.ogl = ogl;
    }

    protected static void bind(Ogl ogl, OglShader shader) {
        if (shader == null && currentId != 0) {
            ogl.glUseProgram(0); 
        }
        else if (currentId != shader.id) {
            ogl.glUseProgram(shader.id); 
        }
    }
    
    public void bindAndApplyUniforms() {
        validate(); 
        bind(ogl, this); 
        
        collectUniforms(); 
        uploadUniforms(); 
    }

    @Override
    public boolean isValid() {
        validate();
        return valid;
    }

    @Override
    public String getErrorMessage() {
        validate();
        return error;
    }

    private void validate() {
        if (id != 0) return;

        compileAndLink(); 
    }

    private void compileAndLink() {
        error = "";
        int[] status = new int[1];
        int[] length = new int[1];
        byte[] text = new byte[512];

        boolean validSoFar = true;

        int vert, frag;
        id = ogl.glCreateProgram();
        vert = ogl.glCreateShader(Ogl.GL_VERTEX_SHADER);
        frag = ogl.glCreateShader(Ogl.GL_FRAGMENT_SHADER);

        ogl.glShaderSource(vert, 1, new String[] { getBaseVertexSource() }, new int[] { getBaseVertexSource().length() }, 0);
        ogl.glCompileShader(vert);
        ogl.glGetShaderiv(vert, Ogl.GL_COMPILE_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
            addError(new String(text).trim());
            log.warning("Vertex Shader Compile Error: " + error);
            validSoFar = false;
        } else {
            log.debug("Vertex Shader Compile Success!");
        }

        ogl.glShaderSource(frag, 1, new String[] { getBaseFragmentSource() }, new int[] { getBaseFragmentSource().length() }, 0);
        ogl.glCompileShader(frag);
        ogl.glGetShaderiv(frag, Ogl.GL_COMPILE_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetShaderInfoLog(frag, 512, length, 0, text, 0);
            addError(new String(text).trim());
            log.warning("Fragment Shader Compile Error: " + error);
            validSoFar = false;
        } else {
            log.debug("Fragment Shader Compile Success!");
        }

        ogl.glAttachShader(id, vert);
        ogl.glAttachShader(id, frag);

        bindAttributes();

        ogl.glLinkProgram(id);
        ogl.glGetProgramiv(id, Ogl.GL_LINK_STATUS, status, 0);
        if (status[0] != Ogl.GL_TRUE) {
            ogl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
            addError(new String(text).trim());
            log.warning("Link Error: " + error);
            validSoFar = false;
        } else {
            log.debug("Shader Link Success!");
        }

        ogl.glDeleteShader(vert);
        ogl.glDeleteShader(frag);

        valid = validSoFar; 
        
        findUniforms(); 
    }

    private void addError(String err) {
        if (error.equals("")) {
            error = err;
        } else {
            error += "\n";
            error += err;
        }
    }

    private void bindAttributes() {
        for (Attribute a : Attribute.values()) {
            ogl.glBindAttribLocation(id, OglConvert.getAttributeId(a), OglConvert.getAttributeName(a));
        }
    }

    private void findUniforms() {
        if (valid && !foundUniforms) {
            bind(ogl, this);
            
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

                oglUniformValues[i] = new OglUniformValue(realName, ogl.glGetUniformLocation(id, realName), Shader.getUniformId(realName), OglConvert.getOglUniformType(type[0]), null);
            }
        }
    }
    
    private void collectUniforms() {
        for (int i = 0; i < oglUniformValues.length; i++) {
            OglUniformValue uv = oglUniformValues[i]; 
            
            switch (uv.getType()) {
            default: 
                throw new OasisException("Unknown uniform type: " + uv.getType()); 
            case INT: 
                uv.setValue(Shader.getInt(uv.getUniformId()));
                break; 
            case FLOAT: 
                uv.setValue(Shader.getFloat(uv.getUniformId()));
                break; 
            case VECTOR2: 
                uv.setValue(Shader.getVector2(uv.getUniformId()));
                break; 
            case VECTOR3: 
                uv.setValue(Shader.getVector3(uv.getUniformId()));
                break; 
            case VECTOR4: 
                uv.setValue(Shader.getVector4(uv.getUniformId()));
                break; 
            case MATRIX3: 
                uv.setValue(Shader.getMatrix3(uv.getUniformId()));
                break; 
            case MATRIX4: 
                uv.setValue(Shader.getMatrix4(uv.getUniformId()));
                break; 
            case TEXTURE_2D: 
                uv.setValue(Shader.getTexture(uv.getUniformId()));
            }
        }
    }
    
    private void uploadUniforms() {
        validate(); 
        
        if (isValid()) {
            findUniforms(); 
            
            bind(ogl, this); 
            
            int nextTexUnit = 0; 
            Map<Integer, Integer> mappedTextures = new HashMap<>(); 
            
            collectUniforms(); 
            
            for (int i = 0; i < oglUniformValues.length; i++) {
//                if (oglUniformValues[i].needsUpdate() || oglUniformValues[i].getType() == OglUniformType.TEXTURE_2D) {
                {
                    OglUniformValue uv = oglUniformValues[i]; 
                    int location = uv.getLocation(); 
                    Object v = uv.getValue(); 
                    
//                    log.debug("Setting uniform: " + uv.getName() + ", " + location + ", " + uv.getUniformId()); 
                    
                    switch (uv.getType()) {
                    default: 
                        throw new OasisException("Unknown uniform type: " + uv.getType()); 
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
                    case TEXTURE_2D: 
//                        log.debug("texture2d uniform update");
                        Texture texture = (Texture) v; 
                        
                        if (texture == null) {
                            ogl.glUniform1i(location, 0); 
                            break; 
                        }
                        
                        switch (texture.getType()) {
                        default: 
                            throw new OasisException("Unsupported texture type for sampler2D: " + texture.getType()); 
                        case RENDER_TEXTURE:
                            OglRenderTexture rt = OglGraphicsDevice.safeCast(texture, OglRenderTexture.class); 
                            
                            if (rt != null) {
                                int id = rt.getId(); 
                                
                                Integer unit = mappedTextures.get(id); 
                                if (unit == null) {
    //                                log.debug("Binding " + id + " to " + nextTexUnit + " for " + u.getName()); 
                                    ogl.glActiveTexture(Ogl.GL_TEXTURE0 + nextTexUnit);
                                    ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id); 
                                    ogl.glUniform1i(location, nextTexUnit); 
                                    mappedTextures.put(id, nextTexUnit++); 
                                }
                                else {
                                    ogl.glUniform1i(location, unit.intValue()); 
                                }
                            }
//                            else {
//                                ogl.glUniform1i(location, 0); 
//                            }
                            
                            break; 
                        case TEXTURE_2D: 
                            OglTexture2D tex = OglGraphicsDevice.safeCast(texture, OglTexture2D.class); 
                            
                            if (tex != null) {
                                int id = tex.getId(); 
                                Integer unit = mappedTextures.get(id); 
                                if (unit == null) {
    //                                log.debug("Binding " + id + " to " + nextTexUnit + " for " + u.getName()); 
                                    ogl.glActiveTexture(Ogl.GL_TEXTURE0 + nextTexUnit);
                                    ogl.glBindTexture(Ogl.GL_TEXTURE_2D, id); 
                                    ogl.glUniform1i(location, nextTexUnit); 
                                    mappedTextures.put(id, nextTexUnit++); 
                                }
                                else {
                                    ogl.glUniform1i(location, unit.intValue()); 
                                }
                            }
//                            else {
//    //                            log.debug("null texture, should bind a blank texture?");
//                                ogl.glUniform1i(location, 0); 
//                            }
                            break; 
                        }
                        
                        break; 
                    }
                    
                    oglUniformValues[i].clearNeedsUpdate(); 
                }
            }
        }
    }

}
