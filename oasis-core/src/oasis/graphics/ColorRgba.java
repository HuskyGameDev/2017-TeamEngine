package oasis.graphics;

import oasis.math.Mathf;
import oasis.math.Vector4f;

/**
 * Floating point values for color
 * 
 * @author Nicholas Hamilton
 *
 */
public class ColorRgba {

    /**
     * Red, green, blue, and alpha 
     */
    public float r, g, b, a;
    
    /**
     * Constructor taking in another ColorRgba
     * 
     * @param other The color to copy 
     */
    public ColorRgba(ColorRgba other) {
        this(other.r, other.g, other.b, other.a); 
    }
    
    /**
     * Constructor taking in a Vector4
     * 
     * @param other The color to copy 
     */
    public ColorRgba(Vector4f other) {
        this(other.x, other.y, other.z, other.w); 
    }
    
    /**
     * Constructor taking in RGBA values 
     * 
     * @param r Amount of red
     * @param g Amount of green 
     * @param b Amount of blue 
     * @param a Amount of alpha 
     */
    public ColorRgba(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    /**
     * Constructor taking in RGB values with an alpha of 1
     * 
     * @param r Amount of red 
     * @param g Amount of green 
     * @param b Amount of blue 
     */
    public ColorRgba(float r, float g, float b) {
        this (r, g, b, 1.0f);
    }
    
    /**
     * Constructor that sets RGBA to (0, 0, 0, 1) 
     */
    public ColorRgba() {
        this (0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    /**
     * Converts color to a 4-vector, 
     * useful to setting shader uniforms 
     * 
     * @return Vector4 equivalent 
     */
    public Vector4f toVector4() { 
        return new Vector4f(r, g, b, a); 
    }
    
    /**
     * Converts color to a packed int in the order RGBA 
     * 
     * @return Packed RGBA int 
     */
    public int getRgba() {
        int r_ = (int) Mathf.clamp(0, 255, r * 255);
        int g_ = (int) Mathf.clamp(0, 255, g * 255);
        int b_ = (int) Mathf.clamp(0, 255, b * 255);
        int a_ = (int) Mathf.clamp(0, 255, a * 255);
        return r_ << 24 | g_ << 16 | b_ << 8 | a_; 
    }
    
    /**
     * Converts color to a packed int in the order ARGB 
     * 
     * @return Packed ARGB int 
     */
    public int getArgb() {
        int r_ = (int) Mathf.clamp(0, 255, r * 255);
        int g_ = (int) Mathf.clamp(0, 255, g * 255);
        int b_ = (int) Mathf.clamp(0, 255, b * 255);
        int a_ = (int) Mathf.clamp(0, 255, a * 255);
        return a_ << 24 | r_ << 16 | g_ << 8 | b_; 
    }
    
    /**
     * Get the red value 
     * 
     * @return Red value 
     */
    public float getRed() { return r; }
    
    /**
     * Get the green value 
     * 
     * @return Green value 
     */
    public float getGreen() { return g; }
    
    /**
     * Get the blue value 
     * 
     * @return Blue value 
     */
    public float getBlue() { return b; }
    
    /**
     * Get the alpha value 
     * 
     * @return Alpha value 
     */
    public float getAlpha() { return a; }
    
    /** 
     * Set the red value 
     * 
     * @param r New red value 
     */
    public void setRed(float r) { this.r = r; }
    
    /** 
     * Set the green value 
     * 
     * @param g New green value 
     */
    public void setGreen(float g) { this.g = g; }
    
    /**
     * Set the blue value 
     * 
     * @param b New blue value 
     */
    public void setBlue(float b) { this.b = b; }
    
    /**
     * Set the alpha value 
     * 
     * @param a New alpha value 
     */
    public void setAlpha(float a) { this.a = a; }
    
    /**
     * Is the color equal to another color
     * 
     * @param other Color to check against 
     * @return If the colors are the same 
     */
    public boolean equals(ColorRgba other) {
        return r == other.r && g == other.g && b == other.b && a == other.a; 
    }
    
    /** 
     * Checks if the color is equal to another object 
     * 
     * @return If the objects are the same 
     */
    public boolean equals(Object obj) {
        if (obj == this) return true; 
        if (obj == null) return false; 
        if (!(obj instanceof ColorRgba)) return false; 
        return equals((ColorRgba) obj); 
    }
    
}