package oasis.cpp_graphics;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import oasis.core.Oasis;
import oasis.graphics.Attribute;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.util.ArrayUtil;

public class Mesh {

    private long ptr; 
    private FloatBuffer floatBuffer; 
    private ShortBuffer shortBuffer; 
    
    // TODO destructor 
    public Mesh() {
        ptr = nativeCreate(); 
    }
    
    private native long nativeCreate(); 
    
    private native void nativeDestroy(long ptr); 
    
    private native void nativeUpload(long ptr); 
    
    private native void nativeClear(long ptr); 
    
    private native boolean nativeHasAttribute(long ptr, int attrib); 
    
    private native void nativeGetAttribute(long ptr, int attrib, FloatBuffer buffer); 
    
    private native void nativeSetAttribute(long ptr, int attrib, FloatBuffer buffer); 
    
    private native int nativeGetVertexCount(long ptr); 
    
    private native int nativeGetSubmeshCount(long ptr); 
    
    private native int nativeGetSubmeshLength(long ptr, int index); 
    
    private native void nativeGetSubmesh(long ptr, int index, ShortBuffer buffer); 
    
    private native void nativeSetSubmesh(long ptr, int index, ShortBuffer buffer); 
    
    private native int nativeGetPrimitive(long ptr, int index); 
    
    private native void nativeSetPrimitive(long ptr, int index, int prim); 
    
    private native void nativeCalculateNormals(long ptr); 
    
    private native void nativeCalculateTangents(long ptr); 
    
    private void validate() {
        if (ptr == 0) ptr = nativeCreate(); 
    }
    
    private void validateShortBuffer(int inds) {
        if (shortBuffer != null) {
            if (shortBuffer.capacity() >= inds) {
                return; // capacity is fine 
            }
            else {
                Oasis.getDirectBufferAllocator().free(shortBuffer);
                shortBuffer = null; 
            }
        }
        
        Oasis.getDirectBufferAllocator().allocate(inds * 2).asShortBuffer(); 
    }
    
    private void validateFloatBuffer(int inds) {
        if (floatBuffer != null) {
            if (floatBuffer.capacity() >= inds) {
                return; // capacity is fine 
            }
            else {
                Oasis.getDirectBufferAllocator().free(floatBuffer);
                floatBuffer = null; 
            }
        }
        
        Oasis.getDirectBufferAllocator().allocate(inds * 4).asFloatBuffer(); 
    }
    
    public void dispose() {
        nativeDestroy(ptr); 
        ptr = 0; 
    }
    
    public void upload() {
        validate(); 
        nativeUpload(ptr); 
    }
    
    public void clear() {
        validate(); 
        nativeClear(ptr); 
    }
    
    public void calculateNormals() {
        validate(); 
        nativeCalculateNormals(ptr); 
    }
    
    public void calculateTangents() {
        validate(); 
        nativeCalculateTangents(ptr); 
    }
    
    public int getSubmeshCount() {
        validate(); 
        return nativeGetSubmeshCount(ptr); 
    }
    
    public short[] getIndices(int submesh) {
        validate(); 
        
        int length = nativeGetSubmeshLength(ptr, submesh); 
        
        validateShortBuffer(length); 
        
        shortBuffer.position(length); 
        shortBuffer.flip(); 
        
        nativeGetSubmesh(ptr, submesh, shortBuffer); 
        
        short[] out = new short[length]; 
        shortBuffer.get(out); 
        
        return out; 
    }
    
    private Vector3[] getAttribute3(Attribute attrib) {
        validate(); 
        
        int length = 3 * nativeGetVertexCount(ptr); 
        
        validateFloatBuffer(length); 
        
        floatBuffer.position(length); 
        floatBuffer.flip(); 
        
        nativeGetAttribute(ptr, attrib.ordinal(), floatBuffer); 
        
        float[] out = new float[length]; 
        floatBuffer.get(out); 
        
        return ArrayUtil.toVector3Array(out); 
    }
    
    public Vector3[] getPositions() {
        return getAttribute3(Attribute.POSITION); 
    }
    
    public Vector3[] getNormals() {
        return getAttribute3(Attribute.NORMAL); 
    }
    
    public Vector2[] getTexCoords() {
        validate(); 
        
        int length = 2 * nativeGetVertexCount(ptr); 
        
        validateFloatBuffer(length); 
        
        floatBuffer.position(length); 
        floatBuffer.flip(); 
        
        nativeGetAttribute(ptr, Attribute.TEX_COORD.ordinal(), floatBuffer); 
        
        float[] out = new float[length]; 
        floatBuffer.get(out); 
        
        return ArrayUtil.toVector2Array(out); 
    }
    
    public Vector3[] getTangents() {
        return getAttribute3(Attribute.TANGENT); 
    }
    
    public void setSubmeshCount(int count) {
        validate(); 
        nativeSetSubmesh(count, index, buffer);
    }
    
}
