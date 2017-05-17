package nhamil.oasis.math;

import nhamil.oasis.util.QuickHash;

public class Vector3 {

    public float x, y, z;
    
    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }
    
    public Vector3(float x, float y) {
        set(x, y, 0.0f);
    }
    
    public Vector3(float a) {
        set(a, a, a);
    }
    
    public Vector3() {
        set(0.0f, 0.0f, 0.0f);
    }
    
    public Vector3(Vector3 r) {
        set(r.x, r.y, r.z);
    }
    
    public static Vector3 fromCartesian(float x, float y, float z) {
        return new Vector3(x, y, z);
    }
    
    public Vector3 copy() { return new Vector3(this); }
    
    public Vector3 set(Vector3 r) {
        return set(r.x, r.y, r.z);
    }
    
    public Vector3 set(float a) {
        return set(a, a, a);
    }
    public Vector3 set(float x, float y, float z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        return this;
    }
    
    public Vector3 setX(float x) { this.x = (float) x; return this; }
    public Vector3 setY(float y) { this.y = (float) y; return this; }
    public Vector3 setZ(float z) { this.z = (float) z; return this; }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    
    public float lengthSquared() { return x * x + y * y + z * z; }
    public float length() { return FastMath.sqrt(x * x + y * y + z * z); }
    
    public float dot(Vector3 r) { return x * r.x + y * r.y + z * r.z; }
    
    public Vector3 scale(float s) { return scale(s, null); }
    public Vector3 scaleAdd(float s, Vector3 r) { return scaleAdd(s, r, null); }
    public Vector3 add(Vector3 r) { return add(r, null); }
    public Vector3 subtract(Vector3 r) { return subtract(r, null); }
    public Vector3 multiply(Vector3 r) { return multiply(r, null); }
    public Vector3 divide(Vector3 r) { return divide(r, null); }
    public Vector3 normalize() { return normalize(null); }
    public Vector3 cross(Vector3 r) { return cross(r, null); }
    
    public Vector3 scaleSelf(float s) { return scale(s, this); }
    public Vector3 scaleAddSelf(float s, Vector3 r) { return scaleAdd(s, r, this); }
    public Vector3 addSelf(Vector3 r) { return add(r, this); }
    public Vector3 subtractSelf(Vector3 r) { return subtract(r, this); }
    public Vector3 multiplySelf(Vector3 r) { return multiply(r, this); }
    public Vector3 divideSelf(Vector3 r) { return divide(r, this); }
    public Vector3 normalizeSelf() { return normalize(this); }
    public Vector3 crossSelf(Vector3 r) { return cross(r, this); }
    
    public Vector3 scale(float s, Vector3 out) { return newIfNull(out).set(x * s, y * s, z * s); }
    public Vector3 scaleAdd(float s, Vector3 r, Vector3 out) { return newIfNull(out).set(x + r.x * s, y + r.y * s, z + r.z * s); }
    public Vector3 add(Vector3 r, Vector3 out) { return newIfNull(out).set(x + r.x, y + r.y, z + r.z); }
    public Vector3 subtract(Vector3 r, Vector3 out) { return newIfNull(out).set(x - r.x, y - r.y, z - r.z); }
    public Vector3 multiply(Vector3 r, Vector3 out) { return newIfNull(out).set(x * r.x, y * r.y, z * r.z); }
    public Vector3 divide(Vector3 r, Vector3 out) { return newIfNull(out).set(x / r.x, y / r.y, z / r.z); }
    public Vector3 cross(Vector3 r, Vector3 out) {
        float x_ = y * r.z - z * r.y;
        float y_ = z * r.x - x * r.z;
        float z_ = x * r.y - y * r.x;
        return newIfNull(out).set(x_, y_, z_);
    }
    
    public Vector3 normalize(Vector3 out) {
        out = newIfNull(out);
        float len = length();
        if (FastMath.equals(len, 0.0f)) {
            out.set(0.0f);
            return out;
        }
        else {
            return scale(1.0f / len, out);
        }
    }
    
    public int hashCode() {
        return QuickHash.compute(x, y, z);
    }
    
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector3)) return false;
        
        Vector3 r = (Vector3) obj;
        return FastMath.equals(x, r.x) && FastMath.equals(y, r.y) && FastMath.equals(z, r.z);
    }
    
    public boolean strictEquals(Vector3 r) {
        return x == r.x && y == r.y && z == r.z;
    }
    
    public String toString() {
        return String.format("(%1.2f, %1.2f, %1.2f)", x, y, z);
    }
    
    private Vector3 newIfNull(Vector3 r) { return r == null ? new Vector3() : r; }
    
}
