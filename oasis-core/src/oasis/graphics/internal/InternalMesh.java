package oasis.graphics.internal;

import oasis.math.Vector2;
import oasis.math.Vector3;

public interface InternalMesh {

    void clear(); 
    
    void upload(); 
    
    boolean hasPositions(); 
    
    boolean hasNormals(); 
    
    boolean hasTexCoords(); 
    
    boolean hasTangents(); 
    
    Vector3[] getPositions(); 
    
    Vector3[] getNormals(); 
    
    Vector2[] getTexCoords(); 
    
    Vector3[] getTangents(); 

    void setPositions(Vector3[] positions); 
    
    void setNormals(Vector3[] normals); 
    
    void setTexCoords(Vector2[] texCoords); 
    
    void setTangents(Vector3[] tangents); 
    
}
