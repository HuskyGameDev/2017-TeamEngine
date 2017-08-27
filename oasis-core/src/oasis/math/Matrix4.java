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
    
    public final float[] m;
    
    /**
     * Creates an identity 4x4 matrix 
     */
    public Matrix4() {
        this (true);
    }
    
    /**
     * Creates a 4x4 matrix 
     * 
     * @param identity Should matrix be identity or all zeros 
     */
    public Matrix4(boolean identity) {
        m = new float[16];
        if (identity) {
            m[M00] = 1.0f;
            m[M11] = 1.0f;
            m[M22] = 1.0f;
            m[M33] = 1.0f;
        }
    }
    
    /**
     * Creates a matrix from a column-major array 
     * 
     * @param m Matrix values 
     */
    public Matrix4(float[] m) {
        this();
        for (int i = 0; i < 16; i++) {
            this.m[i] = m[i];
        }
    }
    
    /**
     * Creates a copy of a matrix 
     * @param r
     */
    public Matrix4(Matrix4 r) {
        m = r.m.clone();
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
    
    public Matrix4 set(float[] m) {
        for (int i = 0; i < 16; i++) {
            this.m[i] = m[i];
        }
        return this;
    }
    
    public static Matrix4 createZero() {
        return new Matrix4(false);
    }
    
    public static Matrix4 createIdentity() {
        return new Matrix4(true);
    }
    
    public static Matrix4 createScale(Vector3 scale) {
        Matrix4 m = createZero();
        m.m[M00] = scale.x;
        m.m[M11] = scale.y;
        m.m[M22] = scale.z;
        m.m[M33] = 1.0f;
        return m;
    }
    
    public static Matrix4 createTranslation(Vector3 translate) {
        Matrix4 m = createIdentity();
        m.m[M03] = translate.x;
        m.m[M13] = translate.y;
        m.m[M23] = translate.z;
        return m;
    }
    
    public static Matrix4 createRotationX(float angle) {
        Matrix4 m = createIdentity();
        float c = MathUtil.cos(angle);
        float s = MathUtil.sin(angle);
        m.m[M11] = c;
        m.m[M12] = -s;
        m.m[M21] = s;
        m.m[M22] = c;
        return m;
    }
    
    public static Matrix4 createRotationY(float angle) {
        Matrix4 m = createIdentity();
        float c = MathUtil.cos(angle);
        float s = MathUtil.sin(angle);
        m.m[M00] = c;
        m.m[M02] = s;
        m.m[M20] = -s;
        m.m[M22] = c;
        return m;
    }
    
    public static Matrix4 createRotationZ(float angle) {
        Matrix4 m = createIdentity();
        float c = MathUtil.cos(angle);
        float s = MathUtil.sin(angle);
        m.m[M00] = c;
        m.m[M01] = -s;
        m.m[M10] = s;
        m.m[M11] = c;
        return m;
    }
    
    public static Matrix4 createPerspective(float fov, float ratio, float near, float far) {
        Matrix4 m = createZero();
        fov = 1.0f / MathUtil.tan(fov * 0.5f);
        m.m[M00] = fov / ratio;
        m.m[M11] = fov;
        m.m[M22] = (far + near) / (near - far);
        m.m[M23] = 2 * far * near / (near - far);
        m.m[M32] = -1;
        return m;
    }
    
    public static Matrix4 createOrthographic(Vector3 min, Vector3 max) {
        Matrix4 m = createIdentity();
        m.m[M00] = 2.0f / (max.x - min.x);
        m.m[M11] = 2.0f / (max.y - min.y);
        m.m[M22] = -2.0f / (max.z - min.z);
        m.m[M03] = - (max.x + min.x) / (max.x - min.x);
        m.m[M13] = - (max.y + min.y) / (max.y - min.y);
        m.m[M23] = - (max.z + min.z) / (max.z - min.z);
        return m;
    }
    
    public static Matrix4 createLookAt(Vector3 position, Vector3 target, Vector3 up) {
        Matrix4 m = createIdentity();
        Vector3 z = target.subtract(position).normalize().scale(-1);
        Vector3 x = z.cross(up).normalize();
        Vector3 y = x.cross(z).normalize();
        m.m[M00] = x.x; m.m[M01] = x.y; m.m[M02] = x.z; m.m[M03] = 0;
        m.m[M10] = y.x; m.m[M11] = y.y; m.m[M12] = y.z; m.m[M13] = 0;
        m.m[M20] = z.x; m.m[M21] = z.y; m.m[M22] = z.z; m.m[M23] = 0;
        m.m[M30] = 0.0f; m.m[M31] = 0.0f; m.m[M32] = 0.0f; m.m[M33] = 1.0f; 
        m.multiplySelf(createTranslation(position.scale(-1)));
        return m;
    }
    
    public Matrix4 multiplySelf(Matrix4 r) {
        set(mul(r));
        return this;
    }
    
    public Matrix4 multiply(Matrix4 r) {
        return new Matrix4(mul(r));
    }
    
    public Matrix4 multiply(Matrix4 r, Matrix4 out) {
        out.set(mul(r));
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
            if (!MathUtil.approxEquals(m[i], rm[i])) return false;
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
    
    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < 4; row++) {
            s += "[";
            for (int col = 0; col < 4; col++) {
                s += String.format("%1.2f", get(row, col));
                if (col != 3) {
                    s += ", ";
                }
            }
            s += "]";
            if (row != 3) {
                s += "\n";
            }
        }
        return s;
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

    public Matrix4 copy() {
        return new Matrix4(this);
    }
    
}