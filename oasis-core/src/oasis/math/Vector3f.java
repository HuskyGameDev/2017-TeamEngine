package oasis.math;

import oasis.util.QuickHash;

public class Vector3f {

    public float x, y, z; 
    
    public Vector3f(float x, float y, float z) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
    }
    
    public Vector3f(Vector3i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
    }
    
    public Vector3f(Vector3f r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
    }
    
    public Vector3f(Vector2f r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0f; 
    }
    
    public Vector3f(float a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
    }
    
    public Vector3f() {
        this.x = 0f; 
        this.y = 0f; 
        this.z = 0f; 
    }
    
    public Vector3f copy() { return new Vector3f(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    public float getZ() { return z; } 
    
    public Vector3f setX(float x) { this.x = x; return this; } 
    public Vector3f setY(float y) { this.y = y; return this; } 
    public Vector3f setZ(float z) { this.z = z; return this; } 
    public Vector3f set(float a) { this.x = a; this.y = a; this.z = a; return this; }
    public Vector3f set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; } 
    public Vector3f set(Vector3f r) { this.x = r.x; this.y = r.y; this.z = r.z; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z); } 
    public float lengthSq() { return x * x + y * y + z * z; } 
    
    public float dot(Vector3f r) { return x * r.x + y * r.y + z * r.z; }
    
    public Vector3f multiply(float s) { return new Vector3f(x * s, y * s, z * s); } 
    public Vector3f add(Vector3f r) { return new Vector3f(x + r.x, y + r.y, z + r.z); } 
    public Vector3f subtract(Vector3f r) { return new Vector3f(x - r.x, y - r.y, z - r.z); } 
    public Vector3f multiply(Vector3f r) { return new Vector3f(x * r.x, y * r.y, z * r.z); } 
    public Vector3f divide(Vector3f r) { return new Vector3f(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3f normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector3f(x / len, y / len, z / len); 
        }
        return new Vector3f(0f); 
    }
    
    public Vector3f cross(Vector3f r) {
        float x_ = y * r.z - z * r.y; 
        float y_ = z * r.x - x * r.z; 
        float z_ = x * r.y - y * r.x; 
        return new Vector3f(x_, y_, z_); 
    }
    
    public Vector3f rotate(Quaternionf r) { 
        Quaternionf res = r.multiply(this).multiply(r.conjugate()); 
        return new Vector3f(res.x, res.y, res.z); 
    }
    
    public Vector3f multiplySelf(float s) { return set(x * s, y * s, z * s); } 
    public Vector3f addSelf(Vector3f r) { return set(x + r.x, y + r.y, z + r.z); } 
    public Vector3f subtractSelf(Vector3f r) { return set(x - r.x, y - r.y, z - r.z); } 
    public Vector3f multiplySelf(Vector3f r) { return set(x * r.x, y * r.y, z * r.z); } 
    public Vector3f divideSelf(Vector3f r) { return set(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3f normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            set(x / len, y / len, z / len); 
        }
        else {
            set(0f); 
        }
        return this; 
    }
    
    public Vector3f crossSelf(Vector3f r) {
        float x_ = y * r.z - z * r.y; 
        float y_ = z * r.x - x * r.z; 
        float z_ = x * r.y - y * r.x; 
        return set(x_, y_, z_); 
    }

    public Vector3f rotateSelf(Quaternionf r) { 
        Quaternionf res = r.multiply(this).multiply(r.conjugate()); 
        return set(res.x, res.y, res.z); 
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y, z);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector3f)) return false; 
        
        Vector3f r = (Vector3f) obj; 
        return x == r.x && y == r.y && z == r.z; 
    }
    
    public boolean approxEquals(Vector3f r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y) && Mathf.approxEquals(z, r.z); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f, %1.2f)", x, y, z); 
    }
    
}
