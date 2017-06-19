package oasis.graphics;

import oasis.math.Vector3;
import oasis.math.Vector4;

public class Mesh {

    private Primitive primitive = Primitive.TRIANGLE_LIST; 
    private int[] indices; 
    private Vector3[] positions; 
    private Vector3[] normals; 
    private Vector4[] colors; 
    // TODO bone weights, bone indices, multiple texture coordinates, tangents 
    
    public Mesh() {
    }
    
    // getters 
    
    public Primitive getPrimitive() { 
        return primitive; 
    }
    
    public int[] getIndices() { 
        return indices; 
    }
    
    public Vector3[] getPositions() { 
        return positions; 
    }
    
    public Vector3[] getNormals() { 
        return normals; 
    }
    
    public Vector4[] getColors() { 
        return colors; 
    }
    
    // setters 
    
    public void setPrimitive(Primitive type) { 
        primitive = type; 
    }
    
    public void setIndices(int[] indices) { 
        this.indices = indices; 
    }
    
    public void setPositions(Vector3[] positions) { 
        this.positions = positions; 
    }
    
    public void setNormals(Vector3[] normals) { 
        this.normals = normals; 
    }
    
    public void setColors(Vector4[] colors) { 
        this.colors = colors; 
    }

}
