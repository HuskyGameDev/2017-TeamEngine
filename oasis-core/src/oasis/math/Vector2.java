package oasis.math;

import oasis.util.QuickHash;

public class Vector2 {

    protected float x, y;
    
    public Vector2(float x, float y) {
        set(x, y);
    }
    
    public Vector2(float xy) {
        set(xy, xy);
    }
    
    public Vector2() {
        set(0.0f, 0.0f);
    }
    
    public Vector2(Vector2 r) {
        set(r.x, r.y);
    }
    
    public static Vector2 fromCartesian(float x, float y) {
        return new Vector2(x, y);
    }
    
    public static Vector2 fromPolar(float r, float theta) {
        float c = MathUtil.cos(theta);
        float s = MathUtil.sin(theta);
        return new Vector2(c * r, s * r);
    }
    
    public Vector2 copy() { return new Vector2(this); }
    
    public Vector2 set(float a) { 
        return set(a, a);
    }
    public Vector2 set(Vector2 r) {
        return set(r.x, r.y);
    }
    
    public Vector2 set(float x, float y) {
        this.x = (float) x;
        this.y = (float) y;
        return this; 
    }
    
    public Vector2 setX(float x) { this.x = (float) x; return this; }
    public Vector2 setY(float y) { this.y = (float) y; return this; }
    
    public float getX() { return x; }
    public float getY() { return y; }
    
    public float lengthSquared() { return x * x + y * y; }
    public float length() { return MathUtil.sqrt(x * x + y * y); }
    
    public float dot(Vector2 r) { return x * r.x + y * r.y; }
    
    public Vector2 scale(float s) { return scale(s, null); }
    public Vector2 scaleAdd(float s, Vector2 r) { return scaleAdd(s, r, null); }
    public Vector2 add(Vector2 r) { return add(r, null); }
    public Vector2 subtract(Vector2 r) { return subtract(r, null); }
    public Vector2 multiply(Vector2 r) { return multiply(r, null); }
    public Vector2 divide(Vector2 r) { return divide(r, null); }
    public Vector2 rotate(float theta) { return rotate(theta, null); }
    public Vector2 normalize() { return normalize(null); }
    
    public Vector2 scaleSelf(float s) { return scale(s, this); }
    public Vector2 scaleAddSelf(float s, Vector2 r) { return scaleAdd(s, r, this); }
    public Vector2 addSelf(Vector2 r) { return add(r, this); }
    public Vector2 subtractSelf(Vector2 r) { return subtract(r, this); }
    public Vector2 multiplySelf(Vector2 r) { return multiply(r, this); }
    public Vector2 divideSelf(Vector2 r) { return divide(r, this); }
    public Vector2 rotateSelf(float theta) { return rotate(theta, this); }
    public Vector2 normalizeSelf() { return normalize(this); }
    
    public Vector2 scale(float s, Vector2 out) { out = newIfNull(out); out.x = x * s; out.y = y * s; return out; }
    public Vector2 scaleAdd(float s, Vector2 r, Vector2 out) { out = newIfNull(out); out.x = x + s * r.x; out.y = y + s * r.y; return out; }
    public Vector2 add(Vector2 r, Vector2 out) { out = newIfNull(out); out.x = x + r.x; out.y = y + r.y; return out; }
    public Vector2 subtract(Vector2 r, Vector2 out) { out = newIfNull(out); out.x = x - r.x; out.y = y - r.y; return out; }
    public Vector2 multiply(Vector2 r, Vector2 out) { out = newIfNull(out); out.x = x * r.x; out.y = y * r.y; return out; }
    public Vector2 divide(Vector2 r, Vector2 out) { out = newIfNull(out); out.x = x / r.x; out.y = y / r.y; return out; }
    public Vector2 rotate(float theta, Vector2 out) {
        out = newIfNull(out);
        float c = MathUtil.cos(theta);
        float s = MathUtil.sin(theta);
        float x_ = c * x - s * y;
        float y_ = s * x + c * y;
        out.set(x_, y_);
        return out;
    }
    public Vector2 normalize(Vector2 out) {
        out = newIfNull(out);
        float len = length();
        if (MathUtil.approxEquals(len, 0.0f)) {
            out.set(0.0f, 0.0f);
            return out;
        }
        else {
            return scale(1.0f / len, out);
        }
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector2)) return false;
        
        Vector2 r = (Vector2) obj;
        return MathUtil.approxEquals(x, r.x) && MathUtil.approxEquals(y, r.y);
    }
    
    public boolean strictEquals(Vector2 r) {
        return x == r.x && y == r.y;
    }
    
    public String toString() {
        return String.format("(%1.2f, %1.2f)", x, y);
    }
    
    private Vector2 newIfNull(Vector2 r) { return r == null ? new Vector2() : r; }
    
}
