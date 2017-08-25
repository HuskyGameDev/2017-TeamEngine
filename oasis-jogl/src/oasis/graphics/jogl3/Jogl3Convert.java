package oasis.graphics.jogl3;

import java.util.HashMap;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.graphics.Attribute;
import oasis.graphics.BlendMode;
import oasis.graphics.BufferUsage;
import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Primitive;
import oasis.graphics.Texture;
import oasis.graphics.WrapMode;

public class Jogl3Convert {
    private static final int[] PRIMITIVE_TYPE = new int[Primitive.values().length]; 
    private static final int[] BUFFER_USAGE = new int[BufferUsage.values().length]; 
    private static final int[] TEXTURE_FORMAT = new int[Texture.Format.values().length]; 
    private static final int[] MIN_FILTER = new int[MinFilter.values().length]; 
    private static final int[] MAG_FILTER = new int[MagFilter.values().length]; 
    private static final int[] WRAP_MODE = new int[WrapMode.values().length]; 
    private static final int[] BLEND_MODE = new int[BlendMode.values().length]; 
    private static final HashMap<String, Attribute> ATTRIBUTE_MAP = new HashMap<>(); 
    
    static { 
        PRIMITIVE_TYPE[Primitive.POINT_LIST.ordinal()] = GL.GL_POINTS; 
        PRIMITIVE_TYPE[Primitive.LINE_LIST.ordinal()] = GL.GL_LINES; 
        PRIMITIVE_TYPE[Primitive.LINE_STRIP.ordinal()] = GL.GL_LINES; 
        PRIMITIVE_TYPE[Primitive.TRIANGLE_LIST.ordinal()] = GL.GL_TRIANGLES; 
        PRIMITIVE_TYPE[Primitive.TRIANGLE_STRIP.ordinal()] = GL.GL_TRIANGLE_STRIP; 
        
        BUFFER_USAGE[BufferUsage.STATIC.ordinal()] = GL.GL_STATIC_DRAW; 
        BUFFER_USAGE[BufferUsage.DYNAMIC.ordinal()] = GL.GL_DYNAMIC_DRAW; 
        BUFFER_USAGE[BufferUsage.STREAM.ordinal()] = GL2.GL_STREAM_DRAW; 
        
        TEXTURE_FORMAT[Texture.Format.RGBA8.ordinal()] = GL.GL_RGBA8; 
        TEXTURE_FORMAT[Texture.Format.RGBA16F.ordinal()] = GL.GL_RGBA16F; 
        TEXTURE_FORMAT[Texture.Format.RGBA32F.ordinal()] = GL.GL_RGBA32F; 
        TEXTURE_FORMAT[Texture.Format.DEPTH24.ordinal()] = GL.GL_DEPTH_COMPONENT24; 
        TEXTURE_FORMAT[Texture.Format.DEPTH32.ordinal()] = GL.GL_DEPTH_COMPONENT32; 
        
        MIN_FILTER[MinFilter.LINEAR.ordinal()] = GL.GL_LINEAR; 
        MIN_FILTER[MinFilter.LINEAR_MIPMAP_LINEAR.ordinal()] = GL.GL_LINEAR_MIPMAP_LINEAR; 
        MIN_FILTER[MinFilter.LINEAR_MIPMAP_NEAREST.ordinal()] = GL.GL_LINEAR_MIPMAP_NEAREST; 
        MIN_FILTER[MinFilter.NEAREST.ordinal()] = GL.GL_NEAREST; 
        MIN_FILTER[MinFilter.NEAREST_MIPMAP_LINEAR.ordinal()] = GL.GL_NEAREST_MIPMAP_LINEAR; 
        MIN_FILTER[MinFilter.NEAREST_MIPMAP_NEAREST.ordinal()] = GL.GL_NEAREST_MIPMAP_NEAREST; 
        
        MAG_FILTER[MagFilter.LINEAR.ordinal()] = GL.GL_LINEAR; 
        MAG_FILTER[MagFilter.NEAREST.ordinal()] = GL.GL_NEAREST; 
        
        WRAP_MODE[WrapMode.CLAMP_EDGE.ordinal()] = GL.GL_CLAMP_TO_EDGE; 
        WRAP_MODE[WrapMode.REPEAT.ordinal()] = GL.GL_REPEAT; 
        
        BLEND_MODE[BlendMode.ONE.ordinal()] = GL.GL_ONE; 
        BLEND_MODE[BlendMode.ZERO.ordinal()] = GL.GL_ZERO; 
        BLEND_MODE[BlendMode.SRC_ALPHA.ordinal()] = GL.GL_SRC_ALPHA; 
        BLEND_MODE[BlendMode.ONE_MINUS_SRC_ALPHA.ordinal()] = GL.GL_ONE_MINUS_SRC_ALPHA; 
        
        for (Attribute a : Attribute.values()) { 
            ATTRIBUTE_MAP.put(a.getGlslName(), a); 
        }
    }
    
    public static int getBlendMode(BlendMode blend) {
        return BLEND_MODE[blend.ordinal()]; 
    }
    
    public static int getWrapMode(WrapMode wrap) {
        return WRAP_MODE[wrap.ordinal()]; 
    }
    
    public static int getMinFilter(MinFilter min) {
        return MIN_FILTER[min.ordinal()]; 
    }
    
    public static int getMagFilter(MagFilter mag) {
        return MAG_FILTER[mag.ordinal()]; 
    }
    
    public static int getPrimitive(Primitive prim) { 
        return PRIMITIVE_TYPE[prim.ordinal()]; 
    }
    
    public static int getBufferUsage(BufferUsage usage) { 
        return BUFFER_USAGE[usage.ordinal()]; 
    }
    
    public static Attribute getAttributeFromString(String name) { 
        return ATTRIBUTE_MAP.get(name); 
    }
    
    public static int getTextureFormat(Texture.Format format) { 
        return TEXTURE_FORMAT[format.ordinal()]; 
    }
    
    public static int getDefaultTextureInputFormat(Texture.Format format) { 
        return format.isDepthFormat() ? GL2.GL_DEPTH_COMPONENT : GL.GL_RGBA; 
    }
    
    public static int getDefaultTextureInputType(Texture.Format format) { 
        return format.isDepthFormat() ? GL2.GL_UNSIGNED_INT : GL.GL_UNSIGNED_INT; 
    }
    
}
