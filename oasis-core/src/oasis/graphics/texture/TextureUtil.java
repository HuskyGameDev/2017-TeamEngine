package oasis.graphics.texture;

import oasis.math.Mathf;
import oasis.math.Vector4;

public final class TextureUtil {

    private TextureUtil() {} 
    
    public static final float INV_255 = 1.0f / 255.0f; 
    
    public static int getRgbaFromVector4(Vector4 in) {
        int red = (int) Mathf.clampUnsafe(0, 255, in.x * 255); 
        int green = (int) Mathf.clampUnsafe(0, 255, in.y * 255); 
        int blue = (int) Mathf.clampUnsafe(0, 255, in.z * 255); 
        int alpha = (int) Mathf.clampUnsafe(0, 255, in.w * 255); 
        return red << 24 | green << 16 | blue << 8 | alpha; 
    }
    
    public static int getRgbaFromVector4(float r, float g, float b, float a) {
        int red = (int) Mathf.clampUnsafe(0, 255, r * 255); 
        int green = (int) Mathf.clampUnsafe(0, 255, g * 255); 
        int blue = (int) Mathf.clampUnsafe(0, 255, b * 255); 
        int alpha = (int) Mathf.clampUnsafe(0, 255, a * 255); 
        return red << 24 | green << 16 | blue << 8 | alpha; 
    }
    
    public static void getVector4FromRgba(int rgba, Vector4 out) {
        out.x = ((rgba >>> 24) & 255) * INV_255; 
        out.y = ((rgba >>> 16) & 255) * INV_255; 
        out.z = ((rgba >>> 8) & 255) * INV_255; 
        out.w = (rgba & 255) * INV_255; 
    }
    
    public static float getRgbaRed(int rgba) {
        return ((rgba >>> 24) & 255) * INV_255;  
    }
    
    public static float getRgbaGreen(int rgba) {
        return ((rgba >>> 16) & 255) * INV_255;  
    }
    
    public static float getRgbaBlue(int rgba) {
        return ((rgba >>> 8) & 255) * INV_255;  
    }
    
    public static float getRgbaAlpha(int rgba) {
        return (rgba & 255) * INV_255;  
    }
    
}
