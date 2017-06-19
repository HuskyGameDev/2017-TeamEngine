package oasis.graphics.jogl;

import java.util.ArrayList;

import com.jogamp.opengl.GL;

import oasis.graphics.vertex.Attribute;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexElement;
import oasis.graphics.vertex.VertexLayout;

public class JoglVertexArray extends JoglGraphicsResource implements VertexArray {

    private ArrayList<JoglVertexBuffer> vertBuffers; 
    
    public JoglVertexArray(JoglGraphicsDevice graphics, VertexBuffer[] buffers) {
        super(graphics);
        vertBuffers = new ArrayList<>(); 
        for (VertexBuffer vbo : buffers) { 
            vertBuffers.add((JoglVertexBuffer) vbo); 
        }
    }
    
    public void bind(JoglShader currentShader) { 
        for (Attribute a : Attribute.values()) { 
            for (JoglVertexBuffer vert : vertBuffers) { 
                VertexLayout layout = vert.getVertexLayout(); 
                VertexElement elem;
                
                if ((elem = layout.getElement(a)) != null) { 
                    int offset = layout.getOffset(a); 
                    int size = layout.getTotalSizeInBytes(); 
                    
                    graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vert.getVbo());
                    graphics.gl.glEnableVertexAttribArray(a.getIndex());
                    graphics.gl.glVertexAttribPointer(a.getIndex(), elem.getComponentCount(), GL.GL_FLOAT, false, size, offset);
                }
            }
        }
    }

    @Override
    public int getVertexBufferCount() {
        return vertBuffers.size();
    }

    @Override
    public VertexBuffer getVertexBuffer(int index) {
        return vertBuffers.get(index);
    }

    @Override
    public void setVertexBuffer(VertexBuffer buffer) {
        setVertexBuffers(buffer); 
    }

    @Override
    public void setVertexBuffers(VertexBuffer... buffers) {
        vertBuffers.clear(); 
        for (VertexBuffer vbo : buffers) { 
            vertBuffers.add((JoglVertexBuffer) vbo); 
        }
    }

}
