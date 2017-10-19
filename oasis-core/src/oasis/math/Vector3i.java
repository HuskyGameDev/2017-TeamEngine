package oasis.math;

import oasis.util.QuickHash;

/**
 * 3D int vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector3i {

    public int x, y, z; 
    
    public Vector3i(int x, int y, int z) {
        this.x = x; 
        this.y = y; 
        this.z = z; 
    }
    
    public Vector3i(Vector3i r) {
        this.x = r.x; 
        this.y = r.y; 
        this.z = r.z; 
    }
    
    public Vector3i(Vector2i r) { 
        this.x = r.x; 
        this.y = r.y; 
        this.z = 0; 
    }
    
    public Vector3i(int a) {
        this.x = a; 
        this.y = a; 
        this.z = a; 
    }
    
    public Vector3i() {
        this.x = 0; 
        this.y = 0; 
        this.z = 0; 
    }
    
    public Vector3i copy() { return new Vector3i(this); }
    
    public int getX() { return x; }
    public int getY() { return y; } 
    public int getZ() { return z; } 
    
    public Vector3i setX(int x) { this.x = x; return this; } 
    public Vector3i setY(int y) { this.y = y; return this; } 
    public Vector3i setZ(int z) { this.z = z; return this; } 
    public Vector3i set(int a) { this.x = a; this.y = a; this.z = a; return this; }
    public Vector3i set(int x, int y, int z) { this.x = x; this.y = y; this.z = z; return this; } 
    public Vector3i set(Vector3i r) { this.x = r.x; this.y = r.y; this.z = r.z; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y + z * z); } 
    public int lengthSq() { return x * x + y * y + z * z; } 
    
    public int dot(Vector3i r) { return x * r.x + y * r.y + z * r.z; }
    
    public Vector3i multiply(int s) { return new Vector3i(x * s, y * s, z * s); } 
    public Vector3i divide(int s) { return new Vector3i(x / s, y / s, z / s); } 
    public Vector3i add(Vector3i r) { return new Vector3i(x + r.x, y + r.y, z + r.z); } 
    public Vector3i subtract(Vector3i r) { return new Vector3i(x - r.x, y - r.y, z - r.z); } 
    public Vector3i multiply(Vector3i r) { return new Vector3i(x * r.x, y * r.y, z * r.z); } 
    public Vector3i divide(Vector3i r) { return new Vector3i(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3i cross(Vector3i r) {
        int x_ = y * r.z - z * r.y; 
        int y_ = z * r.x - x * r.z; 
        int z_ = x * r.y - y * r.x; 
        return new Vector3i(x_, y_, z_); 
    }
    
    public Vector3i multiplySelf(int s) { return set(x * s, y * s, z * s); } 
    public Vector3i divideSelf(int s) { return set(x / s, y / s, z / s); } 
    public Vector3i addSelf(Vector3i r) { return set(x + r.x, y + r.y, z + r.z); } 
    public Vector3i subtractSelf(Vector3i r) { return set(x - r.x, y - r.y, z - r.z); } 
    public Vector3i multiplySelf(Vector3i r) { return set(x * r.x, y * r.y, z * r.z); } 
    public Vector3i divideSelf(Vector3i r) { return set(x / r.x, y / r.y, z / r.z); } 
    
    public Vector3i crossSelf(Vector3i r) {
        int x_ = y * r.z - z * r.y; 
        int y_ = z * r.x - x * r.z; 
        int z_ = x * r.y - y * r.x; 
        return set(x_, y_, z_); 
    }

    public int hashCode() {
        return QuickHash.compute(x, y, z);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector3i)) return false; 
        
        Vector3i r = (Vector3i) obj; 
        return x == r.x && y == r.y && z == r.z; 
    }
    
    public String toString() { 
        return String.format("(%d, %d, %d)", x, y, z); 
    }
    
}
