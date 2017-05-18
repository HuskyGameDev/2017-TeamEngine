package nhamil.oasis.graphics;

import nhamil.oasis.math.FastMath;
import nhamil.oasis.math.Vector4;

public class ColorRgba {

    private float r, g, b, a;
    
    public ColorRgba(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public float getRed() {
        return r;
    }
    
    public float getGreen() {
        return g;
    }
    
    public float getBlue() {
        return b;
    }
    
    public float getAlpha() {
        return a;
    }
    
    public Vector4 toVector4() {
        return new Vector4(r, g, b, a);
    }

    public int toArgbHex() {
        int red = (int) FastMath.clamp(0, 255, 255 * r);
        int green = (int) FastMath.clamp(0, 255, 255 * g);
        int blue = (int) FastMath.clamp(0, 255, 255 * b);
        int alpha = (int) FastMath.clamp(0, 255, 255 * a);
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
    
}
