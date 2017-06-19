package oasis.graphics.vertex;

import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

// ignores index
public class BasicVertex implements Vertex {

    public Vector3 position; 
    public Vector3 normal; 
    public Vector4 color; 
    public Vector2 texture; 
    
    @Override
    public boolean hasElement(VertexElement elem) {
        switch (elem.getAttribute()) { 
        case POSITION: return elem.getComponentCount() <= 3; 
        case NORMAL: return elem.getComponentCount() <= 3; 
        case COLOR: return elem.getComponentCount() <= 4; 
        case TEXTURE: return elem.getComponentCount() <= 2;
        default: return false;  
        }
    }

    @Override
    public void getFloats(VertexElement elem, float[] out) {
        switch (elem.getAttribute()) { 
        case POSITION: 
            out[0] = position.getX(); 
            out[1] = position.getY(); 
            out[2] = position.getZ(); 
            break; 
        case NORMAL: 
            out[0] = normal.getX(); 
            out[1] = normal.getY(); 
            out[2] = normal.getZ(); 
            break; 
        case COLOR: 
            out[0] = color.getX(); 
            out[1] = color.getY(); 
            out[2] = color.getZ(); 
            out[3] = color.getW(); 
            break; 
        case TEXTURE: 
            out[0] = texture.getX(); 
            out[1] = texture.getY(); 
            break;
        default: 
            break; 
        }
    }
    
    public static void calculateNormals(BasicVertex[] verts) { 
        for (int i = 0; i < verts.length; i += 3) { 
            Vector3 a = verts[i + 1].position.subtract(verts[i + 0].position); 
            Vector3 b = verts[i + 2].position.subtract(verts[i + 0].position); 
            Vector3 normal = a.cross(b).normalize(); 
            verts[i + 0].normal = normal; 
            verts[i + 1].normal = normal; 
            verts[i + 2].normal = normal; 
        }
    }

}
