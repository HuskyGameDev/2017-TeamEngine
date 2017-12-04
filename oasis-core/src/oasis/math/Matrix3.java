package oasis.math;

/**
 * 3x3 float matrix 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Matrix3 {

    public float m00; 
    public float m10; 
    public float m20; 
    public float m01; 
    public float m11; 
    public float m21; 
    public float m02; 
    public float m12; 
    public float m22; 
    
    public Matrix3(float m00, float m10, float m20,
                    float m01, float m11, float m21, 
                    float m02, float m12, float m22) {
        this.m00 = m00; 
        this.m10 = m10; 
        this.m20 = m20; 
        this.m01 = m01; 
        this.m11 = m11; 
        this.m21 = m21; 
        this.m02 = m02; 
        this.m12 = m12; 
        this.m22 = m22; 
    }
    
    public Matrix3(Matrix3 r) {
        this.m00 = r.m00; 
        this.m10 = r.m10; 
        this.m20 = r.m20; 
        this.m01 = r.m01; 
        this.m11 = r.m11; 
        this.m21 = r.m21; 
        this.m02 = r.m02; 
        this.m12 = r.m12; 
        this.m22 = r.m22; 
    }
    
    public Matrix3(Matrix4 r) {
        this.m00 = r.m00; 
        this.m10 = r.m10; 
        this.m20 = r.m20; 
        this.m01 = r.m01; 
        this.m11 = r.m11; 
        this.m21 = r.m21; 
        this.m02 = r.m02; 
        this.m12 = r.m12; 
        this.m22 = r.m22; 
    }
    
    public Matrix3() {}
    
    public static Matrix3 zero() {
        return new Matrix3(); 
    }
    
    public static Matrix3 identity() {
        Matrix3 m = new Matrix3(); 
        m.m00 = 1f; 
        m.m11 = 1f; 
        m.m22 = 1f; 
        return m; 
    }
    
    public static Matrix3 scale(Vector2 scale) {
        Matrix3 m = Matrix3.zero();  
        m.m00 = scale.x; 
        m.m11 = scale.y; 
        m.m22 = 1f; 
        return m; 
    }
    
    public static Matrix3 translation(Vector2 translate) {
        Matrix3 m = Matrix3.identity(); 
        m.m02 = translate.x; 
        m.m12 = translate.y; 
        return m; 
    }
    
    public static Matrix3 rotation(float theta) {
        Matrix3 m = Matrix3.zero(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m00 = c; 
        m.m01 = -s; 
        m.m10 = s; 
        m.m11 = c; 
        m.m22 = 1f; 
        return m; 
    }
    
    public float getM00() { return m00; }
    public float getM10() { return m10; }
    public float getM20() { return m20; }
    public float getM01() { return m01; }
    public float getM11() { return m11; }
    public float getM21() { return m21; }
    public float getM02() { return m02; }
    public float getM12() { return m12; }
    public float getM22() { return m22; }

    public float[] get(float[] out) {
        out[0] = m00; 
        out[1] = m10; 
        out[2] = m20; 
        out[3] = m01; 
        out[4] = m11; 
        out[5] = m21; 
        out[6] = m02; 
        out[7] = m12; 
        out[8] = m22; 
        return out; 
    }
    
    public Matrix3 setM00(float m00) { this.m00 = m00; return this; }
    public Matrix3 setM10(float m10) { this.m10 = m10; return this; }
    public Matrix3 setM20(float m20) { this.m20 = m20; return this; }
    public Matrix3 setM01(float m01) { this.m01 = m01; return this; }
    public Matrix3 setM11(float m11) { this.m11 = m11; return this; }
    public Matrix3 setM21(float m21) { this.m21 = m21; return this; }
    public Matrix3 setM02(float m02) { this.m02 = m02; return this; }
    public Matrix3 setM12(float m12) { this.m12 = m12; return this; }
    public Matrix3 setM22(float m22) { this.m22 = m22; return this; }

    public Matrix3 set(float m00, float m10, float m20, 
                        float m01, float m11, float m21, 
                        float m02, float m12, float m22) {
        this.m00 = m00; 
        this.m10 = m10; 
        this.m20 = m20; 
        this.m01 = m01; 
        this.m11 = m11; 
        this.m21 = m21; 
        this.m02 = m02; 
        this.m12 = m12; 
        this.m22 = m22; 
        return this; 
    }
    
    public Matrix3 set(Matrix3 r) {
        this.m00 = r.m00; 
        this.m10 = r.m10; 
        this.m20 = r.m20; 
        this.m01 = r.m01; 
        this.m11 = r.m11; 
        this.m21 = r.m21; 
        this.m02 = r.m02; 
        this.m12 = r.m12; 
        this.m22 = r.m22; 
        return this; 
    }
    
    public Matrix3 add(Matrix3 r) {
        Matrix3 out = new Matrix3(this); 
        out.m00 += r.m00; 
        out.m10 += r.m10; 
        out.m20 += r.m20; 
        out.m01 += r.m01; 
        out.m11 += r.m11; 
        out.m21 += r.m21; 
        out.m02 += r.m02; 
        out.m12 += r.m12; 
        out.m22 += r.m22; 
        return out; 
    }
    
    public Matrix3 subtract(Matrix3 r) {
        Matrix3 out = new Matrix3(this); 
        out.m00 -= r.m00; 
        out.m10 -= r.m10; 
        out.m20 -= r.m20; 
        out.m01 -= r.m01; 
        out.m11 -= r.m11; 
        out.m21 -= r.m21; 
        out.m02 -= r.m02; 
        out.m12 -= r.m12; 
        out.m22 -= r.m22; 
        return out; 
    }
    
    public Matrix3 multiply(float s) {
        Matrix3 out = new Matrix3(this); 
        out.m00 *= s; 
        out.m10 *= s; 
        out.m20 *= s; 
        out.m01 *= s; 
        out.m11 *= s; 
        out.m21 *= s; 
        out.m02 *= s; 
        out.m12 *= s; 
        out.m22 *= s; 
        return out; 
    }
    
    public Matrix3 multiply(Matrix3 r) {
        Matrix3 out = new Matrix3(); 
        
        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20; 
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20; 
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20; 
        
        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21; 
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21; 
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21;; 
        
        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22; 
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22; 
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22; 
        
        return out; 
    }
    
    public Vector3 multiply(Vector3 r) {
        Vector3 out = new Vector3(); 
        out.x = m00 * r.x + m01 * r.y + m02 * r.z; 
        out.y = m10 * r.x + m11 * r.y + m12 * r.z; 
        out.z = m20 * r.x + m21 * r.y + m22 * r.z; 
        return out; 
    }
    
    public Vector2 multiply(Vector2 r, float z) {
        Vector2 out = new Vector2(); 
        out.x = m00 * r.x + m01 * r.y + m02 * z; 
        out.y = m10 * r.x + m11 * r.y + m12 * z; 
        float z_ = m20 * r.x + m21 * r.y + m22 * z;
        return out.multiply(1f / z_); 
    }
    
    public Matrix3 addSelf(Matrix3 r) {
        this.m00 += r.m00; 
        this.m10 += r.m10; 
        this.m20 += r.m20; 
        this.m01 += r.m01; 
        this.m11 += r.m11; 
        this.m21 += r.m21; 
        this.m02 += r.m02; 
        this.m12 += r.m12; 
        this.m22 += r.m22; 
        return this; 
    }
    
    public Matrix3 subtractSelf(Matrix3 r) {
        this.m00 -= r.m00; 
        this.m10 -= r.m10; 
        this.m20 -= r.m20; 
        this.m01 -= r.m01; 
        this.m11 -= r.m11; 
        this.m21 -= r.m21; 
        this.m02 -= r.m02; 
        this.m12 -= r.m12; 
        this.m22 -= r.m22; 
        return this; 
    }
    
    public Matrix3 multiplySelf(float s) {
        this.m00 *= s; 
        this.m10 *= s; 
        this.m20 *= s; 
        this.m01 *= s; 
        this.m11 *= s; 
        this.m21 *= s; 
        this.m02 *= s; 
        this.m12 *= s; 
        this.m22 *= s; 
        return this; 
    }
    
    public Matrix3 multiplySelf(Matrix3 r) {
        Matrix3 out = new Matrix3(); 
        
        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20; 
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20; 
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20; 
        
        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21; 
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21; 
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21; 
        
        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22; 
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22; 
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22; 
        
        return set(out); 
    }
    
    public String toString() {
        return String.format("[%1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f]", 
                m00, m01, m02, 
                m10, m11, m12, 
                m20, m21, m22); 
    }
    
}
