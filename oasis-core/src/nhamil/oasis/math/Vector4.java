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
    
}
