package oasis.graphics;

import oasis.core.Oasis;
import oasis.math.Vector2;
import oasis.math.Vector3;

public abstract class Mesh {

    public static Mesh create() {
        return Oasis.getGraphicsDevice().createMesh(); 
    }
    
    public abstract void dispose(); 
    
    public abstract void upload(); 
    
    public abstract void clear(); 
    
    public abstract void calculateNormals(); 
    
    public abstract void calculateTangents(); 
    
    public abstract int getSubmeshCount(); 
    
    public abstract short[] getIndices(int submesh); 
    
    public abstract Primitive getPrimitive(int submesh); 
    
    public abstract Vector3[] getPositions(); 
    
    public abstract Vector3[] getNormals(); 
    
    public abstract Vector2[] getTexCoords(); 
    
    public abstract Vector3[] getTangents(); 
    
    public abstract void setSubmeshCount(int count); 
    
    public abstract void setIndices(int submesh, short[] data); 
    
    public abstract void setPrimitive(int submesh, Primitive prim); 
    
    public abstract void setPositions(Vector3[] positions); 
    
    public abstract void setNormals(Vector3[] normals); 
    
    public abstract void setTexCoords(Vector2[] texCoords); 
    
    public abstract void setTangents(Vector3[] tangents);

}
