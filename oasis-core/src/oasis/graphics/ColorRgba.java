package oasis.graphics;

import oasis.math.FastMath;
import oasis.math.Vector4;

public class ColorRgba {

    public float r, g, b, a;
    
    public ColorRgba(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public ColorRgba(float r, float g, float b) {
        this (r, g, b, 1.0f);
    }
    
    public ColorRgba() {
        this (0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Vector4 toVector4() { 
        return new Vector4(r, g, b, a); 
    }
    
    public int getRgba() {
        int r_ = (int) FastMath.clamp(0, 255, r * 255);
        int g_ = (int) FastMath.clamp(0, 255, g * 255);
        int b_ = (int) FastMath.clamp(0, 255, b * 255);
        int a_ = (int) FastMath.clamp(0, 255, a * 255);
        return r_ << 24 | g_ << 16 | b_ << 8 | a_; 
    }
    
    public int getArgb() {
        int r_ = (int) FastMath.clamp(0, 255, r * 255);
        int g_ = (int) FastMath.clamp(0, 255, g * 255);
        int b_ = (int) FastMath.clamp(0, 255, b * 255);
        int a_ = (int) FastMath.clamp(0, 255, a * 255);
        return a_ << 24 | r_ << 16 | g_ << 8 | b_; 
    }
    
    public float getRed() { return r; }
    public float getGreen() { return g; }
    public float getBlue() { return b; }
    public float getAlpha() { return a; }
    
    public void setRed(float r) { this.r = r; }
    public void setGreen(float g) { this.g = g; }
    public void setBlue(float b) { this.b = b; }
    public void setAlpha(float a) { this.a = a; }
    
}