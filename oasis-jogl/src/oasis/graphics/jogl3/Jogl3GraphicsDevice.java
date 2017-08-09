package oasis.graphics.jogl3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.glu.GLU;

import oasis.core.EngineException;
import oasis.graphics.BufferUsage;
import oasis.graphics.ColorRgba;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;

public class Jogl3GraphicsDevice implements GraphicsDevice {

    protected GL3 gl;
    protected Jogl3Context context; 
    
    private Jogl3VertexArray currentVao = null; 
    private Jogl3Shader currentShader = null; 

    public Jogl3GraphicsDevice() {
        context = new Jogl3Context(this); 
    }
    
    public void getError(String cmd) {
        int i = gl.glGetError(); 
        if (i == 0) return; 
        
        GLU glu = new GLU(); 
        
        System.out.println("[GLERROR] " + cmd + ": " + i + ": " + glu.gluErrorString(i));
    }
    
    @Override
    public Shader createShaderFromSource(String vertex, String fragment) {
        return new Jogl3Shader(this, vertex, fragment); 
    }

    @Override
    public Shader createShaderFromFile(String vertex, String fragment) {
        // TODO finish
        throw new EngineException("Creating shader from file is not currently supported"); 
    }

    @Override
    public VertexBuffer createVertexBuffer(VertexFormat format, BufferUsage usage) {
        return new Jogl3VertexBuffer(this, format, usage); 
    }

    @Override
    public IndexBuffer createIndexBuffer(BufferUsage usage) {
        return new Jogl3IndexBuffer(this, usage); 
    }

    @Override
    public VertexArray createVertexArray() {
        return new Jogl3VertexArray(this); 
    }

    @Override
    public VertexArray getVertexArray() {
        return currentVao; 
    }

    @Override
    public Shader getShader() {
        return currentShader; 
    }

    @Override
    public void setShader(Shader shader) {
        this.currentShader = (Jogl3Shader) shader; 
    }

    @Override
    public void setVertexArray(VertexArray vao) {
        this.currentVao = (Jogl3VertexArray) vao; 
    }

    @Override
    public void clearScreen(ColorRgba color) {
        gl.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        getError("glClearColor"); 
    }

    @Override
    public void drawArrays(Primitive prim, int start, int count) {
        context.bindProgram(currentShader.id); 
        context.bindVao(currentVao.id);
        gl.glDrawArrays(Jogl3Convert.getPrimitiveInt(prim), start, count);
        getError("glDrawArrays"); 
    }

    @Override
    public void drawElements(Primitive prim, int start, int count) {
        context.bindProgram(currentShader.id); 
        context.bindVao(currentVao.id);
        gl.glDrawElements(Jogl3Convert.getPrimitiveInt(prim), count, GL.GL_UNSIGNED_INT, start);
        getError("glDrawElements"); 
    } 
    
}
