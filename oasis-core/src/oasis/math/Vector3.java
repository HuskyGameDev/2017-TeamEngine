package oasis.math;

import oasis.util.QuickHash;

/**
 * 3D float vector
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector3 {

    public float x, y, z; 
    
    public Vector3(float x, float y, float z) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
    }
    
    public Vector3(Vector3i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
    }
    
    public Vector3(Vector3 r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
    }
    
    public Vector3(Vector2 r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0f; 
    }
    
    public Vector3(float a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
    }
    
    public Vector3(float[] a) {
        this.x = a[0]; 
        this.y = a[1]; 
        this.z = a[2]; 
    }
    
    public Vector3() {
        this.x = 0f; 
        this.y = 0f; 
        this.z = 0f; 
    }
    
    public Vector3 copy() { return new Vector3(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    public float getZ() { return z; } 
    
    public Vector3 setX(float x) { this.x = x; return this; } 
    public Vector3 setY(float y) { this.y = y; return this; } 
    public Vector3 setZ(float z) { this.z = z; return this; } 
    public Vector3 set(float a) { this.x = a; this.y = a; this.z = a; return this; }
    public Vector3 set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; } 
    public Vector3 set(Vector3 r) { this.x = r.x; this.y = r.y; this.z = r.z; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z); } 
    public float lengthSq() { return x * x + y * y + z * z; } 
    
    public float dot(Vector3 r) { return x * r.x + y * r.y + z * r.z; }
    
    public Vector3 multiply(float s) { return new Vector3(x * s, y * s, z * s); } 
    public Vector3 add(Vector3 r) { return new Vector3(x + r.x, y + r.y, z + r.z); } 
    public Vector3 subtract(Vector3 r) { return new Vector3(x - r.x, y - r.y, z - r.z); } 
    public Vector3 multiply(Vector3 r) { return new Vector3(x * r.x, y * r.y, z * r.z); } 
    public Vector3 divide(Vector3 r) { return new Vector3(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3 normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector3(x / len, y / len, z / len); 
        }
        return new Vector3(0f); 
    }
    
    public Vector3 cross(Vector3 r) {
        float x_ = y * r.z - z * r.y; 
        float y_ = z * r.x - x * r.z; 
        float z_ = x * r.y - y * r.x; 
        return new Vector3(x_, y_, z_); 
    }
    
    public Vector3 rotate(Quaternion r) { 
        Quaternion res = r.multiply(this).multiply(r.conjugate()); 
        return new Vector3(res.x, res.y, res.z); 
    }
    
    public Vector3 multiplySelf(float s) { return set(x * s, y * s, z * s); } 
    public Vector3 addSelf(Vector3 r) { return set(x + r.x, y + r.y, z + r.z); } 
    public Vector3 subtractSelf(Vector3 r) { return set(x - r.x, y - r.y, z - r.z); } 
    public Vector3 multiplySelf(Vector3 r) { return set(x * r.x, y * r.y, z * r.z); } 
    public Vector3 divideSelf(Vector3 r) { return set(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3 normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            set(x / len, y / len, z / len); 
        }
        else {
            set(0f); 
        }
        return this; 
    }
    
    public Vector3 crossSelf(Vector3 r) {
        float x_ = y * r.z - z * r.y; 
        float y_ = z * r.x - x * r.z; 
        float z_ = x * r.y - y * r.x; 
        return set(x_, y_, z_); 
    }

    public Vector3 rotateSelf(Quaternion r) { 
        Quaternion res = r.multiply(this).multiply(r.conjugate()); 
        return set(res.x, res.y, res.z); 
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y, z);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector3)) return false; 
        
        Vector3 r = (Vector3) obj; 
        return x == r.x && y == r.y && z == r.z; 
    }
    
    public boolean approxEquals(Vector3 r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f)", x, y, z); 
    }
    
}
