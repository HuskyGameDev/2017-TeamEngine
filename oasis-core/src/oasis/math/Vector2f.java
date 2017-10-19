package oasis.math;

import oasis.util.QuickHash;

/**
 * 2D float vector 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Vector2f {

    public float x, y; 
    
    public Vector2f(float x, float y) {
        this.x = x; 
        this.y = y; 
    }
    
    public Vector2f(Vector2i r) {
        this.x = r.x; 
        this.y = r.y; 
    }
    
    public Vector2f(Vector2f r) {
        this.x = r.x; 
        this.y = r.y; 
    }
    
    public Vector2f(float a) {
        this.x = a; 
        this.y = a; 
    }
    
    public Vector2f() {
        this.x = 0f; 
        this.y = 0f; 
    }
    
    public static Vector2f polar(float r, float theta) {
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta);
        return new Vector2f(c * r, s * r); 
    }
    
    public Vector2f copy() { return new Vector2f(this); }
    
    public float getX() { return x; }
    public float getY() { return y; } 
    
    public Vector2f setX(float x) { this.x = x; return this; } 
    public Vector2f setY(float y) { this.y = y; return this; } 
    public Vector2f set(float a) { this.x = a; this.y = a; return this; }
    public Vector2f set(float x, float y) { this.x = x; this.y = y; return this; } 
    public Vector2f set(Vector2f r) { this.x = r.x; this.y = r.y; return this; } 
    
    public float length() { return Mathf.sqrt(x * x + y * y); } 
    public float lengthSq() { return x * x + y * y; } 
    
    public float dot(Vector2f r) { return x * r.x + y * r.y; }
    
    public Vector2f multiply(float s) { return new Vector2f(x * s, y * s); } 
    public Vector2f add(Vector2f r) { return new Vector2f(x + r.x, y + r.y); } 
    public Vector2f subtract(Vector2f r) { return new Vector2f(x - r.x, y - r.y); } 
    public Vector2f multiply(Vector2f r) { return new Vector2f(x * r.x, y * r.y); } 
    public Vector2f divide(Vector2f r) { return new Vector2f(x / r.x, y / r.y); } 
    
    public Vector2f rotate(float theta) { 
        Vector2f out = new Vector2f(); 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        out.x = c * x - s * y; 
        out.y = s * x + c * y; 
        return out; 
    }
    
    public Vector2f normalize() {
        float len = length(); 
        if (len != 0) {
            return new Vector2f(x / len, y / len); 
        }
        return new Vector2f(0f); 
    }
    
    public Vector2f multiplySelf(float s) { return set(x * s, y * s); } 
    public Vector2f addSelf(Vector2f r) { return set(x + r.x, y + r.y); } 
    public Vector2f subtractSelf(Vector2f r) { return set(x - r.x, y - r.y); } 
    public Vector2f multiplySelf(Vector2f r) { return set(x * r.x, y * r.y); } 
    public Vector2f divideSelf(Vector2f r) { return set(x / r.x, y / r.y); } 
    
    public Vector2f rotateSelf(float theta) { 
        float c = Mathf.cos(theta); 
        float s = Mathf.sin(theta); 
        float x_ = c * x - s * y; 
        float y_ = s * x + c * y; 
        x = x_; 
        y = y_; 
        return this; 
    }
    
    public Vector2f normalizeSelf() {
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
        if (!(obj instanceof Vector2f)) return false; 
        
        Vector2f r = (Vector2f) obj; 
        return x == r.x && y == r.y; 
    }
    
    public boolean approxEquals(Vector2f r) {
        return Mathf.approxEquals(x, r.x) && Mathf.approxEquals(y, r.y); 
    }
    
    public String toString() { 
        return String.format("(%1.2f, %1.2f)", x, y); 
    }
    
}
