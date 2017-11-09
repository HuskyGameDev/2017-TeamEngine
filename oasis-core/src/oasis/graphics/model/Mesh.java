package oasis.graphics.model;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Oasis;
import oasis.graphics.BufferUsage;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;
import oasis.math.Vector3f;
import oasis.math.Vector4f;
import oasis.util.ArrayUtil;

/**
 * Basic class for managing 3D geometry. 
 * Meshes should only be modified on the main 
 * game thread 
 * 
 * @author Nicholas Hamilton
 *
 */
public class Mesh {
    
    private VertexArray vao; 
    private IndexBuffer ibo; 
    private VertexBuffer positionVbo; 
    private VertexBuffer normalVbo; 
    private VertexBuffer colorVbo; 
    private VertexBuffer textureVbo; 
    
    private boolean update; 
    
    /**
     * Constructor. Needs a graphics device to manage 
     * buffers 
     * 
     * @param gd Graphics device 
     */
    public Mesh() {
        this.vao = Oasis.graphics.createVertexArray(); 
    }
    
    /**
     * Draws the mesh. 
     */
    public void draw() {
        if (update) {
            updateVao(); 
        }
        
        if (vao.getVertexBufferCount() == 0) return; 
        
        Oasis.graphics.setVertexArray(vao);
        if (vao.getIndexBuffer() == null) {
            Oasis.graphics.drawArrays(Primitive.TRIANGLE_LIST, 0, vao.getVertexBuffer(0).getVertexCount());
        }
        else {
            Oasis.graphics.drawElements(Primitive.TRIANGLE_LIST, 0, vao.getIndexBuffer().getIndexCount());
        }
    }
    
    /**
     * Assign indices for the mesh
     * 
     * @param inds Indices 
     */
    public void setIndices(int[] inds) {
        if (inds == null) {
            if (ibo != null) {
                ibo.dispose(); 
            }
            ibo = null; 
        }
        else {
            if (ibo == null) {
                ibo = Oasis.graphics.createIndexBuffer(BufferUsage.DYNAMIC); 
            }
            
            ibo.setData(inds);
        }
        
        update = true; 
    }
    
    /**
     * Assign position data for vertices 
     * 
     * @param positions Positions 
     */
    public void setPositions(Vector3f[] positions) {
        positionVbo = setVertexData(positionVbo, VertexFormat.POSITION_3, positions == null ? null : ArrayUtil.toFloatArray(positions)); 
    }
    
    /**
     * Assign normal data for vertices 
     * 
     * @param normals Normals 
     */
    public void setNormals(Vector3f[] normals) {
        normalVbo = setVertexData(normalVbo, VertexFormat.NORMAL_3, normals == null ? null : ArrayUtil.toFloatArray(normals)); 
    }
    
    /**
     * Assign color data for vertices 
     * 
     * @param colors Colors 
     */
    public void setColors(Vector4f[] colors) {
        colorVbo = setVertexData(colorVbo, VertexFormat.COLOR_4, colors == null ? null : ArrayUtil.toFloatArray(colors)); 
    }
    
    // set vertex data for a vertex buffer 
    private VertexBuffer setVertexData(VertexBuffer vbo, VertexFormat fmt, float[] data) {
        if (data == null) {
            if (vbo != null) {
                vbo.dispose(); 
            }
            return null; 
        }
        else {
            if (vbo == null) {
                vbo = Oasis.graphics.createVertexBuffer(fmt, BufferUsage.DYNAMIC); 
            }
            
            vbo.setVertices(data);
        }
        
        update = true; 
        return vbo; 
    }
    
    private void updateVao() {
        List<VertexBuffer> vbos = new ArrayList<>(); 
        tryAddVbo(positionVbo, vbos); 
        tryAddVbo(normalVbo, vbos); 
        tryAddVbo(colorVbo, vbos); 
        tryAddVbo(textureVbo, vbos); 
        
        vao.setVertexBuffers(vbos.toArray(new VertexBuffer[vbos.size()]));
        vao.setIndexBuffer(ibo); 
        
        update = false; 
    }
    
    private static void tryAddVbo(VertexBuffer vbo, List<VertexBuffer> out) {
        if (vbo != null) out.add(vbo); 
    }
    
}
