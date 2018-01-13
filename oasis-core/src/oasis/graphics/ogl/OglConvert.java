package oasis.graphics.ogl;

import oasis.core.OasisException;
import oasis.graphics.Attribute;
import oasis.graphics.BlendMode;
import oasis.graphics.BufferUsage;
import oasis.graphics.FillMode;
import oasis.graphics.FrontFace;
import oasis.graphics.Primitive;
import oasis.graphics.Texture.Format;
import oasis.graphics.Texture.MagFilter;
import oasis.graphics.Texture.MinFilter;
import oasis.graphics.Texture.WrapMode;

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
        case TANGENT: 
            return 4; 
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
        case TANGENT: 
            return "a_Tangent"; 
        }
    }
    
    public static int getPrimitive(Primitive prim) {
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

    public static OglUniformType getOglUniformType(int type) {
        switch (type) {
        default: 
            return OglUniformType.UNKNOWN; 
        case Ogl.GL_SAMPLER_2D: 
            return OglUniformType.TEXTURE_2D; 
        case Ogl.GL_INT: 
            return OglUniformType.INT; 
        case Ogl.GL_FLOAT: 
            return OglUniformType.FLOAT; 
        case Ogl.GL_FLOAT_VEC2: 
            return OglUniformType.VECTOR2; 
        case Ogl.GL_FLOAT_VEC3: 
            return OglUniformType.VECTOR3; 
        case Ogl.GL_FLOAT_VEC4: 
            return OglUniformType.VECTOR4; 
        case Ogl.GL_FLOAT_MAT3: 
            return OglUniformType.MATRIX3; 
        case Ogl.GL_FLOAT_MAT4: 
            return OglUniformType.MATRIX4; 
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

    public static int getTextureFormat(Format format) {
        switch (format) {
        default: 
            throw new OasisException("Unknown Texture Format: " + format); 
        case RGBA8: 
            return Ogl.GL_RGBA8; 
        }
    }

    public static int getWrapMode(WrapMode mode) {
        switch (mode) {
        default: 
            throw new OasisException("Unknown WrapMode: " + mode); 
        case CLAMP_TO_EDGE: 
            return Ogl.GL_CLAMP_TO_EDGE; 
        case REPEAT: 
            return Ogl.GL_REPEAT; 
        }
    }

    public static int getMinFilter(MinFilter min) {
        switch (min) {
        default: 
            throw new OasisException("Unknown MinFilter: " + min); 
        case NEAREST: 
            return Ogl.GL_NEAREST; 
        case NEAREST_MIPMAP_NEAREST: 
            return Ogl.GL_NEAREST_MIPMAP_NEAREST; 
        case NEAREST_MIPMAP_LINEAR: 
            return Ogl.GL_NEAREST_MIPMAP_LINEAR; 
        case LINEAR: 
            return Ogl.GL_LINEAR; 
        case LINEAR_MIPMAP_NEAREST: 
            return Ogl.GL_LINEAR_MIPMAP_NEAREST; 
        case LINEAR_MIPMAP_LINEAR: 
            return Ogl.GL_LINEAR_MIPMAP_LINEAR; 
        }
    }
    
    public static int getMagFilter(MagFilter min) {
        switch (min) {
        default: 
            throw new OasisException("Unknown MinFilter: " + min); 
        case NEAREST: 
            return Ogl.GL_NEAREST; 
        case LINEAR: 
            return Ogl.GL_LINEAR; 
        }
    }
    
}
