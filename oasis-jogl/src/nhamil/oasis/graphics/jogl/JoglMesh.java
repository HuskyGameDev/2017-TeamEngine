package nhamil.oasis.graphics.jogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import nhamil.oasis.graphics.Mesh;
import nhamil.oasis.graphics.Vertex;
import nhamil.oasis.graphics.VertexDefinition;

public class JoglMesh implements Mesh {

    private JoglGlWrapper gl;
    private int vao;
    private int vbo;
    private int ebo;
    private Primitive prim = Primitive.Triangle;
    private boolean cwFront = false;
    private UsageHint hint = UsageHint.Static;
    private VertexDefinition def;
    
    public JoglMesh(VertexDefinition def, JoglGlWrapper gl) {
        this.gl = gl;
        this.def = def;
        if (!def.isFinished()) def.finish();
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
    public VertexDefinition getVertexDefinition() {
        return def;
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
    public void setVertices(Vertex[] verts) {
        // TODO Auto-generated method stub
        
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
