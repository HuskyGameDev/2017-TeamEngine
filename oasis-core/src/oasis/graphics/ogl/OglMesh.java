package oasis.graphics.ogl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.graphics.Attribute;
import oasis.graphics.Mesh;
import oasis.graphics.Primitive;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.util.ArrayUtil;

// TODO more than one submesh 
public class OglMesh extends Mesh {

    private static final Logger log = new Logger(OglMesh.class); 
    
    private Ogl ogl; 
    
    private int[] vbo = new int[1]; 
    private int[] ibo = new int[1]; 
    
    private float[] positions; 
    private float[] normals; 
    private float[] texCoords; 
    private float[] tangents; 
    
    private short[] indices; 
    
    private boolean updateVertices = true; 
    private boolean updateIndices = true; 
    
    private boolean blankVertices = true;
    private boolean blankIndices = true; 
    private FloatBuffer vertexBuffer; 
    private ShortBuffer indexBuffer; 
    
    public OglMesh(Ogl ogl) {
        this.ogl = ogl; 
    }

    private void checkForNewPosition(int verts) {
        if (indices != null) checkInNewRange(indices, verts); 
        
        if (normals != null && normals.length / 3 != verts ||
            texCoords != null && texCoords.length / 2 != verts || 
            tangents != null && texCoords.length / 3 != verts) {
            throw new OasisException("Positions can only be reset with other attributes if it has the same number of vertices"); 
        }
    }
    
    private void checkForPosition(int verts) {
        if (positions == null) {
            throw new OasisException("Positions must be set before other attributes"); 
        }
        
        if (verts != positions.length / 3) {
            throw new OasisException("All attributes must have the same number of vertices: " + verts + " (should be " + positions.length / 3 + ")"); 
        }
    }
    
    private void checkInRange(short[] indices) {
        if (positions == null) {
            throw new OasisException("Positions must be set before indices can be set"); 
        }
        
        int max = positions.length / 3 - 1; 
        
        for (short s : indices) {
            if (s > max) {
                throw new OasisException("Index cannot be larger than the number of indices: " + s + " (max is " + max + ")"); 
            }
        }
    }
    
    private void checkInNewRange(short[] indices, int max) {
        for (short s : indices) {
            if (s > max) {
                throw new OasisException("Index cannot be larger than the number of indices: " + s + " (max is " + max + ")"); 
            }
        }
    }
    
    private void validate() {
        if (vbo[0] == 0) {
            ogl.glGenBuffers(1, vbo, 0); 
        }
        
        if (ibo[0] == 0) {
            ogl.glGenBuffers(1, ibo, 0); 
        }
    }
    
    public void draw(int submesh) {
        validate(); 
        uploadIfNeeded(); 
        
        if (blankVertices) {
            log.warning("Cannot draw mesh, there are no vertices");
            return; 
        }
        if (blankIndices) {
            log.warning("Cannot draw mesh, there are no indices");
            return; 
        }
        
        setupVertexAttribArrays(); 
        
        ogl.glBindBuffer(Ogl.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
        ogl.glDrawElements(Ogl.GL_TRIANGLES, indices.length, Ogl.GL_UNSIGNED_SHORT, 0);
    }
    
    private void setupVertexAttribArrays() {
        int offset = 0; 
        
        int floatsPerElement = 3; 
        
        if (normals != null) floatsPerElement += 3;
        if (texCoords != null) floatsPerElement += 2; 
        if (tangents != null) floatsPerElement += 3; 
        
        ogl.glBindBuffer(Ogl.GL_ARRAY_BUFFER, vbo[0]);
        
        ogl.glEnableVertexAttribArray(Attribute.POSITION.ordinal()); 
        ogl.glVertexAttribPointer(Attribute.POSITION.ordinal(), 3, Ogl.GL_FLOAT, false, floatsPerElement * 4, offset * 4);
        offset += 3; 
        
        if (normals == null) {
            ogl.glDisableVertexAttribArray(Attribute.NORMAL.ordinal());
        }
        else {
            ogl.glEnableVertexAttribArray(Attribute.NORMAL.ordinal()); 
            ogl.glVertexAttribPointer(Attribute.NORMAL.ordinal(), 3, Ogl.GL_FLOAT, false, floatsPerElement * 4, offset * 4);
            offset += 3; 
        }
        
        if (texCoords == null) {
            ogl.glDisableVertexAttribArray(Attribute.TEX_COORD.ordinal());
        }
        else {
            ogl.glEnableVertexAttribArray(Attribute.TEX_COORD.ordinal()); 
            ogl.glVertexAttribPointer(Attribute.TEX_COORD.ordinal(), 2, Ogl.GL_FLOAT, false, floatsPerElement * 4, offset * 4);
            offset += 2; 
        }
        
        if (tangents == null) {
            ogl.glDisableVertexAttribArray(Attribute.TANGENT.ordinal());
        }
        else {
            ogl.glEnableVertexAttribArray(Attribute.TANGENT.ordinal()); 
            ogl.glVertexAttribPointer(Attribute.TANGENT.ordinal(), 3, Ogl.GL_FLOAT, false, floatsPerElement * 4, offset * 4);
            offset += 3; 
        }
    }
    
    @Override
    public void upload() {
        validate(); 
        uploadIfNeeded(); 
    }
    
    private void setMinVbSize(int floats) {
        if (vertexBuffer == null || vertexBuffer.capacity() < floats) {
            if (vertexBuffer != null) Oasis.getDirectBufferAllocator().free(vertexBuffer); 
            
            vertexBuffer = Oasis.getDirectBufferAllocator().allocate(floats * 4).asFloatBuffer(); 
        }
    }
    
    private void setMinIbSize(int shorts) {
        if (indexBuffer == null || indexBuffer.capacity() < shorts) {
            if (indexBuffer != null) Oasis.getDirectBufferAllocator().free(indexBuffer); 
            
            indexBuffer = Oasis.getDirectBufferAllocator().allocate(shorts * 2).asShortBuffer(); 
        }
    }
    
    private void uploadIfNeeded() {
        if (updateVertices) {
            blankVertices = positions == null; 
            
            if (blankVertices) {
                // do nothing 
            }
            else {
                int floatsPerElement = 3; 
                int vertices = positions.length / 3; 
                
                if (normals != null) floatsPerElement += 3;
                if (texCoords != null) floatsPerElement += 2; 
                if (tangents != null) floatsPerElement += 3; 
                
                setMinVbSize(vertices * floatsPerElement); 
                
                vertexBuffer.position(0); 
                for (int i = 0; i < vertices; i++) {
                    int i2 = i * 2; 
                    int i3 = i * 3; 
                    
                    vertexBuffer.put(positions, i3, 3); 
                    if (normals != null) vertexBuffer.put(normals, i3, 3); 
                    if (texCoords != null) vertexBuffer.put(texCoords, i2, 2); 
                    if (tangents != null) vertexBuffer.put(tangents, i3, 3); 
                }
                vertexBuffer.flip(); 
                ogl.glBindBuffer(Ogl.GL_ARRAY_BUFFER, vbo[0]);
                ogl.glBufferData(Ogl.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, Ogl.GL_DYNAMIC_DRAW); 
            }
            
            updateVertices = false; 
        }
        
        if (updateIndices) {
            blankIndices = indices == null; 
            
            if (blankIndices) {
                // do nothing 
            }
            else {
                setMinIbSize(indices.length); 
                
                indexBuffer.position(0); 
                indexBuffer.put(indices); 
                indexBuffer.flip(); 
                ogl.glBindBuffer(Ogl.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
                ogl.glBufferData(Ogl.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * 2, indexBuffer, Ogl.GL_DYNAMIC_DRAW); 
            }
            
            updateIndices = false; 
        }
    }
    
    @Override
    public void clear() {
        positions = null; 
        normals = null; 
        texCoords = null; 
        tangents = null; 
    }

    @Override
    public void calculateNormals() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void calculateTangents() {
        // make sure we can calculate tangents 
        if (positions == null || texCoords == null || normals == null) {
            throw new OasisException("Position, normal, and texture coordinates must be set before tangents can be calculated!"); 
        }
        if (indices == null) {
            throw new OasisException("Index data must be set before tangents can be calculated!"); 
        }
        
        int vertCount = positions.length / 3; 
        
        float[] tan1 = new float[3 * vertCount]; 
        float[] tan2 = new float[3 * vertCount]; 
        
        Vector3 v1 = new Vector3(); 
        Vector3 v2 = new Vector3(); 
        Vector3 v3 = new Vector3(); 
        
        Vector2 w1 = new Vector2(); 
        Vector2 w2 = new Vector2();
        Vector2 w3 = new Vector2();
        
        Vector3 sDir = new Vector3(); 
        Vector3 tDir = new Vector3(); 
        
        for (int i = 0; i < indices.length; i += 3) {
            short i1 = indices[i + 0]; 
            short i2 = indices[i + 1]; 
            short i3 = indices[i + 2]; 
            
            v1.set(positions[i1 * 3 + 0], positions[i1 * 3 + 1], positions[i1 * 3 + 2]); 
            v2.set(positions[i2 * 3 + 0], positions[i2 * 3 + 1], positions[i2 * 3 + 2]); 
            v3.set(positions[i3 * 3 + 0], positions[i3 * 3 + 1], positions[i3 * 3 + 2]); 
            
            w1.set(texCoords[i1 * 2 + 0], texCoords[i1 * 2 + 1]); 
            w2.set(texCoords[i2 * 2 + 0], texCoords[i2 * 2 + 1]); 
            w3.set(texCoords[i3 * 2 + 0], texCoords[i3 * 2 + 1]); 
            
            float x1 = v2.x - v1.x; 
            float x2 = v3.x - v1.x; 
            float y1 = v2.y - v1.y; 
            float y2 = v3.y - v1.y; 
            float z1 = v2.z - v1.z; 
            float z2 = v3.z - v1.z; 
            
            float s1 = w2.x - w1.x; 
            float s2 = w3.x - w1.x; 
            float t1 = w2.y - w1.y; 
            float t2 = w3.y - w1.y; 
            
            float r = 1.0f / (s1 * t2 - s2 * t1); 
            
            sDir.set((t2 * x1 - t1 * x2) * r, (t2 * y1 - t1 * y2) * r, (t2 * z1 - t1 * z2) * r); 
            tDir.set((s1 * x2 - s2 * x1) * r, (s1 * y2 - s2 * y1) * r, (s1 * z2 - s2 * z1) * r); 
            
            tan1[i1 * 3 + 0] += sDir.x; 
            tan1[i1 * 3 + 1] += sDir.y; 
            tan1[i1 * 3 + 2] += sDir.z; 
            tan1[i2 * 3 + 0] += sDir.x; 
            tan1[i2 * 3 + 1] += sDir.y; 
            tan1[i2 * 3 + 2] += sDir.z; 
            tan1[i3 * 3 + 0] += sDir.x; 
            tan1[i3 * 3 + 1] += sDir.y; 
            tan1[i3 * 3 + 2] += sDir.z; 
            
            tan2[i1 * 3 + 0] += tDir.x; 
            tan2[i1 * 3 + 1] += tDir.y; 
            tan2[i1 * 3 + 2] += tDir.z; 
            tan2[i2 * 3 + 0] += tDir.x; 
            tan2[i2 * 3 + 1] += tDir.y; 
            tan2[i2 * 3 + 2] += tDir.z; 
            tan2[i3 * 3 + 0] += tDir.x; 
            tan2[i3 * 3 + 1] += tDir.y; 
            tan2[i3 * 3 + 2] += tDir.z; 
        }
        
        tangents = tan1; 
        updateVertices = true; 
    }

    @Override
    public int getSubmeshCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public short[] getIndices(int submesh) {
        if (indices == null) {
            return null; 
        }
        else {
            return indices.clone(); 
        }
    }

    @Override
    public Primitive getPrimitive(int submesh) {
        return Primitive.TRIANGLE_LIST;
    }

    @Override
    public Vector3[] getPositions() {
        if (positions == null) {
            return null; 
        }
        else {
            return ArrayUtil.toVector3Array(positions); 
        }
    }

    @Override
    public Vector3[] getNormals() {
        if (normals == null) {
            return null; 
        }
        else {
            return ArrayUtil.toVector3Array(normals); 
        }
    }

    @Override
    public Vector2[] getTexCoords() {
        if (texCoords == null) {
            return null; 
        }
        else {
            return ArrayUtil.toVector2Array(texCoords); 
        }
    }

    @Override
    public Vector3[] getTangents() {
        if (tangents == null) {
            return null; 
        }
        else {
            return ArrayUtil.toVector3Array(tangents); 
        }
    }

    @Override
    public void setSubmeshCount(int count) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setIndices(int submesh, short[] data) {
        if (data == null) {
            this.indices = null; 
            updateIndices = true; 
        }
        
        checkInRange(data); 
        this.indices = data.clone(); 
        updateIndices = true; 
    }

    @Override
    public void setPrimitive(int submesh, Primitive prim) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPositions(Vector3[] positions) {
        if (positions == null) {
            checkForNewPosition(0); 
            this.positions = null; 
            updateVertices = true; 
        }
        
        checkForNewPosition(positions.length); 
        
        this.positions = ArrayUtil.toFloatArray(positions); 
        updateVertices = true; 
    }

    @Override
    public void setNormals(Vector3[] normals) {
        if (normals == null) {
            normals = null; 
            updateVertices = true; 
        }
        
        checkForPosition(normals.length);
        
        this.normals = ArrayUtil.toFloatArray(normals); 
        updateVertices = true; 
    }

    @Override
    public void setTexCoords(Vector2[] texCoords) {
        if (texCoords == null) {
            texCoords = null; 
            updateVertices = true; 
        }
        
        checkForPosition(texCoords.length);
        
        this.texCoords = ArrayUtil.toFloatArray(texCoords); 
        updateVertices = true; 
    }

    @Override
    public void setTangents(Vector3[] tangents) {
        if (tangents == null) {
            tangents = null; 
            updateVertices = true; 
        }
        
        checkForPosition(tangents.length);
        
        this.tangents = ArrayUtil.toFloatArray(tangents); 
        updateVertices = true; 
    }

}
