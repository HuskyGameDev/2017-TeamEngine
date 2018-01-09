package oasis.graphics.ogl;

import oasis.core.OasisException;
import oasis.graphics.Attribute;
import oasis.graphics.BufferUsage;
import oasis.graphics.Geometry;
import oasis.graphics.GraphicsState.BlendMode;
import oasis.graphics.GraphicsState.FillMode;
import oasis.graphics.GraphicsState.FrontFace;
import oasis.graphics.Uniform;

public class OglConvert {

    private OglConvert() {}
    
    public static int getBufferUsage(BufferUsage usage) {
        switch (usage) {
        default: 
            throw new OasisException("Unknown BufferUsage: " + usage); 
        case DYNAMIC: 
            return Ogl.GL_DYNAMIC_DRAW; 
        case STATIC: 
            return Ogl.GL_STATIC_DRAW; 
        case STREAM: 
            return Ogl.GL_STREAM_DRAW; 
        }
    }
    
    public static int getAttributeId(Attribute attrib) {
        switch (attrib) {
        default: 
            throw new OasisException("Unknown Attribute: " + attrib); 
        case POSITION: 
            return 0; 
        case NORMAL: 
            return 1; 
        case COLOR: 
            return 2; 
        case TEX_COORD: 
            return 3; 
        }
    }
    
    public static String getAttributeName(Attribute attrib) {
        switch (attrib) {
        default: 
            throw new OasisException("Unknown Attribute: " + attrib); 
        case POSITION: 
            return "a_Position"; 
        case NORMAL: 
            return "a_Normal"; 
        case COLOR: 
            return "a_Color"; 
        case TEX_COORD: 
            return "a_TexCoord"; 
        }
    }
    
    public static int getPrimitive(Geometry.Primitive prim) {
        switch (prim) {
        default: 
            throw new OasisException("Unknown Primitive: " + prim); 
        case TRIANGLE_LIST: 
            return Ogl.GL_TRIANGLES; 
        case TRIANGLE_STRIP: 
            return Ogl.GL_TRIANGLE_STRIP; 
        case LINE_LIST: 
            return Ogl.GL_LINES; 
        case LINE_STRIP: 
            return Ogl.GL_LINE_STRIP; 
        case POINT_LIST: 
            return Ogl.GL_POINTS; 
        }
    }

    public static Uniform.Type getUniformType(int type) {
        switch (type) {
        default: 
            return Uniform.Type.UNKNOWN; 
        case Ogl.GL_INT: 
            return Uniform.Type.INT; 
        case Ogl.GL_FLOAT: 
            return Uniform.Type.FLOAT; 
        case Ogl.GL_FLOAT_VEC2: 
            return Uniform.Type.VECTOR2; 
        case Ogl.GL_FLOAT_VEC3: 
            return Uniform.Type.VECTOR3; 
        case Ogl.GL_FLOAT_VEC4: 
            return Uniform.Type.VECTOR4; 
        case Ogl.GL_FLOAT_MAT3: 
            return Uniform.Type.MATRIX3; 
        case Ogl.GL_FLOAT_MAT4: 
            return Uniform.Type.MATRIX4; 
        }
    }

    public static int getBlendMode(BlendMode mode) {
        switch (mode) {
        default: 
            throw new OasisException("Unknown BlendMode: " + mode); 
        case ONE: 
            return Ogl.GL_ONE; 
        case ZERO: 
            return Ogl.GL_ZERO; 
        case ONE_MINUS_SRC_ALPHA: 
            return Ogl.GL_ONE_MINUS_SRC_ALPHA; 
        case SRC_ALPHA: 
            return Ogl.GL_SRC_ALPHA; 
        }
    }

    public static int getFrontFace(FrontFace face) {
        switch (face) {
        default: 
            throw new OasisException("Unknown FrontFace: " + face); 
        case BOTH: 
        case CCW: 
            return Ogl.GL_CCW; 
        case CW: 
            return Ogl.GL_CW; 
        }
    }

    public static int getFillMode(FillMode mode) {
        switch (mode) {
        default: 
            throw new OasisException("Unknown FillMode: " + mode); 
        case FILL: 
            return Ogl.GL_FILL; 
        case LINE: 
            return Ogl.GL_LINE; 
        case POINT: 
            return Ogl.GL_POINT; 
        }
    }
    
}
