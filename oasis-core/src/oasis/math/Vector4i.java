package oasis.math;

import oasis.util.QuickHash;

public class Vector4i {

    public int x, y, z, w; 
    
    public Vector4i(int x, int y, int z, int w) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
        this.w = w; 
    }
    
    public Vector4i(Vector4i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = r.w; 
    }
    
    public Vector4i(Vector3i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
        this.w = 1; 
    }
    
    public Vector4i(Vector2i r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0; 
        this.w = 1; 
    }
    
    public Vector4i(int a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
        this.w = a; 
    }
    
    public Vector4i() {
        this.x = 0; 
        this.y = 0; 
        this.z = 0; 
        this.w = 1; 
    }
    
    public Vector4i copy() { return new Vector4i(this); }
    
    public int getX() { return x; }
    public int getY() { return y; } 
    public int getZ() { return z; } 
    public int getW() { return w; } 
    
    public Vector4i setX(int x) { this.x = x; return this; } 
    public Vector4i setY(int y) { this.y = y; return this; } 
    public Vector4i setZ(int z) { this.z = z; return this; } 
    public Vector4i setW(int w) { this.w = w; return this; } 
    public Vector4i set(int a) { this.x = a; this.y = a; this.z = a; this.w = a; return this; }
    public Vector4i set(int x, int y, int z, int w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; } 
    public Vector4i set(Vector4i r) { this.x = r.x; this.y = r.y; this.z = r.z; this.w = r.w; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z + w * w); } 
    public int lengthSq() { return x * x + y * y + z * z + w * w; } 
    
    public int dot(Vector4i r) { return x * r.x + y * r.y + z * r.z + w * r.w; }
    
    public Vector4i multiply(int s) { return new Vector4i(x * s, y * s, z * s, w * s); } 
    public Vector4i divide(int s) { return new Vector4i(x / s, y / s, z / s, w / s); } 
    public Vector4i add(Vector4i r) { return new Vector4i(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4i subtract(Vector4i r) { return new Vector4i(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4i multiply(Vector4i r) { return new Vector4i(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4i divide(Vector4i r) { return new Vector4i(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public Vector4i multiplySelf(int s) { return set(x * s, y * s, z * s, w * s); } 
    public Vector4i divideSelf(int s) { return set(x / s, y / s, z / s, w / s); } 
    public Vector4i addSelf(Vector4i r) { return set(x + r.x, y + r.y, z + r.z, w + r.w); } 
    public Vector4i subtractSelf(Vector4i r) { return set(x - r.x, y - r.y, z - r.z, w - r.w); } 
    public Vector4i multiplySelf(Vector4i r) { return set(x * r.x, y * r.y, z * r.z, w * r.w); } 
    public Vector4i divideSelf(Vector4i r) { return set(x / r.x, y / r.y, z / r.z, w / r.w); } 
    
    public int hashCode() {
        return QuickHash.compute(x, y, z, w);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector4i)) return false; 
        
        Vector4i r = (Vector4i) obj; 
        return x == r.x && y == r.y && z == r.z && w == r.w; 
    }
    
    public String toString() { 
        return String.format("(%d, %d, %d, %d)", x, y, z, w); 
    }
    
}
