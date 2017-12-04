package oasis.math;

import oasis.util.QuickHash;

/**
 * 4D float vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector4 {

    public float x, y, z, w; 
    
    public Vector4(float x, float y, float z, float w) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
        this.w = w; 
    }
    
    public Vector4(float[] a) {
        this.x = a[0]; 
        this.y = a[1]; 
        this.z = a[2]; 
        this.w = a[3]; 
    }
    
    public Vector4(float xyz, float w) 
    {
        this.x = xyz; 
        this.y = xyz; 
        this.z = xyz; 
        this.w = w; 
    }
    
    public Vector4(Vector4i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4(Vector4 r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4(Quaternion r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4(Vector3 r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = 1f; 
    }
    
    public Vector4(Vector2 r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0f; 
        this.w = 1f; 
    }
    
    public Vector4(float a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
        this.w = a; 
    }
    
    public Vector4() {
        this.x = 0f; 
        this.y = 0f; 
        this.z = 0f; 
        this.w = 1f; 
    }
    
    public Vector4 copy() { return new Vector4(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    public float getZ() { return z; } 
    public float getW() { return w; } 
    
    public Vector4 setX(float x) { this.x = x; return this; } 
    public Vector4 setY(float y) { this.y = y; return this; } 
    public Vector4 setZ(float z) { this.z = z; return this; } 
    public Vector4 setW(float w) { this.w = w; return this; } 
    public Vector4 set(float a) { this.x = a; this.y = a; this.z = a; this.w = a; return this; }
    public Vector4 set(float x, float y, float z, float w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; } 
    public Vector4 set(Vector4 r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z + w * w); } 
    public float lengthSq() { return x * x + y * y + z * z + w * w; } 
    
    public float dot(Vector4 r) { return x * r.x + y * r.y + z * r.z + w * r.w; }
    
    public Vector4 multiply(float s) { return new Vector4(x * s, y * s, z * s, w * s); } 
    public Vector4 add(Vector4 r) { return new Vector4(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4 subtract(Vector4 r) { return new Vector4(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4 multiply(Vector4 r) { return new Vector4(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4 divide(Vector4 r) { return new Vector4(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public Vector4 normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector4(x / len, y / len, z / len, w / len); 
        }
        return new Vector4(0f); 
    }
    
    public Vector4 multiplySelf(float s) { return set(x * s, y * s, z * s, w * s); } 
    public Vector4 addSelf(Vector4 r) { return set(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4 subtractSelf(Vector4 r) { return set(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4 multiplySelf(Vector4 r) { return set(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4 divideSelf(Vector4 r) { return set(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public Vector4 normalizeSelf() {
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
        if (!(obj instanceof Vector4)) return false; 
        
        Vector4 r = (Vector4) obj; 
        return x == r.x && y == r.y && z == r.z && w == r.w; 
    }
    
    public boolean approxEquals(Vector4 r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z) && Mathf.approxEquals(w, r.w); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f, %1.2f)", x, y, z, w); 
    }
    
}
