package oasis.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public abstract class Shader {

    private static class UniformValue {

        private UniformType type; 
        private Object value; 
        
        public UniformValue() {
            this.type = UniformType.NONE; 
            this.value = null; 
        }
        
        public UniformType getType() {
            return type; 
        }

        public Object getValue() {
            return value; 
        }
        
        public void clear() {
            type = UniformType.NONE; 
            value = null; 
        }
        
        private void checkNull() {
            if (value == null) {
                type = UniformType.NONE; 
            }
        }
        
        public void setInt(Integer i) {
            type = UniformType.INT; 
            value = i; 
            checkNull(); 
        }
        
        public void setFloat(Float f) {
            type = UniformType.FLOAT; 
            value = f; 
            checkNull(); 
        }
        
        public void setVector2(Vector2 v) {
            type = UniformType.VECTOR2; 
            value = v; 
            checkNull(); 
        }
        
        public void setVector3(Vector3 v) {
            type = UniformType.VECTOR3; 
            value = v; 
            checkNull(); 
        }
        
        public void setVector4(Vector4 v) {
            type = UniformType.VECTOR4; 
            value = v; 
            checkNull(); 
        }
        
        public void setMatrix3(Matrix3 m) {
            type = UniformType.MATRIX3; 
            value = m; 
            checkNull(); 
        }
        
        public void setMatrix4(Matrix4 m) {
            type = UniformType.MATRIX4; 
            value = m; 
            checkNull(); 
        }
        
        public void setTexture(Texture t) {
            type = UniformType.TEXTURE; 
            value = t; 
            checkNull(); 
        }
        
    }
    
    private static final List<String> uniformNames = new ArrayList<>(); 
    private static final List<UniformValue> uniformValues = new ArrayList<>(); 
    private static final Map<String, Integer> uniformMap = new HashMap<>(); 
    
    public static Shader create(String vs, String fs) {
        return Oasis.getGraphicsDevice().createShader(vs, fs); 
    }
    
    public static int getUniformId(String name) {
        Integer id = uniformMap.get(name); 
        if (id == null) {
            uniformNames.add(name); 
            uniformValues.add(new UniformValue()); 
            int newId = uniformNames.size() - 1; 
            uniformMap.put(name, newId); 
            return newId; 
        }
        else {
            return id.intValue(); 
        }
    }
    
    public static String getUniformName(int id) {
        getUniformValue(id); 
        return uniformNames.get(id); 
    }
    
    private static UniformValue getUniformValue(int id) {
        if (id < 0 || id >= uniformValues.size()) {
            throw new OasisException("Uniform id is invalid: " + id); 
        }
        return uniformValues.get(id); 
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T getUniformValue(int id, UniformType type, Class<T> cast) {
        UniformValue uv = getUniformValue(id); 
        if (uv.getType() == type) {
            return (T) uv.getValue(); 
        }
        else {
            return null; 
        }
    }
    
    public static void clearValue(int id) {
        getUniformValue(id).clear(); 
    }
    
    public static void setInt(int id, Integer i) {
        getUniformValue(id).setInt(i); 
    }
    
    public static void setFloat(int id, Float f) {
        getUniformValue(id).setFloat(f); 
    }
    
    public static void setVector2(int id, Vector2 v) {
        getUniformValue(id).setVector2(v); 
    }
    
    public static void setVector3(int id, Vector3 v) {
        getUniformValue(id).setVector3(v); 
    }
    
    public static void setVector4(int id, Vector4 v) {
        getUniformValue(id).setVector4(v); 
    }
    
    public static void setMatrix3(int id, Matrix3 m) {
        getUniformValue(id).setMatrix3(m); 
    }
    
    public static void setMatrix4(int id, Matrix4 m) {
        getUniformValue(id).setMatrix4(m); 
    }
    
    public static void setTexture(int id, Texture t) {
        getUniformValue(id).setTexture(t); 
    }
    
    public static Integer getInt(int id) {
        return getUniformValue(id, UniformType.INT, Integer.class); 
    }
    
    public static Float getFloat(int id) {
        return getUniformValue(id, UniformType.FLOAT, Float.class); 
    }
    
    public static Vector2 getVector2(int id) {
        return getUniformValue(id, UniformType.VECTOR2, Vector2.class); 
    }
    
    public static Vector3 getVector3(int id) {
        return getUniformValue(id, UniformType.VECTOR3, Vector3.class); 
    }
    
    public static Vector4 getVector4(int id) {
        return getUniformValue(id, UniformType.VECTOR4, Vector4.class); 
    }
    
    public static Matrix3 getMatrix3(int id) {
        return getUniformValue(id, UniformType.MATRIX3, Matrix3.class); 
    }
    
    public static Matrix4 getMatrix4(int id) {
        return getUniformValue(id, UniformType.MATRIX4, Matrix4.class); 
    }
    
    public static Texture getTexture(int id) {
        return getUniformValue(id, UniformType.TEXTURE, Texture.class); 
    }
    
    private String vs; 
    private String fs; 
    
    public Shader(String vs, String fs) {
        this.vs = vs; 
        this.fs = fs; 
    }
    
    public abstract boolean isValid(); 
    
    public abstract String getErrorMessage(); 
    
    public String getBaseVertexSource() {
        return vs; 
    }
    
    public String getBaseFragmentSource() {
        return fs; 
    }
    
}
