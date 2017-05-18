package nhamil.oasis.graphics;

import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;
import nhamil.oasis.math.Vector4;

public class Vertex {

    public static final int TOTAL_FLOATS = 3 + 3 + 2 + 4;
    public static final int OFFSET_POSITION = 0;
    public static final int OFFSET_NORMAL = OFFSET_POSITION + 3;
    public static final int OFFSET_TEX_COORD = OFFSET_NORMAL + 3;
    public static final int OFFSET_COLOR = OFFSET_TEX_COORD + 2;
    
    private Vector3 position;
    private Vector3 normal;
    private Vector2 texCoord;
    private Vector4 color;
    
    public Vertex() {
        position = new Vector3();
        normal = new Vector3();
        texCoord = new Vector2();
        color = new Vector4();
    }
    
    public Vertex setPosition(Vector3 r) {
        position.set(r); 
        return this;
    }
    
    public Vertex setNormal(Vector3 r) {
        normal.set(r);
        return this;
    }
    
    public Vertex setTexCoord(Vector2 r) {
        texCoord.set(r);
        return this;
    }
    
    public Vertex setColor(Vector4 r) {
        color.set(r);
        return this;
    }
    
    public Vertex setColor(ColorRgba col) {
        color.set(col.toVector4());
        return this;
    }
    
    public Vector3 getPosition() { return position; }
    
    public Vector3 getNormal() { return normal; }
    
    public Vector2 getTexCoord() { return texCoord; }
    
    public Vector4 getColor() { return color; }
    
}
