package oasis.graphics;

import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class UniformValue {

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