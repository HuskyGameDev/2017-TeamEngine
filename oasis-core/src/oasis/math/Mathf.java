package oasis.math;

/**
 * Math utilities 
 * 
 * @author Nicholas Hamilton
 *
 */
// TODO generate trig tables instead of casting to float 
public final class Mathf {
    
    /**
     * Pi
     */
    public static final float PI = 3.14159265358979f;
    
    /**
     * One divided by pi
     */
    public static final float INV_PI = 1.0f / PI;
    
    /**
     * 180 degrees 
     */
    public static final float DEG_HALF = 180.0f;
    
    /**
     * One divided by 180 degrees 
     */
    public static final float INV_DEG_HALF = 1.0f / DEG_HALF;
    
    /**
     * Small number to check if floats are approximately equal 
     */
    public static final float EPSILON = 0.00001f;
    
    // No instances 
    private Mathf() {}
    
    /**
     * Convert radians to degrees 
     * 
     * @param rad Radians 
     * @return Degrees 
     */
    public static float toDegrees(float rad) {
        return rad * DEG_HALF * INV_PI;
    }
    
    /**
     * Convert degrees to radians 
     * 
     * @param deg Degrees 
     * @return Radians 
     */
    public static float toRadians(float deg) {
        return deg * PI * INV_DEG_HALF;
    }
    
    /**
     * Get cosine of an angle in radians 
     * 
     * @param rad Degrees 
     * @return Cosine 
     */
    public static float cos(float rad) {
        return (float) Math.cos(rad);
    }
    
    /**
     * Get size of an angle in radians 
     * 
     * @param rad Degrees 
     * @return Sine 
     */
    public static float sin(float rad) {
        return (float) Math.sin(rad);
    }
    
    /**
     * Get tangent of an angle in radians 
     * 
     * @param rad Degrees 
     * @return Tangent 
     */
    public static float tan(float rad) {
        return (float) Math.tan(rad);
    }
    
    public static float acos(float rad) {
        return (float) Math.acos(rad);
    }
    
    public static float asin(float rad) {
        return (float) Math.asin(rad);
    }
    
    public static float atan(float rad) {
        return (float) Math.atan(rad); 
    }
    
    /**
     * Get square root 
     * 
     * @param x Input 
     * @return Square root 
     */
    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }
    
    /**
     * Linear interpolation. Clamps output between a and b 
     * 
     * @param a 1st value 
     * @param b 2nd value 
     * @param x Interpolation amount 
     * @return Linearly interpolated value 
     */
    public static float lerp(float a, float b, float x) {
        return clamp(a, b, lerpUnsafe(a, b, x));
    }
    
    /**
     * Linear interpolation
     * 
     * @param a 1st value 
     * @param b 2nd value 
     * @param x Interpolation amount 
     * @return Linearly interpolated value 
     */
    public static float lerpUnsafe(float a, float b, float x) {
        return a * (1 - x) + x * b;
    }
    
    /**
     * Clamp value between two values. 
     * 
     * @param a 1st value 
     * @param b 2nd value 
     * @param x Value to clamp
     * @return Clamped value 
     */
    public static float clamp(float a, float b, float x) {
        if (b < a) {
            float tmp = b;
            b = a;
            a = tmp;
        }
        
        return Math.min(b, Math.max(a, x));
    }
    
    /**
     * Clamp value between two values. Min should be smaller 
     * than max 
     * 
     * @param min 1st value 
     * @param max 2nd value 
     * @param x Value to clamp
     * @return Clamped value 
     */
    public static float clampUnsafe(float min, float max, float x) {
        return Math.min(max, Math.max(min, x));
    }
    
    /**
     * Get absolute value 
     * 
     * @param x Value 
     * @return Absolute value of x
     */
    public static float abs(float x) {
        return x < 0 ? -x : x;
    }
    
    /**
     * Check if two floats are approximately equal
     * 
     * @param a 1st value 
     * @param b 2nd value 
     * @return If values are approximately equal 
     */
    public static boolean approxEquals(float a, float b) {
        return abs(a - b) < EPSILON;
    }
    
}