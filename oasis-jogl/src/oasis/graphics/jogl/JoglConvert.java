package oasis.graphics.jogl;

import java.util.HashMap;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.graphics.Attribute;
import oasis.graphics.Primitive;
import oasis.graphics.vertex.BufferUsage;

public class JoglConvert {
    private static final int[] PRIMITIVE_TYPE = new int[Primitive.values().length]; 
    
    private static final int[] BUFFER_USAGE = new int[BufferUsage.values().length]; 
    
    private static final HashMap<String, Attribute> ATTRIBUTE_MAP = new HashMap<>(); 
    
    static { 
        PRIMITIVE_TYPE[Primitive.POINT_LIST.ordinal()] = GL.GL_POINTS; 
        PRIMITIVE_TYPE[Primitive.LINE_LIST.ordinal()] = GL.GL_LINES; 
        PRIMITIVE_TYPE[Primitive.LINE_STRIP.ordinal()] = GL.GL_LINES; 
        PRIMITIVE_TYPE[Primitive.TRIANGLE_LIST.ordinal()] = GL.GL_TRIANGLES; 
        PRIMITIVE_TYPE[Primitive.TRIANGLE_STRIP.ordinal()] = GL.GL_TRIANGLE_STRIP; 
        PRIMITIVE_TYPE[Primitive.TRIANGLE_FAN.ordinal()] = GL.GL_TRIANGLE_FAN; 
        
        BUFFER_USAGE[BufferUsage.STATIC.ordinal()] = GL.GL_STATIC_DRAW; 
        BUFFER_USAGE[BufferUsage.DYNAMIC.ordinal()] = GL.GL_DYNAMIC_DRAW; 
        BUFFER_USAGE[BufferUsage.STREAM.ordinal()] = GL2.GL_STREAM_DRAW; 
        
        for (Attribute a : Attribute.values()) { 
            ATTRIBUTE_MAP.put(a.getGlslName(), a); 
        }
    }
    
    public static int getPrimitiveInt(Primitive prim) { 
        return PRIMITIVE_TYPE[prim.ordinal()]; 
    }
    
    public static int getBufferUsageInt(BufferUsage usage) { 
        return BUFFER_USAGE[usage.ordinal()]; 
    }
    
    public static Attribute getAttributeFromString(String name) { 
        return ATTRIBUTE_MAP.get(name); 
    }
    
}
