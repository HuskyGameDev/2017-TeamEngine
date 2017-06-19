package oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import oasis.graphics.Attribute;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexElement;
import oasis.graphics.vertex.VertexLayout;

public class JoglVertexArray {

    private JoglGraphicsDevice graphics; 
    private VertexArray array; 
    
    public JoglVertexArray(JoglGraphicsDevice graphics, VertexArray array) { 
        this.graphics = graphics; 
        this.array = array; 
    }
    
    public void update() { 
        array.setDirty(false);
    }
    
    public void bind(JoglShader shader) { 
        JoglInputLayout input = shader.getInputLayout(); 
        
        for (int i = 0; i < input.attributes.size(); i++) { 
            Attribute attr = input.attributes.get(i); 
            int index = input.indices.get(i); 
            int location = input.location.get(i); 
            
            // TODO IBO
            
            for (int j = 0; j < array.getVertexBufferCount(); j++) { 
                graphics.update(array.getVertexBuffer(j));
                VertexLayout layout = array.getVertexBuffer(j).getVertexLayout(); 
                VertexElement elem;
                
                if ((elem = layout.getElement(attr, index)) != null) { 
                    int offset = layout.getOffset(attr, index); 
                    int size = layout.getTotalSizeInBytes(); 
                    
                    graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, ((JoglVertexBuffer) array.getVertexBuffer(j).getInternalResource()).getVbo());
                    graphics.gl.glEnableVertexAttribArray(location);
                    graphics.gl.glVertexAttribPointer(location, elem.getComponentCount(), GL.GL_FLOAT, false, size, offset);
                }
            }
        }
    }
    
}
