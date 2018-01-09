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
    
    public Vector4[] getColors() {
        log.warning("getColors() not supported yet"); 
        return null; 
    }
    
    public Vector2[] getTexCoords() {
        log.warning("getTexCoords() not supported yet"); 
        return null; 
    }
    
}