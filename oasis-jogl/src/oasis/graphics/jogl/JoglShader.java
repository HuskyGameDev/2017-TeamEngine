package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.GameLogger;
import oasis.graphics.internal.NativeShader;
import oasis.graphics.vertex.Attribute;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class JoglShader extends JoglGraphicsResource implements NativeShader {

    private static final GameLogger log = new GameLogger(JoglShader.class); 
    
    private String vertexSource, fragmentSource; 
    private int id; 
    
    public JoglShader(JoglGraphicsDevice graphics, String vs, String fs) {
        super(graphics); 
        this.vertexSource = vs; 
        this.fragmentSource = fs; 
        create(); 
    }
    
    public void bind() { 
        graphics.gl.glUseProgram(id);
    }
    
    @Override
    public String getVertexSource() {
        return vertexSource;
    }

    @Override
    public String getFragmentSource() {
        return fragmentSource;
    }
    
    @Override
    public void setMatrix4(String name, Matrix4 value) {
        // TODO Auto-generated method stub
        graphics.gl.glUseProgram(id);
        graphics.gl.glUniformMatrix4fv(
                graphics.gl.glGetUniformLocation(id, name), 
                1, false, value.m, 0);
    }
    
    @Override
    public void setVector3(String name, Vector3 value) {
        // TODO Auto-generated method stub
        graphics.gl.glUseProgram(id);
        graphics.gl.glUniform3f(
                graphics.gl.glGetUniformLocation(id, name), 
                value.getX(), value.getY(), value.getZ());
    }
    
    @Override
    public void setFloat(String name, float value) {
        // TODO Auto-generated method stub
        graphics.gl.glUseProgram(id);
        graphics.gl.glUniform1f(
                graphics.gl.glGetUniformLocation(id, name), 
                value);
    }
    
    private void create() { 
        GL2 gl = graphics.gl; 
        
        int[] status = new int[1]; 
        int[] length = new int[1]; 
        byte[] text = new byte[512]; 
        
        int vert, frag; 
        id = gl.glCreateProgram(); 
        vert = gl.glCreateShader(GL2.GL_VERTEX_SHADER); 
        frag = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER); 
        
        gl.glShaderSource(vert, 1, new String[] { vertexSource }, new int[] { vertexSource.length() }, 0);
        gl.glCompileShader(vert);
        gl.glGetShaderiv(vert, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
            log.warning("Vertex Shader Compile Error: " + new String(text).trim());
        }
        
        gl.glShaderSource(frag, 1, new String[] { fragmentSource }, new int[] { fragmentSource.length() }, 0);
        gl.glCompileShader(frag);
        gl.glGetShaderiv(frag, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetShaderInfoLog(frag, 512, length, 0, text, 0);
            log.warning("Fragment Shader Compile Error: " + new String(text).trim());
        }
        
        gl.glAttachShader(id, vert);
        gl.glAttachShader(id, frag);
        
        bindAttributes(); 
        
        gl.glLinkProgram(id);
        gl.glGetProgramiv(id, GL2.GL_LINK_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
            log.warning("Link Error: " + new String(text).trim());
        }
        
        gl.glDeleteShader(vert);
        gl.glDeleteShader(frag);
        
        
        for (Attribute a : Attribute.values()) { 
            System.out.println(a.getGlslName() + " " + a.getIndex() + " " + graphics.gl.glGetAttribLocation(id, a.getGlslName()));
        }
    }

    private void bindAttributes() { 
        for (Attribute a : Attribute.values()) { 
            graphics.gl.glBindAttribLocation(id, a.getIndex(), a.getGlslName());
        }
    }

}
