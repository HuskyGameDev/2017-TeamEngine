package oasis.graphics.vertex;

import oasis.graphics.Primitive;
import oasis.math.Vector3;
import oasis.math.Vector4;

public interface Mesh {

    // getters
    
    Primitive getPrimitive(); 
    
    BufferUsage getUsage(); 
    
    int[] getIndices(); 
    Vector3[] getPositions(); 
    Vector3[] getNormals(); 
    Vector4[] getColors(); 
    
    // setters
    
    void setPrimitive(Primitive primitive); 
    
    void setUsage(BufferUsage usage); 
    
    void setIndices(int[] indices); 
    void setPositions(Vector3[] positions); 
    void setNormals(Vector3[] normals); 
    void setColors(Vector4[] colors); 
    
}
