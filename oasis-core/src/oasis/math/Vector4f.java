package oasis.math;

import oasis.util.QuickHash;

/**
 * 4D float vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector4f {

    public float x, y, z, w; 
    
    public Vector4f(float x, float y, float z, float w) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
        this.w = w; 
    }
    
    public Vector4f(Vector4i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4f(Vector4f r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4f(Quaternionf r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4f(Vector3f r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = 1f; 
    }
    
    public Vector4f(Vector2f r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0f; 
        this.w = 1f; 
    }
    
    public Vector4f(float a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
        this.w = a; 
    }
    
    public Vector4f() {
        this.x = 0f; 
        this.y = 0f; 
        this.z = 0f; 
        this.w = 1f; 
    }
    
    public Vector4f copy() { return new Vector4f(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    public float getZ() { return z; } 
    public float getW() { return w; } 
    
    public Vector4f setX(float x) { this.x = x; return this; } 
    public Vector4f setY(float y) { this.y = y; return this; } 
    public Vector4f setZ(float z) { this.z = z; return this; } 
    public Vector4f setW(float w) { this.w = w; return this; } 
    public Vector4f set(float a) { this.x = a; this.y = a; this.z = a; this.w = a; return this; }
    public Vector4f set(float x, float y, float z, float w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; } 
    public Vector4f set(Vector4f r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z + w * w); } 
    public float lengthSq() { return x * x + y * y + z * z + w * w; } 
    
    public float dot(Vector4f r) { return x * r.x + y * r.y + z * r.z + w * r.w; }
    
    public Vector4f multiply(float s) { return new Vector4f(x * s, y * s, z * s, w * s); } 
    public Vector4f add(Vector4f r) { return new Vector4f(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4f subtract(Vector4f r) { return new Vector4f(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4f multiply(Vector4f r) { return new Vector4f(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4f divide(Vector4f r) { return new Vector4f(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public Vector4f normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector4f(x / len, y / len, z / len, w / len); 
        }
        return new Vector4f(0f); 
    }
    
    public Vector4f multiplySelf(float s) { return set(x * s, y * s, z * s, w * s); } 
    public Vector4f addSelf(Vector4f r) { return set(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4f subtractSelf(Vector4f r) { return set(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4f multiplySelf(Vector4f r) { return set(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4f divideSelf(Vector4f r) { return set(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public Vector4f normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            return set(x / len, y / len, z / len, w / len); 
        }
        return set(0f); 
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y, z, w);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector4f)) return false; 
        
        Vector4f r = (Vector4f) obj; 
        return x == r.x && y == r.y && z == r.z && w == r.w; 
    }
    
    public boolean approxEquals(Vector4f r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z) && Mathf.approxEquals(w, r.w); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f, %1.2f)", x, y, z, w); 
    }
    
}
