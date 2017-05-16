package nhamil.oasis.math;

public class Matrix4 {

    public float[] m;
    
    public Matrix4() {
        m = new float[16];
    }
    
    public float get(int row, int col) {
        return m[row + col * 4];
    }
    
    public Matrix4 set(int row, int col, float value) {
        m[row + col * 4] = value;
        return this;
    }
    
}
