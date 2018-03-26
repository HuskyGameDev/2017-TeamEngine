package oasis.math;

/**
 * float quaternion, useful for rotation 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Quaternion {

    private static final Vector3 UP = new Vector3(0, 1, 0); 
    
    public float x, y, z, w; 
    
    public Quaternion() {
        this.x = 0; 
        this.y = 0; 
        this.z = 0; 
        this.w = 1; 
    }
    
    public Quaternion(float x, float y, float z, float w) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
        this.w = w; 
    }
    
    public Quaternion(float a) { 
        this.x = a; 
        this.y = a; 
        this.z = a; 
        this.w = a; 
    }
    
    public Quaternion(Quaternion r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Quaternion(Vector4 r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public static Quaternion axisAngle(Vector3 r, float theta) { 
        float s = Mathf.sin(theta / 2f); 
        float c = Mathf.cos(theta / 2f); 
        r = r.normalize(); 
        return new Quaternion(r.x * s, r.y * s, r.z * s, c); 
    }
    
    public static Quaternion direction(Vector3 r) {
        return direction(r, UP); 
    }
    
    public static Quaternion direction(Vector3 dir, Vector3 up) {
        return fromMatrix(Matrix4.lookAt(new Vector3(), dir, up)); 
    }
    
    // TODO make sure this is correct 
    public static Quaternion fromMatrix(Matrix4 mat) {
        Quaternion q = new Quaternion(); 
        
        float[] m = mat.get(new float[16]); 
        
        float t = 1 + m[0] + m[5] + m[10]; 
        
        if (t > 0.001) {
            float s = 1.0f / (2 * Mathf.sqrt(t)); 
            q.x = (m[9] - m[6]) * s; 
            q.y = (m[2] - m[8]) * s; 
            q.z = (m[4] - m[1]) * s;
            q.w = 0.25f / s; 
            return q; 
        }
        
        if (m[0] > m[5] && m[0] > m[10]) {
            float s = 1.0f / (2 * Mathf.sqrt(1 + m[0] - m[5] - m[10])); 
            q.x = 0.25f / s; 
            q.y = (m[4] + m[1]) * s;
            q.z = (m[2] + m[8]) * s; 
            q.w = (m[9] - m[6]) * s; 
            return q; 
        }
        else if (m[5] > m[10]) {
            float s = 1.0f / (2 * Mathf.sqrt(1 + m[5] - m[0] - m[10])); 
            q.x = (m[4] + m[1]) * s; 
            q.y = 0.25f / s; 
            q.z = (m[9] + m[6]) * s; 
            q.w = (m[2] - m[8]) * s; 
            return q; 
        }
        else {
            float s = 1.0f / (2 * Mathf.sqrt(1 + m[10] - m[0] - m[5])); 
            q.x = (m[2] + m[8]) * s; 
            q.y = (m[9] + m[6]) * s; 
            q.z = 0.25f / s; 
            q.w = (m[4] - m[1]) * s; 
            return q; 
        }
    }
    
    public float getX() { return x; } 
    public float getY() { return y; } 
    public float getZ() { return z; } 
    public float getW() { return w; } 
    
    public Quaternion setX(float x) { this.x = x; return this; } 
    public Quaternion setY(float y) { this.y = y; return this; } 
    public Quaternion setZ(float z) { this.z = z; return this; } 
    public Quaternion setW(float w) { this.w = w; return this; } 
    public Quaternion set(float a) { this.x = a; this.y = a; this.z = a; this.w = a; return this; }
    public Quaternion set(float x, float y, float z, float w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; } 
    public Quaternion set(Quaternion r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    public Quaternion set(Vector4 r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z + w * w); } 
    public float lengthSq() { return x * x + y * y + z * z + w * w; } 
    
    public Quaternion multiply(float s) { return new Quaternion(x * s, y * s, z * s, w * s); } 
    public Quaternion add(Quaternion r) { return new Quaternion(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Quaternion subtract(Quaternion r) { return new Quaternion(x - r.x, y - r.y, z - r.z, w - r.w); } 
    
    public Quaternion multiply(Quaternion r) { 
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z; 
        float x_ = w * r.x + x * r.w + y * r.z - z * r.y; 
        float y_ = w * r.y - x * r.z + y * r.w + z * r.x; 
        float z_ = w * r.z + x * r.y - y * r.x + z * r.w; 
        return new Quaternion(x_, y_, z_, w_); 
    }
    
    public Quaternion multiply(Vector3 r) { 
        float w_ = -x * r.x - y * r.y - z * r.z; 
        float x_ =  w * r.x + y * r.z - z * r.y; 
        float y_ =  w * r.y - x * r.z + z * r.x; 
        float z_ =  w * r.z + x * r.y - y * r.x; 
        return new Quaternion(x_, y_, z_, w_); 
    }
    
    public Quaternion conjugate() { 
        return new Quaternion(-x, -y, -z, w); 
    }
    
    public Quaternion normalize() {
        float len = length(); 
        if (len != 0) {
            return new Quaternion(x / len, y / len, z / len, w / len); 
        }
        return new Quaternion(0f); 
    }
    
    public Quaternion inverse() { 
        return conjugate().multiplySelf(1f / lengthSq()); 
    }
    
    public Quaternion multiplySelf(float s) { return set(x * s, y * s, z * s, w * s); } 
    public Quaternion addSelf(Quaternion r) { return set(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Quaternion subtractSelf(Quaternion r) { return set(x - r.x, y - r.y, z - r.z, w - r.w); } 
    
    public Quaternion multiplySelf(Quaternion r) { 
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z; 
        float x_ = w * r.x + x * r.w + y * r.z - z * r.y; 
        float y_ = w * r.y - x * r.z + y * r.w + z * r.x; 
        float z_ = w * r.z + x * r.y - y * r.x + z * r.w; 
        return set(x_, y_, z_, w_); 
    }
    
    public Quaternion multiplySelf(Vector3 r) { 
        float w_ = -x * r.x - y * r.y - z * r.z; 
        float x_ =  w * r.x + + y * r.z - z * r.y; 
        float y_ =  w * r.y - x * r.z + z * r.x; 
        float z_ =  w * r.z + x * r.y - y * r.x; 
        return set(x_, y_, z_, w_); 
    }
    
    public Quaternion conjugateSelf() { 
        return set(-x, -y, -z, w); 
    }
    
    public Quaternion normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            return set(x / len, y / len, z / len, w / len); 
        }
        return set(0f); 
    }
    
    public Quaternion inverseSelf() { 
        return conjugateSelf().multiplySelf(1f / lengthSq()); 
    }
    
    public Matrix4 toMatrix4f() {
        Matrix4 out = Matrix4.zero(); 
        out.m00 = 1f - 2f * y * y - 2f * z * z; 
        out.m01 = 2f * x * y - 2f * z * w; 
        out.m02 = 2f * x * z + 2f * y * w;
        out.m10 = 2f * x * y + 2f * z * w; 
        out.m11 = 1f - 2f * x * x - 2f * z * z; 
        out.m12 = 2f * y * z - 2f * x * w; 
        out.m20 = 2f * x * z - 2f * y * w; 
        out.m21 = 2f * y * z + 2f * x * w; 
        out.m22 = 1f - 2f * x * x - 2f * y * y; 
        out.m33 = 1f; 
        return out; 
    }
    
    public Vector3 getForward() {
        return new Vector3(0, 0, -1).rotate(this); 
    }
    
    public Vector3 getBackward() {
        return new Vector3(0, 0, 1).rotate(this); 
    }
    
    public Vector3 getUp() {
        return new Vector3(0, 1, 0).rotate(this); 
    }
    
    public Vector3 getDown() {
        return new Vector3(0, -1, 0).rotate(this); 
    }
    
    public Vector3 getLeft() {
        return new Vector3(-1, 0, 0).rotate(this); 
    }
    
    public Vector3 getRight() {
        return new Vector3(1, 0, 0).rotate(this); 
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Quaternion)) return false; 
        
        Quaternion r = (Quaternion) obj; 
        return x == r.x && y == r.y && z == r.z && w == r.w; 
    }
    
    public boolean approxEquals(Quaternion r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z) && Mathf.approxEquals(w, r.w); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f, %1.2f)", x, y, z, w); 
    }
    
}
