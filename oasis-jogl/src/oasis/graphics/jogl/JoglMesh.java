package oasis.graphics.jogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;

import oasis.graphics.Primitive;
import oasis.graphics.internal.NativeMesh;
import oasis.graphics.vertex.Attribute;
import oasis.graphics.vertex.BufferUsage;
import oasis.math.Vector3;
import oasis.math.Vector4;
import oasis.util.BufferUtil;

public class JoglMesh extends JoglGraphicsResource implements NativeMesh {

    private Primitive primitive = Primitive.TRIANGLE_LIST; 
    private BufferUsage usage = BufferUsage.DYNAMIC; 
    
    private JoglVertexBuffer indexVbo; 
    private JoglVertexBuffer positionVbo; 
    private JoglVertexBuffer normalVbo; 
    private JoglVertexBuffer colorVbo; 
    
    private IntBuffer indexData; 
    private FloatBuffer positionData; 
    private FloatBuffer normalData; 
    private FloatBuffer colorData; 
    
    private int[] indices; 
    private Vector3[] positions; 
    private Vector3[] normals; 
    private Vector4[] colors; 
    
    private int vao; 
    private boolean needsRebind = true; 
    
    public JoglMesh(JoglGraphicsDevice graphics) {
        super(graphics);
        int[] ids = new int[1]; 
        graphics.gl.glGenVertexArrays(1, ids, 0);
        vao = ids[0]; 
    }

    @Override
    public Primitive getPrimitive() {
        return primitive; 
    }

    @Override
    public BufferUsage getUsage() {
        return usage; 
    }

    @Override
    public int[] getIndices() {
        return indices; 
    }

    @Override
    public Vector3[] getPositions() {
        return positions; 
    }

    @Override
    public Vector3[] getNormals() {
        return normals; 
    }

    @Override
    public Vector4[] getColors() {
        return colors; 
    }

    @Override
    public void setPrimitive(Primitive primitive) {
        this.primitive = primitive; 
    }

    @Override
    public void setUsage(BufferUsage usage) {
        this.usage = usage; 
    }

    @Override
    public void setIndices(int[] indices) {
        this.indices = indices; 
        
        if (indexVbo == null) { 
            indexVbo = new JoglVertexBuffer(graphics); 
        }
        
        if (indexData == null || indexData.capacity() < indices.length) { 
            indexData = BufferUtil.createNativeIntBuffer(indices.length); 
        }
        
        indexData.clear();
        indexData.put(indices); 
        indexData.flip(); 
        
        graphics.gl.glBindVertexArray(vao);
        indexVbo.setData(GL.GL_ELEMENT_ARRAY_BUFFER, indexData, indices.length * 4, usage);
    }

    @Override
    public void setPositions(Vector3[] positions) {
        this.positions = positions; 
        
        if (positionVbo == null) { 
            positionVbo = new JoglVertexBuffer(graphics); 
        }
        
        if (positionData == null || positionData.capacity() < positions.length * 3) { 
            positionData = BufferUtil.createNativeFloatBuffer(positions.length * 3); 
            System.out.println("Creating FloatBuffer of size " + (positions.length * 3));
        }
        
        positionData.clear();
        for (Vector3 v : positions) { 
            positionData.put(v.getX()); 
            positionData.put(v.getY()); 
            positionData.put(v.getZ()); 
        }
        positionData.flip(); 
        
        positionVbo.setData(GL.GL_ARRAY_BUFFER, positionData, positions.length * 3 * 4, usage);
    }

    @Override
    public void setNormals(Vector3[] normals) {
        this.normals = normals; 
        
        if (normalVbo == null) { 
            normalVbo = new JoglVertexBuffer(graphics); 
        }
        
        if (normalData == null || normalData.capacity() < normals.length * 3) { 
            normalData = BufferUtil.createNativeFloatBuffer(normals.length * 3); 
        }
        
        normalData.clear();
        for (Vector3 v : normals) { 
            normalData.put(v.getX()); 
            normalData.put(v.getY()); 
            normalData.put(v.getZ()); 
        }
        normalData.flip(); 
        
        normalVbo.setData(GL.GL_ARRAY_BUFFER, normalData, normals.length * 3 * 4, usage);
    }

    @Override
    public void setColors(Vector4[] colors) {
        this.colors = colors; 
        
        if (colorVbo == null) { 
            colorVbo = new JoglVertexBuffer(graphics); 
        }
        
        if (colorData == null || colorData.capacity() < colors.length * 4) { 
            colorData = BufferUtil.createNativeFloatBuffer(colors.length * 4); 
        }
        
        colorData.clear();
        for (Vector4 v : colors) { 
            colorData.put(v.getX()); 
            colorData.put(v.getY()); 
            colorData.put(v.getZ()); 
            colorData.put(v.getW()); 
        }
        colorData.flip(); 
        
        colorVbo.setData(GL.GL_ARRAY_BUFFER, colorData, colors.length * 4 * 4, usage);
    }

    public void bindAndDraw() {
        graphics.gl.glBindVertexArray(vao);
        
        if (needsRebind) { 
            rebind(); 
        }
        
        if (indices == null) { 
            graphics.gl.glDrawArrays(JoglConvert.getPrimitiveInt(primitive), 0, positions.length);
        }
        else { 
            graphics.gl.glDrawElements(JoglConvert.getPrimitiveInt(primitive), indices.length, GL.GL_UNSIGNED_INT, 0);
        }
    }
    
    private void rebind() { 
        int ibo = indexVbo == null ? 0 : indexVbo.getVbo(); 
        graphics.gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ibo);
        
        bindVbo(positionVbo, Attribute.POSITION, 3, GL.GL_FLOAT); 
        bindVbo(normalVbo, Attribute.NORMAL, 3, GL.GL_FLOAT); 
        bindVbo(colorVbo, Attribute.COLOR, 4, GL.GL_FLOAT); 
        
        needsRebind = false; 
    }
    
    private void bindVbo(JoglVertexBuffer buffer, Attribute attribute, int comps, int type) { 
        if (buffer != null ) { 
            graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffer.getVbo());
            graphics.gl.glEnableVertexAttribArray(attribute.getIndex());
            graphics.gl.glVertexAttribPointer(attribute.getIndex(), comps, type, false, 0, 0); 
        }
        else { 
            graphics.gl.glDisableVertexAttribArray(attribute.getIndex());
        }
    }

}
