package oasis.graphics.jogl3;

import java.util.HashSet;
import java.util.Set;

import com.jogamp.opengl.GL;

import oasis.core.EngineException;
import oasis.graphics.vertex.Attribute;
import oasis.graphics.vertex.IndexBuffer;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexElement;
import oasis.graphics.vertex.VertexFormat;

public class Jogl3VertexArray implements VertexArray {

    protected int id;

    private Jogl3GraphicsDevice gd; 
    private Jogl3IndexBuffer ibo; 
    private Jogl3VertexBuffer[] vboList; 
    
    public Jogl3VertexArray(Jogl3GraphicsDevice gd) { 
        this.gd = gd; 
        this.ibo = null; 
        this.vboList = new Jogl3VertexBuffer[0]; 
        
        int[] ids = new int[1]; 
        gd.gl.glGenVertexArrays(1, ids, 0);
        id = ids[0]; 
        
        setup(); 
    }
    
    @Override
    public int getVertexBufferCount() {
        return vboList.length; 
    }

    @Override
    public Jogl3VertexBuffer getVertexBuffer(int index) {
        return vboList[index]; 
    }

    @Override
    public void setVertexBuffers(VertexBuffer[] list) {
        for (VertexBuffer vbo : list) { 
            if (vbo == null || !(vbo instanceof Jogl3VertexBuffer)) {
                throw new EngineException("VertexBuffer must be instance of Jogl3VertexBuffer"); 
            }
        }
        
        vboList = new Jogl3VertexBuffer[list.length]; 
        
        for (int i = 0; i < list.length; i++) {
            vboList[i] = (Jogl3VertexBuffer) list[i]; 
        }
        
        setupVbos(); 
    }

    @Override
    public void setVertexBuffer(VertexBuffer vbo) {
        if (vbo == null) {
            setupVbos(); 
        }
        else {
            setVertexBuffers(new VertexBuffer[] { vbo }); 
        }
    }

    @Override
    public Jogl3IndexBuffer getIndexBuffer() {
        return ibo; 
    }

    @Override
    public void setIndexBuffer(IndexBuffer ibo) {
        this.ibo = (Jogl3IndexBuffer) ibo; 
        setupIbo(); 
    } 
    
    private void setup() {
        setupVbos(); 
        setupIbo(); 
    }
    
    private void setupVbos() {
        gd.context.bindVao(id);
        
        Set<Attribute> usedAttribs = new HashSet<>(); 
        
        for (Jogl3VertexBuffer vbo : vboList) {
            VertexFormat fmt = vbo.getFormat(); 
            int offset = 0; 
            for (int i = 0; i < fmt.getElementCount(); i++) {
                VertexElement elem = fmt.getElement(i); 
                
                Attribute attr = elem.getAttribute(); 
                
                if (!usedAttribs.contains(attr)) {
                    usedAttribs.add(attr); 
                    
                    gd.context.bindVbo(vbo.id);
                    
                    gd.gl.glVertexAttribPointer(attr.getIndex(), elem.getCount(), GL.GL_FLOAT, false, fmt.getByteCount(), offset);
                    gd.getError("glVertexAttribPointer"); 
                    
                    gd.gl.glEnableVertexAttribArray(attr.getIndex());
                    gd.getError("glEnableVertexAttribArray"); 
                }
                
                offset += elem.getByteCount(); 
            }
        }
        
        for (Attribute attr : Attribute.values()) {
            if (!usedAttribs.contains(attr)) {
                gd.gl.glDisableVertexAttribArray(attr.getIndex());
                gd.getError("glDisableVertexAttribArray"); 
            }
        }
    }
    
    private void setupIbo() {
        gd.context.bindVao(id);
        
        if (ibo == null) {
            gd.context.bindIbo(0);
        }
        else {
            gd.context.bindIbo(ibo.id);
        }
    }

    @Override
    public void dispose() {
        if (id != 0) {
            gd.gl.glDeleteBuffers(1, new int[] { id }, 1);
            id = 0; 
        }
    }

    @Override
    public boolean isDisposed() {
        return id == 0; 
    }
    
}
