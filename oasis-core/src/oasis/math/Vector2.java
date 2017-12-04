package oasis.math;

import oasis.util.QuickHash;

/**
 * 2D float vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector2 {

    public float x, y; 
    
    public Vector2(float x, float y) {
        this.x = x; 
        this.y = y; 
    }
    
    public Vector2(float[] a) {
        this.x = a[0]; 
        this.y = a[1]; 
    }
    
    public Vector2(Vector2i r) {
        this.x = r.x; 
        this.y = r.y; 
    }
    
    public Vector2(Vector2 r) {
        this.x = r.x; 
        this.y = r.y; 
    }
    
    public Vector2(float a) {
        this.x = a; 
        this.y = a; 
    }
    
    public Vector2() {
        this.x = 0f; 
        this.y = 0f; 
    }
    
    public static Vector2 polar(float r, float theta) {
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta);
        return new Vector2(c * r, s * r); 
    }
    
    public Vector2 copy() { return new Vector2(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    
    public Vector2 setX(float x) { this.x = x; return this; } 
    public Vector2 setY(float y) { this.y = y; return this; } 
    public Vector2 set(float a) { this.x = a; this.y = a; return this; }
    public Vector2 set(float x, float y) { this.x = x; this.y = y; return this; } 
    public Vector2 set(Vector2 r) { this.x = r.x; this.y = r.y; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y); } 
    public float lengthSq() { return x * x + y * y; } 
    
    public float dot(Vector2 r) { return x * r.x + y * r.y; }
    
    public Vector2 multiply(float s) { return new Vector2(x * s, y * s); } 
    public Vector2 add(Vector2 r) { return new Vector2(x + r.x, y + r.y); } 
    public Vector2 subtract(Vector2 r) { return new Vector2(x - r.x, y - r.y); } 
    public Vector2 multiply(Vector2 r) { return new Vector2(x * r.x, y * r.y); } 
    public Vector2 divide(Vector2 r) { return new Vector2(x / r.x, y / r.y); } 
    
    public Vector2 rotate(float theta) { 
        Vector2 out = new Vector2(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        out.x = c * x - s * y; 
        out.y = s * x + c * y; 
        return out; 
    }
    
    public Vector2 normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector2(x / len, y / len); 
        }
        return new Vector2(0f); 
    }
    
    public Vector2 multiplySelf(float s) { return set(x * s, y * s); } 
    public Vector2 addSelf(Vector2 r) { return set(x + r.x, y + r.y); } 
    public Vector2 subtractSelf(Vector2 r) { return set(x - r.x, y - r.y); } 
    public Vector2 multiplySelf(Vector2 r) { return set(x * r.x, y * r.y); } 
    public Vector2 divideSelf(Vector2 r) { return set(x / r.x, y / r.y); } 
    
    public Vector2 rotateSelf(float theta) { 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        float x_ = c * x - s * y; 
        float y_ = s * x + c * y; 
        x = x_; 
        y = y_; 
        return this; 
    }
    
    public Vector2 normalizeSelf() {
        float len = length(); 
        if (len != 0) {
            set(x / len, y / len); 
        }
        else {
            set(0f); 
        }
        return this; 
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof Vector2)) return false; 
        
        Vector2 r = (Vector2) obj; 
        return x == r.x && y == r.y; 
    }
    
    public boolean approxEquals(Vector2 r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f)", x, y); 
    }
    
}
