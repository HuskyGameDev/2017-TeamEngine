package nhamil.oasis.math;

public final class FastMath {
    
    private FastMath() {}
    
    public static float cos(float rad) {
        return (float) Math.cos(rad);
    }
    
    public static float sin(float rad) {
        return (float) Math.sin(rad);
    }
    
    public static float tan(float rad) {
        return (float) Math.tan(rad);
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
    
}
