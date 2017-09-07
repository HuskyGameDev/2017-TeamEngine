package oasis.math;

public class Quaternionf {

    public float x, y, z, w; 
    
    public Quaternionf(float x, float y, float z, float w) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
        this.w = w; 
    }
    
    public Quaternionf(float a) { 
        this.x = a; 
        this.y = a; 
        this.z = a; 
        this.w = a; 
    }
    
    public Quaternionf(Quaternionf r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Quaternionf(Vector4f r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Quaternionf(Vector3f r, float theta) { 
        float s = Mathf.sin(theta / 2f); 
        float c = Mathf.cos(theta / 2f); 
        r = r.normalize(); 
        set(r.x * s, r.y * s, r.z * s, c); 
    }
    
    public float getX() { return x; } 
    public float getY() { return y; } 
    public float getZ() { return z; } 
    public float getW() { return w; } 
    
    public Quaternionf setX(float x) { this.x = x; return this; } 
    public Quaternionf setY(float y) { this.y = y; return this; } 
    public Quaternionf setZ(float z) { this.z = z; return this; } 
    public Quaternionf setW(float w) { this.w = w; return this; } 
    public Quaternionf set(float a) { this.x = a; this.y = a; this.z = a; this.w = a; return this; }
    public Quaternionf set(float x, float y, float z, float w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; } 
    public Quaternionf set(Quaternionf r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    public Quaternionf set(Vector4f r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z + w * w); } 
    public float lengthSq() { return x * x + y * y + z * z + w * w; } 
    
    public Quaternionf multiply(float s) { return new Quaternionf(x * s, y * s, z * s, w * s); } 
    public Quaternionf add(Quaternionf r) { return new Quaternionf(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Quaternionf subtract(Quaternionf r) { return new Quaternionf(x - r.x, y - r.y, z - r.z, w - r.w); } 
    
    public Quaternionf multiply(Quaternionf r) { 
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z; 
        float x_ = w * r.x + x * r.w + y * r.z - z * r.y; 
        float y_ = w * r.y - x * r.z + y * r.w + z * r.x; 
        float z_ = w * r.z + x * r.y - y * r.x + z * r.w; 
        return new Quaternionf(x_, y_, z_, w_); 
    }
    
    public Quaternionf multiply(Vector3f r) { 
        float w_ = -x * r.x - y * r.y - z * r.z; 
        float x_ =  w * r.x + y * r.z - z * r.y; 
        float y_ =  w * r.y - x * r.z + z * r.x; 
        float z_ =  w * r.z + x * r.y - y * r.x; 
        return new Quaternionf(x_, y_, z_, w_); 
    }
    
    public Quaternionf conjugate() { 
        return new Quaternionf(-x, -y, -z, w); 
    }
    
    public Quaternionf normalize() {
        float len = length(); 
        if (len != 0) {
            return new Quaternionf(x / len, y / len, z / len, w / len); 
        }
        return new Quaternionf(0f); 
    }
    
    public Quaternionf inverse() { 
        return conjugate().multiplySelf(1f / lengthSq()); 
    }
    
    public Quaternionf multiplySelf(float s) { return set(x * s, y * s, z * s, w * s); } 
    public Quaternionf addSelf(Quaternionf r) { return set(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Quaternionf subtractSelf(Quaternionf r) { return set(x - r.x, y - r.y, z - r.z, w - r.w); } 
    
    public Quaternionf multiplySelf(Quaternionf r) { 
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z; 
        float x_ = w * r.x + x * r.w + y * r.z - z * r.y; 
        float y_ = w * r.y - x * r.z + y * r.w + z * r.x; 
        float z_ = w * r.z + x * r.y - y * r.x + z * r.w; 
        return set(x_, y_, z_, w_); 
    }
    
    public Quaternionf multiplySelf(Vector3f r) { 
        float w_ = -x * r.x - y * r.y - z * r.z; 
        float x_ =  w * r.x + + y * r.z - z * r.y; 
        float y_ =  w * r.y - x * r.z + z * r.x; 
        float z_ =  w * r.z + x * r.y - y * r.x; 
        return set(x_, y_, z_, w_); 
    }
    
    public Quaternionf conjugateSelf() { 
        return set(-x, -y, -z, w); 
    }
    
    public Quaternionf normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            return set(x / len, y / len, z / len, w / len); 
        }
        return set(0f); 
    }
    
    public Quaternionf inverseSelf() { 
        return conjugateSelf().multiplySelf(1f / lengthSq()); 
    }
    
    public Matrix4f toMatrix4f() {
        Matrix4f out = Matrix4f.zero(); 
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
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Quaternionf)) return false; 
        
        Quaternionf r = (Quaternionf) obj; 
        return x == r.x && y == r.y && z == r.z && w == r.w; 
    }
    
    public boolean approxEquals(Quaternionf r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z) && Mathf.approxEquals(w, r.w); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f, %1.2f)", x, y, z, w); 
    }
    
}
