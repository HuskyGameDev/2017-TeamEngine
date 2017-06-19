package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.EngineException;
import oasis.graphics.ColorRgba;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.vertex.IndexBuffer;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;

public class JoglGraphicsDevice implements GraphicsDevice {

    protected GL2 gl; 
    
    private Shader curShader = null;
    private VertexArray curVao = null; 
    
    protected void setOglContext(GL gl) { 
        this.gl = gl.getGL2(); 
        gl.glEnable(GL.GL_DEPTH_TEST);
    }
    
    @Override
    public void clearScreen(ColorRgba color) {
        gl.glClearColor(color.r, color.g, color.b, color.a);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void setShader(Shader shader) {
        update(shader); 
        curShader = shader; 
    }

    @Override
    public void setVertexArray(VertexArray vertArray) {
        update(vertArray); 
        curVao = vertArray; 
    }

    @Override
    public void drawArrays(Primitive primitive, int start, int count) {
        if (curShader == null) { 
            throw new EngineException("Graphics device must have a shader set in order to render"); 
        }
        
        if (curVao == null) { 
            throw new EngineException("Graphics device must have a vertex array set in order to render"); 
        }
        
        update(curShader); 
        update(curVao); 
        
        ((JoglShader) curShader.getInternalResource()).bind(); 
        ((JoglVertexArray) curVao.getInternalResource()).bind((JoglShader) curShader.getInternalResource());
        
        gl.glDrawArrays(JoglConvert.getPrimitiveInt(primitive), start, count * primitive.getPointCount());
    }

    @Override
    public void drawArraysIndexed(Primitive primitive, int start, int count) {
        // TODO finish
    }

    // TODO use VAOs, right now it is just index buffer and list of vertex buffers
    @Override
    public void update(VertexArray vertArray) {
        if (!vertArray.isDirty()) { 
            return; 
        }
        
        if (vertArray.getInternalResource() == null) { 
            // need to create VBO
            vertArray.setInternalResource(new JoglVertexArray(this, vertArray));
        }
        
        ((JoglVertexArray) vertArray.getInternalResource()).update(); 
    }

    @Override
    public void update(VertexBuffer vertBuffer) {
        if (!vertBuffer.isDirty()) { 
            return; 
        }
        
        if (vertBuffer.getInternalResource() == null) { 
            // need to create VBO
            vertBuffer.setInternalResource(new JoglVertexBuffer(this, vertBuffer));
        }
        
        ((JoglVertexBuffer) vertBuffer.getInternalResource()).update(); 
    }

    @Override
    public void update(IndexBuffer indBuffer) {
        if (!indBuffer.isDirty()) { 
            return; 
        }
        
        if (indBuffer.getInternalResource() == null) { 
            // need to create VBO
            indBuffer.setInternalResource(new JoglIndexBuffer(this, indBuffer));
        }
        
        ((JoglIndexBuffer) indBuffer.getInternalResource()).update(); 
    }
    
    @Override
    public void update(Shader shader) {
        if (!shader.isDirty()) { 
            return; 
        }
        
        if (shader.getInternalResource() == null) { 
            // need to create VBO
            shader.setInternalResource(new JoglShader(this, shader));
        }
        
        ((JoglShader) shader.getInternalResource()).update(); 
    }

}
