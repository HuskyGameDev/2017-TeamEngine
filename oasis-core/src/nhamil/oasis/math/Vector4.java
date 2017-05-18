package nhamil.oasis.math;

public class Vector4 {

    public float x, y, z, w;
    
    public Vector4() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vector4 copy() {
        return new Vector4(x, y, z, w);
    }
    
    public Vector4 set(Vector4 r) {
        x = r.x;
        y = r.y;
        z = r.z;
        w = r.w;
        return this;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }
    
    public String toString() {
        return String.format("(%1.2f, %1.2f, %1.2f, %1.2f)", x, y, z, w);
    }
    
}
