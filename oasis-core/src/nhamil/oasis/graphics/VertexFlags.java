package nhamil.oasis.graphics;

// TODO REMOVE ???

public class VertexFlags {

    public static final int POSITION = 0x0001;
    public static final int COLOR = 0x0002;
    public static final int NORMAL = 0x0004;
    public static final int TEXCOORD = 0x0008;
    public static final int FLOAT_1 = 0x0010;
    public static final int FLOAT_2 = 0x0020;
    public static final int VEC2_1 = 0x0040;
    public static final int VEC2_2 = 0x0080;
    public static final int VEC3_1 = 0x0100;
    public static final int VEC3_2 = 0x0200;
    public static final int VEC4_1 = 0x0400;
    public static final int VEC4_2 = 0x0800;
    
    public static final int POSITION_COLOR = POSITION | COLOR;
    public static final int POSITION_TEXCOORD = POSITION | TEXCOORD;
    public static final int POSITION_COLOR_TEXCOORD = POSITION | COLOR | TEXCOORD;
    public static final int POSITION_COLOR_NORMAL_TEXCOORD = POSITION | COLOR | NORMAL | TEXCOORD;
    
}
