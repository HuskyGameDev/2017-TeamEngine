package oasis.graphics.jogl;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL;

import oasis.core.GameLogger;
import oasis.graphics.Mesh;
import oasis.graphics.vertex.VertexDeclaration;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class JoglMesh implements Mesh {

    private static final GameLogger log = new GameLogger(JoglMesh.class);
    
    private JoglGlWrapper gl;
    private int vao;
    private int vbo;
    private int ebo;
    private Primitive prim = Primitive.Triangles;
    private boolean cwFront = false;
    private UsageHint hint = UsageHint.Static;
    private int vboSize = 0, curVboSize = 0;
    private boolean useEbo = false;
    
    public JoglMesh(JoglGlWrapper gl) {
        this.gl = gl;
        vao = gl.genVertexArray();
        vbo = gl.genBuffer();
        ebo = gl.genBuffer();
    }
    
    @Override
    public void dispose() {
        gl.deleteBuffer(vbo);
        gl.deleteBuffer(ebo);
        gl.deleteVertexArray(vao);
    }
    
    @Override
    public void bind() {
        gl.bindVertexArray(vao);
    }
    
    @Override
    public void unbind() {
        gl.bindVertexArray(0);
    }
    
    @Override
    public void draw() {
        // TODO FINISH
        if (useEbo) {
            // TODO FINISH
        }
        else {
            bind();
            gl.bindBuffer(GL.GL_ARRAY_BUFFER, vbo);

            gl.enableVertexAttribArray(0);
            gl.vertexAttribPointer(0, 3, GL.GL_FLOAT, 4 * VertexDeclaration.TOTAL_FLOATS, 4 * VertexDeclaration.OFFSET_POSITION);
            
            gl.enableVertexAttribArray(1);
            gl.vertexAttribPointer(1, 3, GL.GL_FLOAT, 4 * VertexDeclaration.TOTAL_FLOATS, 4 * VertexDeclaration.OFFSET_NORMAL);
            
            gl.enableVertexAttribArray(2);
            gl.vertexAttribPointer(2, 2, GL.GL_FLOAT, 4 * VertexDeclaration.TOTAL_FLOATS, 4 * VertexDeclaration.OFFSET_TEX_COORD);
            
            gl.enableVertexAttribArray(3);
            gl.vertexAttribPointer(3, 4, GL.GL_FLOAT, 4 * VertexDeclaration.TOTAL_FLOATS, 4 * VertexDeclaration.OFFSET_COLOR);
            
            
            gl.drawArrays(prim, curVboSize);
        }
    }
    
    @Override
    public Primitive getPrimitive() {
        return prim;
    }

    @Override
    public void setPrimitive(Primitive type) {
        prim = type;
    }

    @Override
    public boolean isFrontFaceClockwise() {
        return cwFront;
    }

    @Override
    public void setFrontFaceClockwise(boolean cw) {
        cwFront = cw;
    }

    @Override
    public UsageHint getUsageHint() {
        return hint;
    }

    @Override
    public void setUsageHint(UsageHint hint) {
        this.hint = hint;
    }

    @Override
    public void setVertices(VertexDeclaration[] verts) {
        int totalSize = verts.length * VertexDeclaration.TOTAL_FLOATS;
        FloatBuffer buffer = FloatBuffer.allocate(totalSize);
        
        for (int i = 0; i < verts.length; i++) {
            Vector3 pos = verts[i].getPosition();
            Vector3 norm = verts[i].getNormal();
            Vector2 tex = verts[i].getTexCoord();
            Vector4 col = verts[i].getColor();
            
            buffer.put(pos.getX());
            buffer.put(pos.getY());
            buffer.put(pos.getZ());
            
            buffer.put(norm.getX());
            buffer.put(norm.getY());
            buffer.put(norm.getZ());
            
            buffer.put(tex.getX());
            buffer.put(tex.getY());
            
            buffer.put(col.getX());
            buffer.put(col.getY());
            buffer.put(col.getZ());
            buffer.put(col.getW());
        }
        buffer.flip();
        
        bind();
        gl.bindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        
        if (vboSize < totalSize) {
            log.debug("New buffer");
            vboSize = totalSize;
            gl.bufferData(GL.GL_ARRAY_BUFFER, 4 * totalSize, buffer, hint);
        }
        else {
            log.debug("Sub buffer");
            gl.bufferSubData(GL.GL_ARRAY_BUFFER, 4 * totalSize, buffer);
        }
        
        curVboSize = totalSize;
        log.debug("vboSize: " + vboSize + ", curVboSize: " + curVboSize + ", triangles: " + (verts.length / 3));
    }

    @Override
    public void setIndices(int[] inds) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setIndices(Integer[] inds) {
        // TODO Auto-generated method stub
        
    }

}
