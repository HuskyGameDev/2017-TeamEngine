package nhamil.oasis.math;

public final class FastMath {
    
    public static final float PI = 3.14159265358979f;
    public static final float INV_PI = 1.0f / PI;
    public static final float DEG_HALF = 180.0f;
    public static final float INV_DEG_HALF = 1.0f / DEG_HALF;
    public static final float EPSILON = 0.00001f;
    
    private FastMath() {}
    
    public static float toDegrees(float rad) {
        return rad * DEG_HALF * INV_PI;
    }
    
    public static float toRadians(float deg) {
        return deg * PI * INV_DEG_HALF;
    }
    
    public static float cos(float rad) {
        return (float) Math.cos(rad);
    }
    
    public static float sin(float rad) {
        return (float) Math.sin(rad);
    }
    
    public static float tan(float rad) {
        return (float) Math.tan(rad);
    }
    
    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }
    
    public static float lerp(float a, float b, float x) {
        return clamp(a, b, lerpUnsafe(a, b, x));
    }
    
    public static float lerpUnsafe(float a, float b, float x) {
        return a * (1 - x) + x * b;
    }
    
    public static float clamp(float min, float max, float x) {
        if (max < min) {
            float tmp = max;
            max = min;
            min = tmp;
        }
        
        return Math.min(max, Math.max(min, x));
    }
    
    public static float clampUnsafe(float min, float max, float x) {
        return Math.min(max, Math.max(min, x));
    }
    
    public static float abs(float x) {
        return x < 0 ? -x : x;
    }
    
    public static boolean equals(float a, float b) {
        return abs(a - b) < EPSILON;
    }
    
}
