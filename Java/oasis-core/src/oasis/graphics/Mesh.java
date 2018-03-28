package oasis.graphics;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import oasis.math.Vector2;
import oasis.math.Vector3;

/**
 * 
 * Holds vertex data 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Mesh {

    // WORK IN PROGRESS
    
    private static final int POSITION = 0; 
    private static final int NORMAL = 1; 
    private static final int TANGENT = 2; 
    private static final int TEXTURE = 3; 
    
    private native void nativeDispose(); 
    
    private native void nativeUpload(); 
    
    private native void nativeClear(); 
    
    private native void nativeUploadVertexBuffer(int attrib, FloatBuffer buffer); 
    
    private native void nativeSetSubmeshCount(int submeshes); 
    
    private native void nativeUploadIndexBuffer(int index, ShortBuffer buffer); 
    
    public void dispose() {
        nativeDispose(); 
    }
    
    public void upload() {
        nativeUpload(); 
    }
    
    public void clear() {
        nativeClear(); 
    }
    
    public native void calculateNormals(); 
    
    public native void calculateTangents(); 
    
    public native int getSubmeshCount(); 
    
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
