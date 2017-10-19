package oasis.math;

/**
 * 4x4 float matrix 
 * 
 * @author Nicholas Hamilton 
 *
 */
// TODO inverse and transpose 
public class Matrix4f {

    public float m00; 
    public float m10; 
    public float m20; 
    public float m30; 
    public float m01; 
    public float m11; 
    public float m21; 
    public float m31; 
    public float m02; 
    public float m12; 
    public float m22; 
    public float m32; 
    public float m03; 
    public float m13; 
    public float m23; 
    public float m33; 
    
    public Matrix4f(float m00, float m10, float m20, float m30, 
                    float m01, float m11, float m21, float m31, 
                    float m02, float m12, float m22, float m32, 
                    float m03, float m13, float m23, float m33) {
        this.m00 = m00; 
        this.m10 = m10; 
        this.m20 = m20; 
        this.m30 = m30; 
        this.m01 = m01; 
        this.m11 = m11; 
        this.m21 = m21; 
        this.m31 = m31; 
        this.m02 = m02; 
        this.m12 = m12; 
        this.m22 = m22; 
        this.m32 = m32; 
        this.m03 = m03; 
        this.m13 = m13; 
        this.m23 = m23; 
        this.m33 = m33; 
    }
    
    public Matrix4f(Matrix4f r) {
        this.m00 = r.m00; 
        this.m10 = r.m10; 
        this.m20 = r.m20; 
        this.m30 = r.m30; 
        this.m01 = r.m01; 
        this.m11 = r.m11; 
        this.m21 = r.m21; 
        this.m31 = r.m31; 
        this.m02 = r.m02; 
        this.m12 = r.m12; 
        this.m22 = r.m22; 
        this.m32 = r.m32; 
        this.m03 = r.m03; 
        this.m13 = r.m13; 
        this.m23 = r.m23; 
        this.m33 = r.m33; 
    }
    
    public Matrix4f() {}
    
    public static Matrix4f zero() {
        return new Matrix4f(); 
    }
    
    public static Matrix4f identity() {
        Matrix4f m = new Matrix4f(); 
        m.m00 = 1f; 
        m.m11 = 1f; 
        m.m22 = 1f; 
        m.m33 = 1f; 
        return m; 
    }
    
    public static Matrix4f scale(Vector3f scale) {
        Matrix4f m = Matrix4f.zero();  
        m.m00 = scale.x; 
        m.m11 = scale.y; 
        m.m22 = scale.z; 
        m.m33 = 1f; 
        return m; 
    }
    
    public static Matrix4f translation(Vector3f translate) {
        Matrix4f m = Matrix4f.identity(); 
        m.m03 = translate.x; 
        m.m13 = translate.y; 
        m.m23 = translate.z; 
        return m; 
    }
    
    public static Matrix4f rotationX(float theta) {
        Matrix4f m = Matrix4f.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m11 = c; 
        m.m12 = -s; 
        m.m21 = s; 
        m.m22 = c; 
        return m; 
    }
    
    public static Matrix4f rotationY(float theta) {
        Matrix4f m = Matrix4f.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m00 = c; 
        m.m02 = s; 
        m.m20 = -s; 
        m.m22 = c; 
        return m; 
    }
    
    public static Matrix4f rotationZ(float theta) {
        Matrix4f m = Matrix4f.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m00 = c; 
        m.m01 = -s; 
        m.m10 = s; 
        m.m11 = c; 
        return m; 
    }
    
    public static Matrix4f perspective(float fov, float ratio, float near, float far) {
        Matrix4f m = Matrix4f.zero(); 
        fov = 1f / Mathf.tan(fov * 0.5f); 
        m.m00 = fov / ratio; 
        m.m11 = fov; 
        m.m22 = (far + near) / (near - far); 
        m.m23 = 2f * far * near / (near - far); 
        m.m32 = -1f; 
        return m; 
    }
    
    public static Matrix4f orthographic(Vector3f min, Vector3f max) {
        Matrix4f m = Matrix4f.identity(); 
        m.m00 = 2f / (max.x - min.x); 
        m.m11 = 2f / (max.y - min.y); 
        m.m22 = 2f / (max.z - min.z); 
        m.m03 = -(max.x + min.x) / (max.x - min.x); 
        m.m13 = -(max.y + min.y) / (max.y - min.y); 
        m.m23 = -(max.z + min.z) / (max.z - min.z); 
        return m; 
    }
    
    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Matrix4f m = Matrix4f.zero(); 
        Vector3f f = target.subtract(eye).normalizeSelf(); 
        Vector3f s = f.cross(up).normalizeSelf(); 
        Vector3f y = s.cross(f).normalizeSelf(); 
        m.m00 = s.x; m.m01 = s.y; m.m02 = s.z; 
        m.m10 = y.x; m.m11 = y.y; m.m12 = y.z; 
        m.m20 = -f.x; m.m21 = -f.y; m.m22 = -f.z; 
        m.m33 = 1f;  
        m.multiplySelf(Matrix4f.translation(eye.multiply(-1))); 
        return m; 
    }
    
    public static Matrix4f rotation(Quaternionf r) {
        return r.toMatrix4f(); 
    }
    
    public float getM00() { return m00; }
    public float getM10() { return m10; }
    public float getM20() { return m20; }
    public float getM30() { return m30; }
    public float getM01() { return m01; }
    public float getM11() { return m11; }
    public float getM21() { return m21; }
    public float getM31() { return m31; }
    public float getM02() { return m02; }
    public float getM12() { return m12; }
    public float getM22() { return m22; }
    public float getM32() { return m32; }
    public float getM03() { return m03; }
    public float getM13() { return m13; }
    public float getM23() { return m23; }
    public float getM33() { return m33; }

    public float[] get(float[] out) {
        out[0] = m00; 
        out[1] = m10; 
        out[2] = m20; 
        out[3] = m30; 
        out[4] = m01; 
        out[5] = m11; 
        out[6] = m21; 
        out[7] = m31; 
        out[8] = m02; 
        out[9] = m12; 
        out[10] = m22; 
        out[11] = m32; 
        out[12] = m03; 
        out[13] = m13; 
        out[14] = m23; 
        out[15] = m33; 
        return out; 
    }
    
    public Matrix4f setM00(float m00) { this.m00 = m00; return this; }
    public Matrix4f setM10(float m10) { this.m10 = m10; return this; }
    public Matrix4f setM20(float m20) { this.m20 = m20; return this; }
    public Matrix4f setM30(float m30) { this.m30 = m30; return this; }
    public Matrix4f setM01(float m01) { this.m01 = m01; return this; }
    public Matrix4f setM11(float m11) { this.m11 = m11; return this; }
    public Matrix4f setM21(float m21) { this.m21 = m21; return this; }
    public Matrix4f setM31(float m31) { this.m31 = m31; return this; }
    public Matrix4f setM02(float m02) { this.m02 = m02; return this; }
    public Matrix4f setM12(float m12) { this.m12 = m12; return this; }
    public Matrix4f setM22(float m22) { this.m22 = m22; return this; }
    public Matrix4f setM32(float m32) { this.m32 = m32; return this; }
    public Matrix4f setM03(float m03) { this.m03 = m03; return this; }
    public Matrix4f setM13(float m13) { this.m13 = m13; return this; }
    public Matrix4f setM23(float m23) { this.m23 = m23; return this; }
    public Matrix4f setM33(float m33) { this.m33 = m33; return this; }

    public Matrix4f set(float m00, float m10, float m20, float m30, 
                        float m01, float m11, float m21, float m31, 
                        float m02, float m12, float m22, float m32, 
                        float m03, float m13, float m23, float m33) {
        this.m00 = m00; 
        this.m10 = m10; 
        this.m20 = m20; 
        this.m30 = m30; 
        this.m01 = m01; 
        this.m11 = m11; 
        this.m21 = m21; 
        this.m31 = m31; 
        this.m02 = m02; 
        this.m12 = m12; 
        this.m22 = m22; 
        this.m32 = m32; 
        this.m03 = m03; 
        this.m13 = m13; 
        this.m23 = m23; 
        this.m33 = m33; 
        return this; 
    }
    
    public Matrix4f set(Matrix4f r) {
        this.m00 = r.m00; 
        this.m10 = r.m10; 
        this.m20 = r.m20; 
        this.m30 = r.m30; 
        this.m01 = r.m01; 
        this.m11 = r.m11; 
        this.m21 = r.m21; 
        this.m31 = r.m31; 
        this.m02 = r.m02; 
        this.m12 = r.m12; 
        this.m22 = r.m22; 
        this.m32 = r.m32; 
        this.m03 = r.m03; 
        this.m13 = r.m13; 
        this.m23 = r.m23; 
        this.m33 = r.m33; 
        return this; 
    }
    
    public Matrix4f add(Matrix4f r) {
        Matrix4f out = new Matrix4f(this); 
        out.m00 += r.m00; 
        out.m10 += r.m10; 
        out.m20 += r.m20; 
        out.m30 += r.m30; 
        out.m01 += r.m01; 
        out.m11 += r.m11; 
        out.m21 += r.m21; 
        out.m31 += r.m31; 
        out.m02 += r.m02; 
        out.m12 += r.m12; 
        out.m22 += r.m22; 
        out.m32 += r.m32; 
        out.m03 += r.m03; 
        out.m13 += r.m13; 
        out.m23 += r.m23; 
        out.m33 += r.m33; 
        return out; 
    }
    
    public Matrix4f subtract(Matrix4f r) {
        Matrix4f out = new Matrix4f(this); 
        out.m00 -= r.m00; 
        out.m10 -= r.m10; 
        out.m20 -= r.m20; 
        out.m30 -= r.m30; 
        out.m01 -= r.m01; 
        out.m11 -= r.m11; 
        out.m21 -= r.m21; 
        out.m31 -= r.m31; 
        out.m02 -= r.m02; 
        out.m12 -= r.m12; 
        out.m22 -= r.m22; 
        out.m32 -= r.m32; 
        out.m03 -= r.m03; 
        out.m13 -= r.m13; 
        out.m23 -= r.m23; 
        out.m33 -= r.m33; 
        return out; 
    }
    
    public Matrix4f multiply(float s) {
        Matrix4f out = new Matrix4f(this); 
        out.m00 *= s; 
        out.m10 *= s; 
        out.m20 *= s; 
        out.m30 *= s; 
        out.m01 *= s; 
        out.m11 *= s; 
        out.m21 *= s; 
        out.m31 *= s; 
        out.m02 *= s; 
        out.m12 *= s; 
        out.m22 *= s; 
        out.m32 *= s; 
        out.m03 *= s; 
        out.m13 *= s; 
        out.m23 *= s; 
        out.m33 *= s; 
        return out; 
    }
    
    public Matrix4f multiply(Matrix4f r) {
        Matrix4f out = new Matrix4f(); 
        
        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20 + m03 * r.m30; 
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20 + m13 * r.m30; 
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20 + m23 * r.m30; 
        out.m30 = m30 * r.m00 + m31 * r.m10 + m32 * r.m20 + m33 * r.m30; 
        
        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21 + m03 * r.m31; 
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21 + m13 * r.m31; 
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21 + m23 * r.m31; 
        out.m31 = m30 * r.m01 + m31 * r.m11 + m32 * r.m21 + m33 * r.m31; 
        
        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22 + m03 * r.m32; 
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22 + m13 * r.m32; 
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22 + m23 * r.m32; 
        out.m32 = m30 * r.m02 + m31 * r.m12 + m32 * r.m22 + m33 * r.m32; 
        
        out.m03 = m00 * r.m03 + m01 * r.m13 + m02 * r.m23 + m03 * r.m33; 
        out.m13 = m10 * r.m03 + m11 * r.m13 + m12 * r.m23 + m13 * r.m33; 
        out.m23 = m20 * r.m03 + m21 * r.m13 + m22 * r.m23 + m23 * r.m33; 
        out.m33 = m30 * r.m03 + m31 * r.m13 + m32 * r.m23 + m33 * r.m33; 
        
        return out; 
    }
    
    public Vector4f multiply(Vector4f r) {
        Vector4f out = new Vector4f(); 
        out.x = m00 * r.x + m01 * r.y + m02 * r.z + m03 * r.w; 
        out.y = m10 * r.x + m11 * r.y + m12 * r.z + m13 * r.w; 
        out.z = m20 * r.x + m21 * r.y + m22 * r.z + m23 * r.w; 
        out.w = m30 * r.x + m31 * r.y + m32 * r.z + m33 * r.w;
        return out; 
    }
    
    public Vector3f multiply(Vector3f r, float w) {
        Vector3f out = new Vector3f(); 
        out.x = m00 * r.x + m01 * r.y + m02 * r.z + m03 * w; 
        out.y = m10 * r.x + m11 * r.y + m12 * r.z + m13 * w; 
        out.z = m20 * r.x + m21 * r.y + m22 * r.z + m23 * w; 
        float w_ = m30 * r.x + m31 * r.y + m32 * r.z + m33 * w;
        return out.multiply(1f / w_); 
    }
    
    public Matrix4f addSelf(Matrix4f r) {
        this.m00 += r.m00; 
        this.m10 += r.m10; 
        this.m20 += r.m20; 
        this.m30 += r.m30; 
        this.m01 += r.m01; 
        this.m11 += r.m11; 
        this.m21 += r.m21; 
        this.m31 += r.m31; 
        this.m02 += r.m02; 
        this.m12 += r.m12; 
        this.m22 += r.m22; 
        this.m32 += r.m32; 
        this.m03 += r.m03; 
        this.m13 += r.m13; 
        this.m23 += r.m23; 
        this.m33 += r.m33; 
        return this; 
    }
    
    public Matrix4f subtractSelf(Matrix4f r) {
        this.m00 -= r.m00; 
        this.m10 -= r.m10; 
        this.m20 -= r.m20; 
        this.m30 -= r.m30; 
        this.m01 -= r.m01; 
        this.m11 -= r.m11; 
        this.m21 -= r.m21; 
        this.m31 -= r.m31; 
        this.m02 -= r.m02; 
        this.m12 -= r.m12; 
        this.m22 -= r.m22; 
        this.m32 -= r.m32; 
        this.m03 -= r.m03; 
        this.m13 -= r.m13; 
        this.m23 -= r.m23; 
        this.m33 -= r.m33; 
        return this; 
    }
    
    public Matrix4f multiplySelf(float s) {
        this.m00 *= s; 
        this.m10 *= s; 
        this.m20 *= s; 
        this.m30 *= s; 
        this.m01 *= s; 
        this.m11 *= s; 
        this.m21 *= s; 
        this.m31 *= s; 
        this.m02 *= s; 
        this.m12 *= s; 
        this.m22 *= s; 
        this.m32 *= s; 
        this.m03 *= s; 
        this.m13 *= s; 
        this.m23 *= s; 
        this.m33 *= s; 
        return this; 
    }
    
    public Matrix4f multiplySelf(Matrix4f r) {
        Matrix4f out = new Matrix4f(); 
        
        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20 + m03 * r.m30; 
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20 + m13 * r.m30; 
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20 + m23 * r.m30; 
        out.m30 = m30 * r.m00 + m31 * r.m10 + m32 * r.m20 + m33 * r.m30; 
        
        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21 + m03 * r.m31; 
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21 + m13 * r.m31; 
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21 + m23 * r.m31; 
        out.m31 = m30 * r.m01 + m31 * r.m11 + m32 * r.m21 + m33 * r.m31; 
        
        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22 + m03 * r.m32; 
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22 + m13 * r.m32; 
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22 + m23 * r.m32; 
        out.m32 = m30 * r.m02 + m31 * r.m12 + m32 * r.m22 + m33 * r.m32; 
        
        out.m03 = m00 * r.m03 + m01 * r.m13 + m02 * r.m23 + m03 * r.m33; 
        out.m13 = m10 * r.m03 + m11 * r.m13 + m12 * r.m23 + m13 * r.m33; 
        out.m23 = m20 * r.m03 + m21 * r.m13 + m22 * r.m23 + m23 * r.m33; 
        out.m33 = m30 * r.m03 + m31 * r.m13 + m32 * r.m23 + m33 * r.m33; 
        
        return set(out); 
    }
    
    public String toString() {
        return String.format("[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]", 
                m00, m01, m02, m03, 
                m10, m11, m12, m13, 
                m20, m21, m22, m23, 
                m30, m31, m32, m33); 
    }
    
}
