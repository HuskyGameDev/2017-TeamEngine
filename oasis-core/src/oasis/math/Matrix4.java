package oasis.math;

/**
 * 4x4 float matrix 
 * 
 * @author Nicholas Hamilton 
 *
 */
// TODO inverse and transpose 
public class Matrix4 {

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
    
    public Matrix4(float m00, float m10, float m20, float m30, 
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
    
    public Matrix4(Matrix4 r) {
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
    
    public Matrix4() {}
    
    public static Matrix4 zero() {
        return new Matrix4(); 
    }
    
    public static Matrix4 identity() {
        Matrix4 m = new Matrix4(); 
        m.m00 = 1f; 
        m.m11 = 1f; 
        m.m22 = 1f; 
        m.m33 = 1f; 
        return m; 
    }
    
    public static Matrix4 scale(Vector3 scale) {
        Matrix4 m = Matrix4.zero();  
        m.m00 = scale.x; 
        m.m11 = scale.y; 
        m.m22 = scale.z; 
        m.m33 = 1f; 
        return m; 
    }
    
    public static Matrix4 translation(Vector3 translate) {
        Matrix4 m = Matrix4.identity(); 
        m.m03 = translate.x; 
        m.m13 = translate.y; 
        m.m23 = translate.z; 
        return m; 
    }
    
    public static Matrix4 rotationX(float theta) {
        Matrix4 m = Matrix4.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m11 = c; 
        m.m12 = -s; 
        m.m21 = s; 
        m.m22 = c; 
        return m; 
    }
    
    public static Matrix4 rotationY(float theta) {
        Matrix4 m = Matrix4.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m00 = c; 
        m.m02 = s; 
        m.m20 = -s; 
        m.m22 = c; 
        return m; 
    }
    
    public static Matrix4 rotationZ(float theta) {
        Matrix4 m = Matrix4.identity(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        m.m00 = c; 
        m.m01 = -s; 
        m.m10 = s; 
        m.m11 = c; 
        return m; 
    }
    
    public static Matrix4 perspective(float fov, float ratio, float near, float far) {
        Matrix4 m = Matrix4.zero(); 
        fov = 1f / Mathf.tan(fov * 0.5f); 
        m.m00 = fov / ratio; 
        m.m11 = fov; 
        m.m22 = (far + near) / (near - far); 
        m.m23 = 2f * far * near / (near - far); 
        m.m32 = -1f; 
        return m; 
    }
    
    public static Matrix4 orthographic(Vector3 min, Vector3 max) {
        Matrix4 m = Matrix4.identity(); 
        m.m00 = 2f / (max.x - min.x); 
        m.m11 = 2f / (max.y - min.y); 
        m.m22 = 2f / (max.z - min.z); 
        m.m03 = -(max.x + min.x) / (max.x - min.x); 
        m.m13 = -(max.y + min.y) / (max.y - min.y); 
        m.m23 = -(max.z + min.z) / (max.z - min.z); 
        return m; 
    }
    
    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        Matrix4 m = Matrix4.zero(); 
        Vector3 f = target.subtract(eye).normalizeSelf(); 
        Vector3 s = f.cross(up).normalizeSelf(); 
        Vector3 y = s.cross(f).normalizeSelf(); 
        m.m00 = s.x; m.m01 = s.y; m.m02 = s.z; 
        m.m10 = y.x; m.m11 = y.y; m.m12 = y.z; 
        m.m20 = -f.x; m.m21 = -f.y; m.m22 = -f.z; 
        m.m33 = 1f;  
        m.multiplySelf(Matrix4.translation(eye.multiply(-1))); 
        return m; 
    }
    
    public static Matrix4 rotation(Quaternion r) {
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
    
    public Matrix4 set(float[] in) {
        m00 = in[0];  
        m10 = in[1];  
        m20 = in[2];  
        m30 = in[3];  
        m01 = in[4];  
        m11 = in[5];  
        m21 = in[6];  
        m31 = in[7];  
        m02 = in[8];  
        m12 = in[9];  
        m22 = in[10];  
        m32 = in[11];  
        m03 = in[12];  
        m13 = in[13];  
        m23 = in[14];  
        m33 = in[15];  

        return this; 
    }
    
    public Matrix4 setM00(float m00) { this.m00 = m00; return this; }
    public Matrix4 setM10(float m10) { this.m10 = m10; return this; }
    public Matrix4 setM20(float m20) { this.m20 = m20; return this; }
    public Matrix4 setM30(float m30) { this.m30 = m30; return this; }
    public Matrix4 setM01(float m01) { this.m01 = m01; return this; }
    public Matrix4 setM11(float m11) { this.m11 = m11; return this; }
    public Matrix4 setM21(float m21) { this.m21 = m21; return this; }
    public Matrix4 setM31(float m31) { this.m31 = m31; return this; }
    public Matrix4 setM02(float m02) { this.m02 = m02; return this; }
    public Matrix4 setM12(float m12) { this.m12 = m12; return this; }
    public Matrix4 setM22(float m22) { this.m22 = m22; return this; }
    public Matrix4 setM32(float m32) { this.m32 = m32; return this; }
    public Matrix4 setM03(float m03) { this.m03 = m03; return this; }
    public Matrix4 setM13(float m13) { this.m13 = m13; return this; }
    public Matrix4 setM23(float m23) { this.m23 = m23; return this; }
    public Matrix4 setM33(float m33) { this.m33 = m33; return this; }

    public Matrix4 set(float m00, float m10, float m20, float m30, 
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
    
    public Matrix4 set(Matrix4 r) {
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
    
    public Matrix3 getNormalMatrix() {
        return new Matrix3(transposeInverse()); 
    }
    
    public Matrix4 add(Matrix4 r) {
        Matrix4 out = new Matrix4(this); 
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
    
    public Matrix4 subtract(Matrix4 r) {
        Matrix4 out = new Matrix4(this); 
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
    
    public Matrix4 multiply(float s) {
        Matrix4 out = new Matrix4(this); 
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
    
    public Matrix4 multiply(Matrix4 r) {
        Matrix4 out = new Matrix4(); 
        
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
    
    public Vector4 multiply(Vector4 r) {
        Vector4 out = new Vector4(); 
        out.x = m00 * r.x + m01 * r.y + m02 * r.z + m03 * r.w; 
        out.y = m10 * r.x + m11 * r.y + m12 * r.z + m13 * r.w; 
        out.z = m20 * r.x + m21 * r.y + m22 * r.z + m23 * r.w; 
        out.w = m30 * r.x + m31 * r.y + m32 * r.z + m33 * r.w;
        return out; 
    }
    
    public Vector3 multiply(Vector3 r, float w) {
        Vector3 out = new Vector3(); 
        out.x = m00 * r.x + m01 * r.y + m02 * r.z + m03 * w; 
        out.y = m10 * r.x + m11 * r.y + m12 * r.z + m13 * w; 
        out.z = m20 * r.x + m21 * r.y + m22 * r.z + m23 * w; 
        float w_ = m30 * r.x + m31 * r.y + m32 * r.z + m33 * w;
        return out.multiply(1f / w_); 
    }
    
    public Matrix4 addSelf(Matrix4 r) {
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
    
    public Matrix4 subtractSelf(Matrix4 r) {
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
    
    public Matrix4 multiplySelf(float s) {
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
    
    public Matrix4 multiplySelf(Matrix4 r) {
        Matrix4 out = new Matrix4(); 
        
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
    
    public Matrix4 transposeSelf() {
        return set(transpose()); 
    }
    
    public Matrix4 transpose() {
        Matrix4 out = new Matrix4();
        
        out.m00 = m00; 
        out.m01 = m10; 
        out.m02 = m20; 
        out.m03 = m30; 
        out.m10 = m01; 
        out.m11 = m11; 
        out.m12 = m21; 
        out.m13 = m31;
        out.m20 = m02; 
        out.m21 = m12; 
        out.m22 = m22; 
        out.m23 = m32; 
        out.m30 = m03; 
        out.m31 = m13; 
        out.m32 = m23; 
        out.m33 = m33; 
        
        return out; 
    }
    
    public Matrix4 transposeInverseSelf() {
        return transposeSelf().inverseSelf(); 
    }
    
    /**
     * Transpose-inverse matrix4
     * Modified from https://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix
     * 
     * @return
     */
    public Matrix4 transposeInverse() {
        return transpose().inverseSelf(); 
    }
    
    public Matrix4 inverseSelf() {
        return set(inverse()); 
    }
    
    /**
     * Inverse matrx4
     * modified from https://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix 
     * 
     * @return
     */
    public Matrix4 inverse() {
        Matrix4 out = new Matrix4(); 
        
        double[] inv = new double[16]; 
        double det;
        int i;
        float[] m = this.get(new float[16]); 

        inv[0] = m[5]  * m[10] * m[15] - 
                m[5]  * m[11] * m[14] - 
                m[9]  * m[6]  * m[15] + 
                m[9]  * m[7]  * m[14] +
                m[13] * m[6]  * m[11] - 
                m[13] * m[7]  * m[10];

       inv[4] = -m[4]  * m[10] * m[15] + 
                 m[4]  * m[11] * m[14] + 
                 m[8]  * m[6]  * m[15] - 
                 m[8]  * m[7]  * m[14] - 
                 m[12] * m[6]  * m[11] + 
                 m[12] * m[7]  * m[10];

       inv[8] = m[4]  * m[9] * m[15] - 
                m[4]  * m[11] * m[13] - 
                m[8]  * m[5] * m[15] + 
                m[8]  * m[7] * m[13] + 
                m[12] * m[5] * m[11] - 
                m[12] * m[7] * m[9];

       inv[12] = -m[4]  * m[9] * m[14] + 
                  m[4]  * m[10] * m[13] +
                  m[8]  * m[5] * m[14] - 
                  m[8]  * m[6] * m[13] - 
                  m[12] * m[5] * m[10] + 
                  m[12] * m[6] * m[9];

       inv[1] = -m[1]  * m[10] * m[15] + 
                 m[1]  * m[11] * m[14] + 
                 m[9]  * m[2] * m[15] - 
                 m[9]  * m[3] * m[14] - 
                 m[13] * m[2] * m[11] + 
                 m[13] * m[3] * m[10];

       inv[5] = m[0]  * m[10] * m[15] - 
                m[0]  * m[11] * m[14] - 
                m[8]  * m[2] * m[15] + 
                m[8]  * m[3] * m[14] + 
                m[12] * m[2] * m[11] - 
                m[12] * m[3] * m[10];

       inv[9] = -m[0]  * m[9] * m[15] + 
                 m[0]  * m[11] * m[13] + 
                 m[8]  * m[1] * m[15] - 
                 m[8]  * m[3] * m[13] - 
                 m[12] * m[1] * m[11] + 
                 m[12] * m[3] * m[9];

       inv[13] = m[0]  * m[9] * m[14] - 
                 m[0]  * m[10] * m[13] - 
                 m[8]  * m[1] * m[14] + 
                 m[8]  * m[2] * m[13] + 
                 m[12] * m[1] * m[10] - 
                 m[12] * m[2] * m[9];

       inv[2] = m[1]  * m[6] * m[15] - 
                m[1]  * m[7] * m[14] - 
                m[5]  * m[2] * m[15] + 
                m[5]  * m[3] * m[14] + 
                m[13] * m[2] * m[7] - 
                m[13] * m[3] * m[6];

       inv[6] = -m[0]  * m[6] * m[15] + 
                 m[0]  * m[7] * m[14] + 
                 m[4]  * m[2] * m[15] - 
                 m[4]  * m[3] * m[14] - 
                 m[12] * m[2] * m[7] + 
                 m[12] * m[3] * m[6];

       inv[10] = m[0]  * m[5] * m[15] - 
                 m[0]  * m[7] * m[13] - 
                 m[4]  * m[1] * m[15] + 
                 m[4]  * m[3] * m[13] + 
                 m[12] * m[1] * m[7] - 
                 m[12] * m[3] * m[5];

       inv[14] = -m[0]  * m[5] * m[14] + 
                  m[0]  * m[6] * m[13] + 
                  m[4]  * m[1] * m[14] - 
                  m[4]  * m[2] * m[13] - 
                  m[12] * m[1] * m[6] + 
                  m[12] * m[2] * m[5];

       inv[3] = -m[1] * m[6] * m[11] + 
                 m[1] * m[7] * m[10] + 
                 m[5] * m[2] * m[11] - 
                 m[5] * m[3] * m[10] - 
                 m[9] * m[2] * m[7] + 
                 m[9] * m[3] * m[6];

       inv[7] = m[0] * m[6] * m[11] - 
                m[0] * m[7] * m[10] - 
                m[4] * m[2] * m[11] + 
                m[4] * m[3] * m[10] + 
                m[8] * m[2] * m[7] - 
                m[8] * m[3] * m[6];

       inv[11] = -m[0] * m[5] * m[11] + 
                  m[0] * m[7] * m[9] + 
                  m[4] * m[1] * m[11] - 
                  m[4] * m[3] * m[9] - 
                  m[8] * m[1] * m[7] + 
                  m[8] * m[3] * m[5];

       inv[15] = m[0] * m[5] * m[10] - 
                 m[0] * m[6] * m[9] - 
                 m[4] * m[1] * m[10] + 
                 m[4] * m[2] * m[9] + 
                 m[8] * m[1] * m[6] - 
                 m[8] * m[2] * m[5];

        det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

        if (det == 0) // set to zeros 
           return set(new float[16]);

        det = 1.0 / det;

        for (i = 0; i < 16; i++)
           m[i] = (float) (inv[i] * det);

        return out.set(m); 
    }
    
    public String toString() {
        return String.format("[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]\n[%1.2f, %1.2f, %1.2f, %1.2f]", 
                m00, m01, m02, m03, 
                m10, m11, m12, m13, 
                m20, m21, m22, m23, 
                m30, m31, m32, m33); 
    }
    
}
