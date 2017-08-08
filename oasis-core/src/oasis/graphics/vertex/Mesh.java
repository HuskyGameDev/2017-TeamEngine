package oasis.graphics.vertex;

import java.util.ArrayList;
import java.util.List;

import oasis.graphics.GraphicsDevice;
import oasis.graphics.Primitive;
import oasis.math.Vector3;
import oasis.math.Vector4;
import oasis.util.ArrayUtil;

public class Mesh {
    
    private VertexArray vao; 
    private IndexBuffer ibo; 
    private VertexBuffer positionVbo; 
    private VertexBuffer normalVbo; 
    private VertexBuffer colorVbo; 
    private VertexBuffer textureVbo; 
    
    private boolean update; 
    
    private GraphicsDevice gd; 
    
    public Mesh(GraphicsDevice gd) {
        this.gd = gd; 
        this.vao = gd.createVertexArray(); 
    }
    
    public void draw() {
        if (update) {
            updateVao(); 
        }
        
        gd.setVertexArray(vao);
        if (vao.getIndexBuffer() == null) {
            gd.drawArrays(Primitive.TRIANGLE_LIST, 0, vao.getVertexBuffer(0).getVertexCount());
        }
        else {
            gd.drawElements(Primitive.TRIANGLE_LIST, 0, vao.getIndexBuffer().getIndexCount());
        }
    }
    
    public void setIndices(int[] inds) {
        if (inds == null) {
            ibo.dispose(); 
            ibo = null; 
        }
        else {
            if (ibo == null) {
                ibo = gd.createIndexBuffer(BufferUsage.DYNAMIC); 
            }
            
            ibo.setData(inds);
        }
        
        update = true; 
    }
    
    public void setPositions(Vector3[] positions) {
        positionVbo = setVertexData(positionVbo, VertexFormat.POSITION_3, positions == null ? null : ArrayUtil.toFloatArray(positions)); 
    }
    
    public void setNormals(Vector3[] normals) {
        normalVbo = setVertexData(normalVbo, VertexFormat.NORMAL_3, normals == null ? null : ArrayUtil.toFloatArray(normals)); 
    }
    
    public void setColors(Vector4[] colors) {
        colorVbo = setVertexData(colorVbo, VertexFormat.COLOR_4, colors == null ? null : ArrayUtil.toFloatArray(colors)); 
    }
    
    private VertexBuffer setVertexData(VertexBuffer vbo, VertexFormat fmt, float[] data) {
        if (data == null) {
            if (vbo != null) {
                vbo.dispose(); 
            }
            return null; 
        }
        else {
            if (vbo == null) {
                vbo = gd.createVertexBuffer(fmt, BufferUsage.DYNAMIC); 
            }
            
            vbo.setData(data);
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
