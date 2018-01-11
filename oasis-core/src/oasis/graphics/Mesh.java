package oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Logger;
import oasis.core.OasisException;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;
import oasis.util.ArrayUtil;

public class Mesh {

    private static final Logger log = new Logger(Mesh.class); 
    
    private class Submesh {
        
        private Geometry geometry; 
        private IndexBuffer indices; 
        
        public Submesh() {
            indices = new IndexBuffer(0, BufferUsage.DYNAMIC); 
            geometry = new Geometry(); 
            geometry.setIndexBuffer(indices); 
        }
        
    }
    
    private Submesh[] submeshes; 
    private VertexBuffer[] vertexBuffers = new VertexBuffer[Attribute.values().length]; 
    
    public Mesh() {
        submeshes = new Submesh[1];
        submeshes[0] = new Submesh(); 
    }
    
    protected static final VertexFormat getVertexFormat(Attribute attrib) {
        switch (attrib) {
        default: 
            throw new OasisException("Unknown attribute: " + attrib); 
        case POSITION: 
            return VertexFormat.POSITION; 
        case NORMAL: 
            return VertexFormat.NORMAL; 
        case COLOR: 
            return VertexFormat.COLOR; 
        case TEX_COORD: 
            return VertexFormat.TEX_COORD; 
        case TANGENT: 
            return VertexFormat.TANGENT; 
        }
    }
    
    VertexBuffer[] getVertexBuffers() {
        List<VertexBuffer> buffers = new ArrayList<>(); 
        
        for (int i = 0; i < vertexBuffers.length; i++) {
            if (vertexBuffers[i] != null) buffers.add(vertexBuffers[i]); 
        }
        
        return buffers.toArray(new VertexBuffer[buffers.size()]); 
    }
    
    public void upload() {
        VertexBuffer[] vbs = getVertexBuffers(); 
        
        for (int i = 0; i < vbs.length; i++) {
            vbs[i].upload(); 
        }
        
        for (int i = 0; i < submeshes.length; i++) {
            submeshes[i].indices.upload(); 
            submeshes[i].geometry.setVertexBuffers(vbs); 
            submeshes[i].geometry.upload(); 
        }
    }
    
    public Geometry getGeometry(int submesh) {
        return submeshes[submesh].geometry; 
    }
    
    public int getSubmeshCount() {
        return submeshes.length; 
    }
    
    public void setPrimitive(int submesh, Primitive prim) {
        submeshes[submesh].geometry.setPrimitive(prim); 
    }
    
    public void setIndices(int submesh, short[] data) {
        IndexBuffer ib = submeshes[submesh].indices; 
        
        int newSize = data.length; 
        
        if (ib.getIndexCount() != newSize) {
            ib.resize(newSize); 
        }
        
        ib.setIndices(0, newSize, data, 0);
    }
    
    private void setVertices(Attribute attrib, float[] verts) {
        if (verts == null || verts.length == 0) {
            if (vertexBuffers[attrib.ordinal()] != null) {
                vertexBuffers[attrib.ordinal()].release(); 
                vertexBuffers[attrib.ordinal()] = null; 
            }
        }
        else {
            VertexBuffer vb = vertexBuffers[attrib.ordinal()]; 
            VertexFormat format = getVertexFormat(attrib); 
            int vertCount = verts.length / format.getFloatsPerElement(); 
            
            if (vb == null) {
                vb = new VertexBuffer(format, vertCount, BufferUsage.DYNAMIC); 
            }
            
            if (vb.getVertexCount() != vertCount) {
                vb.resize(vertCount); 
            }
            
            vb.setVertices(0, vertCount, verts, 0); 
            
            vertexBuffers[attrib.ordinal()] = vb; 
        }
    }
    
    public int getVertexCount() {
        VertexBuffer vb = vertexBuffers[Attribute.POSITION.ordinal()]; 
        
        return vb == null ? 0 : vb.getVertexCount(); 
    }
    
    public void setPositions(Vector3[] positions) {
        setVertices(Attribute.POSITION, ArrayUtil.toFloatArray(positions)); 
    }
    
    public void setNormals(Vector3[] normals) {
        setVertices(Attribute.NORMAL, ArrayUtil.toFloatArray(normals)); 
    }
    
    public void setColors(Vector4[] colors) {
        setVertices(Attribute.COLOR, ArrayUtil.toFloatArray(colors)); 
    }
    
    public void setTexCoords(Vector2[] texCoords) {
        setVertices(Attribute.TEX_COORD, ArrayUtil.toFloatArray(texCoords)); 
    }
    
    public void setTangents(Vector3[] tangents) {
        setVertices(Attribute.TANGENT, ArrayUtil.toFloatArray(tangents)); 
    }
    
    // should be called after all submesh indices has been applied
    // reference: http://www.terathon.com/code/tangent.html 
    // reference: http://www.opengl-tutorial.org/intermediate-tutorials/tutorial-13-normal-mapping/ 
    // TODO apply for all submeshes, not just index 0
    // TODO check primitive type, right now this applies only to triangle lists
    public void calculateTangents() {
        VertexBuffer posVb = vertexBuffers[Attribute.POSITION.ordinal()];
        VertexBuffer normalVb = vertexBuffers[Attribute.NORMAL.ordinal()];
        VertexBuffer texCoordVb = vertexBuffers[Attribute.TEX_COORD.ordinal()];
        
        if (getSubmeshCount() < 1) {
            throw new OasisException("Triangle data must be set before tangents can be calculated!"); 
        }
        
        IndexBuffer ib = submeshes[0].indices; 
        
        // make sure we can calculate tangents 
        if (normalVb == null || texCoordVb == null || ib == null) {
            throw new OasisException(" texture coordinates must be set before tangents can be calculated!"); 
        }
        
        int vertCount = getVertexCount(); 
        int indCount = ib.getIndexCount(); 
        
        float[] positions = new float[3 * vertCount]; 
        float[] texCoords = new float[2 * vertCount]; 
        short[] triangles = new short[indCount]; 
        
        float[] tan1 = new float[3 * vertCount]; 
        float[] tan2 = new float[3 * vertCount]; 
        
        posVb.getVertices(0, vertCount, positions, 0); 
        texCoordVb.getVertices(0, vertCount, texCoords, 0); 
        ib.getIndices(0, indCount, triangles, 0); 
        
        Vector3 v1 = new Vector3(); 
        Vector3 v2 = new Vector3(); 
        Vector3 v3 = new Vector3(); 
        
        Vector2 w1 = new Vector2(); 
        Vector2 w2 = new Vector2();
        Vector2 w3 = new Vector2();
        
        Vector3 sDir = new Vector3(); 
        Vector3 tDir = new Vector3(); 
        
        for (int i = 0; i < triangles.length; i += 3) {
            short i1 = triangles[i + 0]; 
            short i2 = triangles[i + 1]; 
            short i3 = triangles[i + 2]; 
            
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
        
        setVertices(Attribute.TANGENT, tan1); 
    }
    
    public Primitive getPrimitive(int submesh) {
        return submeshes[submesh].geometry.getPrimitive(); 
    }
    
    public short[] getIndices(int submesh) {
        IndexBuffer ib = submeshes[submesh].indices; 
        short[] out = new short[ib.getSizeInShorts()]; 
        ib.getIndices(0, ib.getIndexCount(), out, 0);
        return out; 
    }
    
    public Vector3[] getPositions() {
        log.warning("getPositions() not supported yet"); 
        return null; 
    }
    
    public Vector3[] getNormals() {
        log.warning("getNormals() not supported yet"); 
        return null; 
    }
    
    public Vector3[] getTangents() {
        log.warning("getTangents() not supported yet"); 
        return null; 
    }
    
    public Vector4[] getColors() {
        log.warning("getColors() not supported yet"); 
        return null; 
    }
    
    public Vector2[] getTexCoords() {
        log.warning("getTexCoords() not supported yet"); 
        return null; 
    }
    
}