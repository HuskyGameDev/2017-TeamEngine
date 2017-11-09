package oasis.math;

import oasis.util.QuickHash;

/**
 * 2D int vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector2i {

    public int x, y; 
    
    public Vector2i(int x, int y) {
        this.x = x; 
        this.y = y; 
    }
    
    public Vector2i(int[] a) {
        this.x = a[0]; 
        this.y = a[1]; 
    }
    
    public Vector2i(Vector2i r) {
        this.x = r.x; 
        this.y = r.y; 
    }
    
    public Vector2i(int a) {
        this.x = a; 
        this.y = a; 
    }
    
    public Vector2i() {
        this.x = 0; 
        this.y = 0; 
    }
    
    public Vector2i copy() { return new Vector2i(this); }
    
    public int getX() { return x; }
    public int getY() { return y; } 
    
    public Vector2i setX(int x) { this.x = x; return this; } 
    public Vector2i setY(int y) { this.y = y; return this; } 
    public Vector2i set(int a) { this.x = a; this.y = a; return this; }
    public Vector2i set(int x, int y) { this.x = x; this.y = y; return this; } 
    public Vector2i set(Vector2i r) { this.x = r.x; this.y = r.y; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y); } 
    public int lengthSq() { return x * x + y * y; } 
    
    public int dot(Vector2i r) { return x * r.x + y * r.y; }
    
    public Vector2i multiply(int s) { return new Vector2i(x * s, y * s); } 
    public Vector2i divide(int s) { return new Vector2i(x / s, y / s); } 
    public Vector2i add(Vector2i r) { return new Vector2i(x + r.x, y + r.y); } 
    public Vector2i subtract(Vector2i r) { return new Vector2i(x - r.x, y - r.y); } 
    public Vector2i multiply(Vector2i r) { return new Vector2i(x * r.x, y * r.y); } 
    public Vector2i divide(Vector2i r) { return new Vector2i(x / r.x, y / r.y); } 
    
    public Vector2i multiplySelf(int s) { return set(x * s, y * s); } 
    public Vector2i divideSelf(int s) { return set(x / s, y / s); } 
    public Vector2i addSelf(Vector2i r) { return set(x + r.x, y + r.y); } 
    public Vector2i subtractSelf(Vector2i r) { return set(x - r.x, y - r.y); } 
    public Vector2i multiplySelf(Vector2i r) { return set(x * r.x, y * r.y); } 
    public Vector2i divideSelf(Vector2i r) { return set(x / r.x, y / r.y); } 
    
    public int hashCode() {
        return QuickHash.compute(x, y);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector2i)) return false; 
        
        Vector2i r = (Vector2i) obj; 
        return x == r.x && y == r.y; 
    }
    
    public String toString() { 
        return String.format("(%d, %d)", x, y); 
    }
    
}
