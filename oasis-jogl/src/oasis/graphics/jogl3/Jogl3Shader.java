package oasis.graphics.jogl3;

import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;

import oasis.core.GameLogger;
import oasis.graphics.Attribute;
import oasis.graphics.Shader;
import oasis.math.Matrix3f;
import oasis.math.Matrix4f;
import oasis.math.Vector2f;
import oasis.math.Vector3f;
import oasis.math.Vector4f;

public class Jogl3Shader implements Shader {

    private static final GameLogger log = new GameLogger(Jogl3Shader.class); 
    
    protected int id;
    
    private Jogl3GraphicsDevice gd; 
    private String vertSource; 
    private String fragSource; 
    
    private Map<String, Integer> locations = new HashMap<>(); 
    private Map<String, Object> values = new HashMap<>(); 
    
    public Jogl3Shader(Jogl3GraphicsDevice gd, String vs, String fs) {
        this.gd = gd; 
        this.vertSource = vs; 
        this.fragSource = fs; 
        create(); 
    }
    
    private int getLocation(String name) {
        Integer loc = locations.get(name); 
        if (loc == null) {
            int newLoc = gd.gl.glGetUniformLocation(id, name); 
            locations.put(name, newLoc); 
            return newLoc; 
        }
        return loc.intValue(); 
    }
    
    private boolean setValue(String name, Object obj) {
        Object value = values.get(name); 
        if (value != null && value.equals(obj)) {
            return true; 
        }
        values.put(name, obj); 
        return false; 
    }
    
    @Override
    public String getVertexSource() {
        return vertSource; 
    }

    @Override
    public String getFragmentSource() {
        return fragSource; 
    }

    @Override
    public void setInt(String name, int value) {
        if (setValue(name, value)) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniform1i(getLocation(name), value);
    }
    
    @Override
    public void setFloat(String name, float value) {
        if (setValue(name, value)) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniform1f(getLocation(name), value);
    }

    @Override
    public void setVector2f(String name, Vector2f value) {
        if (setValue(name, new Vector2f(value))) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniform2f(getLocation(name), value.getX(), value.getY());
    }

    @Override
    public void setVector3f(String name, Vector3f value) {
        if (setValue(name, new Vector3f(value))) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniform3f(getLocation(name), value.getX(), value.getY(), value.getZ());
    }

    @Override
    public void setVector4f(String name, Vector4f value) {
        if (setValue(name, new Vector4f(value))) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniform4f(getLocation(name), value.getX(), value.getY(), value.getZ(), value.getW());
    }

    @Override
    public void setMatrix4f(String name, Matrix4f value) {
        if (setValue(name, new Matrix4f(value))) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniformMatrix4fv(getLocation(name), 1, false, value.get(new float[16]), 0);
    }
    
    @Override
    public void setMatrix3f(String name, Matrix3f value) {
        if (setValue(name, new Matrix3f(value))) return; 
        gd.context.bindProgram(id);
        gd.gl.glUniformMatrix3fv(getLocation(name), 1, false, value.get(new float[9]), 0);
    }
    
    private void create() { 
        GL3 gl = gd.gl; 
        
        int[] status = new int[1]; 
        int[] length = new int[1]; 
        byte[] text = new byte[512]; 
        
        int vert, frag; 
        id = gl.glCreateProgram(); 
        vert = gl.glCreateShader(GL2.GL_VERTEX_SHADER); 
        frag = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER); 
        
        gl.glShaderSource(vert, 1, new String[] { vertSource }, new int[] { vertSource.length() }, 0);
        gl.glCompileShader(vert);
        gl.glGetShaderiv(vert, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
            log.warning("Vertex Shader Compile Error: " + new String(text).trim());
        }
        
        gl.glShaderSource(frag, 1, new String[] { fragSource }, new int[] { fragSource.length() }, 0);
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
        
        
//        for (Attribute a : Attribute.values()) { 
//            System.out.println(a.getGlslName() + " " + a.getIndex() + " " + gd.gl.glGetAttribLocation(id, a.getGlslName()));
//        }
    }

    private void bindAttributes() { 
        for (Attribute a : Attribute.values()) { 
            gd.gl.glBindAttribLocation(id, a.getIndex(), a.getGlslName());
        }
    }

    @Override
    public void dispose() {
        if (id != 0) {
            gd.gl.glDeleteProgram(id);
            id = 0; 
        }
    }

    @Override
    public boolean isDisposed() {
        return id == 0; 
    }

}
