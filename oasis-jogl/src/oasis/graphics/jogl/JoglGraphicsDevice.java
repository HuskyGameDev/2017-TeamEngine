package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.EngineException;
import oasis.graphics.ColorRgba;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsResourceFactory;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.vertex.VertexArray;

public class JoglGraphicsDevice implements GraphicsDevice {

    protected GL2 gl; 
    
    private JoglShader curShader = null;
    private JoglVertexArray curVao = null; 
    
    private JoglGraphicsResourceFactory resources; 
    
    public JoglGraphicsDevice() { 
        resources = new JoglGraphicsResourceFactory(this); 
    }
    
    protected void setOglContext(GL gl) { 
        this.gl = gl.getGL2(); 
        gl.glEnable(GL.GL_DEPTH_TEST);
    }
    
    @Override
    public GraphicsResourceFactory getResourceFactory() { 
        return resources; 
    }
    
    @Override
    public void clearScreen(ColorRgba color) {
        gl.glClearColor(color.r, color.g, color.b, color.a);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void setShader(Shader shader) {
        curShader = (JoglShader) shader; 
    }

    @Override
    public void setVertexArray(VertexArray vertArray) {
        curVao = (JoglVertexArray) vertArray; 
    }

    @Override
    public void drawArrays(Primitive primitive, int start, int count) {
        if (curShader == null) { 
            throw new EngineException("Graphics device must have a shader set in order to render"); 
        }
        
        if (curVao == null) { 
            throw new EngineException("Graphics device must have a vertex array set in order to render"); 
        }

        // TODO finish
        
        curShader.bind(); 
        curVao.bind(curShader); 
        
        gl.glDrawArrays(JoglConvert.getPrimitiveInt(primitive), start, count * primitive.getPointCount());
    }

    @Override
    public void drawArraysIndexed(Primitive primitive, int start, int count) {
        // TODO finish
    }

}
