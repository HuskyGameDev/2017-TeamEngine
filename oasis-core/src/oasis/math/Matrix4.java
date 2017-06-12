package oasis.math;

import oasis.util.QuickHash;

public class Matrix4 {

    public static final int M00 = 0;
    public static final int M10 = 1;
    public static final int M20 = 2;
    public static final int M30 = 3;
    public static final int M01 = 4;
    public static final int M11 = 5;
    public static final int M21 = 6;
    public static final int M31 = 7;
    public static final int M02 = 8;
    public static final int M12 = 9;
    public static final int M22 = 10;
    public static final int M32 = 11;
    public static final int M03 = 12;
    public static final int M13 = 13;
    public static final int M23 = 14;
    public static final int M33 = 15;
    
    private float[] m;
    
    public Matrix4() {
        m = new float[16];
    }
    
    public Matrix4(float[] m) {
        this();
        for (int i = 0; i < 16; i++) {
            this.m[i] = m[i];
        }
    }
    
    public float[] get(float[] out) {
        for (int i = 0; i < 16; i++) {
            out[i] = m[i];
        }
        return out;
    }
    
    public float get(int row, int col) {
        return m[row + col * 4];
    }
    
    public Matrix4 set(int row, int col, float value) {
        m[row + col * 4] = value;
        return this;
    }
    
    public Matrix4 setZero() {
        for (int i = 0; i < 16; i++) {
            m[i] = 0.0f;
        }
        return this;
    }
    
    public Matrix4 setIdentity() {
        for (int i = 0; i < 16; i++) {
            m[i] = 0.0f;
        }
        m[M00] = 1.0f;
        m[M11] = 1.0f;
        m[M22] = 1.0f;
        m[M33] = 1.0f;
        return this;
    }
    
    public Matrix4 setScale(Vector3 scale) {
        setZero();
        m[M00] = scale.x;
        m[M11] = scale.y;
        m[M22] = scale.z;
        m[M33] = 1.0f;
        return this;
    }
    
    public Matrix4 setTranslation(Vector3 translate) {
        setIdentity();
        m[M03] = translate.x;
        m[M13] = translate.y;
        m[M23] = translate.z;
        return this;
    }
    
    public Matrix4 setRotationX(float angle) {
        setIdentity();
        float c = FastMath.cos(angle);
        float s = FastMath.sin(angle);
        m[M11] = c;
        m[M12] = -s;
        m[M21] = s;
        m[M22] = c;
        return this;
    }
    
    public Matrix4 setRotationY(float angle) {
        setIdentity();
        float c = FastMath.cos(angle);
        float s = FastMath.sin(angle);
        m[M00] = c;
        m[M02] = s;
        m[M20] = -s;
        m[M22] = c;
        return this;
    }
    
    public Matrix4 setRotationZ(float angle) {
        setIdentity();
        float c = FastMath.cos(angle);
        float s = FastMath.sin(angle);
        m[M00] = c;
        m[M01] = -s;
        m[M10] = s;
        m[M11] = c;
        return this;
    }
    
    public Matrix4 setPerspective(float fov, float ratio, float near, float far) {
        setZero();
        fov = 1.0f / FastMath.tan(fov * 0.5f);
        m[M00] = fov / ratio;
        m[M11] = fov;
        m[M22] = (far + near) / (near - far);
        m[M23] = 2 * far * near / (near - far);
        m[M32] = -1;
        return this;
    }
    
    public Matrix4 multiplySelf(Matrix4 r) {
        m = mul(r);
        return this;
    }
    
    public Matrix4 multiply(Matrix4 r) {
        return new Matrix4(mul(r));
    }
    
    public Matrix4 multiply(Matrix4 r, Matrix4 out) {
        out.m = mul(r);
        return out;
    }
    
    @Override
    public int hashCode() {
        return QuickHash.compute(m);
    }
    
    @Override
    public boolean equals(Object r) {
        if (this == r) return true;
        if (r == null) return false;
        if (!(r instanceof Matrix4)) return false;
        
        float[] rm = ((Matrix4) r).m;
        
        for (int i = 0; i < 16; i++) {
            if (!FastMath.equals(m[i], rm[i])) return false;
        }
        
        return true;
    }
    
    public boolean strictEquals(Matrix4 r) {
        float[] rm = ((Matrix4) r).m;
        
        for (int i = 0; i < 16; i++) {
            if (m[i] != rm[i]) return false;
        }
        
        return true;
    }
    
    private float[] mul(Matrix4 r) {
        float[] out = new float[16];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                out[row + col * 4] = 
                        m[row + 0 * 4] * r.m[0 + col * 4] +
                        m[row + 1 * 4] * r.m[1 + col * 4] +
                        m[row + 2 * 4] * r.m[2 + col * 4] +
                        m[row + 3 * 4] * r.m[3 + col * 4];
            }
        }
        return out;
    }
    
}
