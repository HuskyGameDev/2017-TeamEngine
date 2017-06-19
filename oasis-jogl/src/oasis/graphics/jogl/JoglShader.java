package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.EngineException;
import oasis.core.GameLogger;
import oasis.graphics.Attribute;
import oasis.graphics.Shader;
import oasis.math.FastMath;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class JoglShader {

    private static final GameLogger log = new GameLogger(JoglShader.class); 
    
    private JoglGraphicsDevice graphics; 
    private Shader shader; 
    
    private int id; 
    private JoglInputLayout layout; 
    
    public static float ratio = 2.0f; 
    
    public JoglShader(JoglGraphicsDevice graphics, Shader shader) { 
        this.graphics = graphics; 
        this.shader = shader; 
        createShader(); 
    }
    
    public int getId() { 
        return id; 
    }
    
    public JoglInputLayout getInputLayout() { 
        return layout; 
    }
    
    public static float angle = 0.0f;
    
    public void update() { 
        
        Vector3 pos = new Vector3();
        float scale = 5.0f;
        float time = angle * 5.0f;
        pos.setX(scale * FastMath.cos(time));
        pos.setY(20.2f);
        pos.setZ(scale * FastMath.sin(time));
        
        graphics.gl.glUseProgram(id);
        
        Matrix4 m;

        System.out.println(ratio);
        
        // view matrix
        m = Matrix4.createLookAt(pos, new Vector3(0, 6.0f, 0), new Vector3(0, 1, 0));
        graphics.gl.glUniformMatrix4fv(
                graphics.gl.glGetUniformLocation(id, "View"), 
                1, false, m.get(new float[16]), 0);
        
        // light direction
        time = angle * 8.0f;
        pos.setX(scale * FastMath.cos(time));
        pos.setY(8f);
        pos.setZ(scale * FastMath.sin(time));
//        graphics.gl.glUniform3f(
//                graphics.gl.glGetUniformLocation(id, "LightDirection"), 
//                pos.getX(), pos.getY(), pos.getZ());
        
        // projection 
        m = Matrix4.createPerspective(15.0f, (float) ratio, 0.1f, 1000.0f);
        graphics.gl.glUniformMatrix4fv(
                graphics.gl.glGetUniformLocation(id, "Projection"), 
                1, false, m.get(new float[16]), 0);
        
        m = Matrix4.createIdentity(); 
        graphics.gl.glUniformMatrix4fv(
                graphics.gl.glGetUniformLocation(id, "Model"), 
                1, false, m.get(new float[16]), 0);
        
        // TODO 
//        shader.setDirty(false);
    }
    
    public void bind() { 
        graphics.gl.glUseProgram(id);
    }
    
    private void createShader() { 
        GL2 gl = graphics.gl; 
        
        int[] status = new int[1]; 
        int[] length = new int[1]; 
        byte[] text = new byte[512]; 
        
        int vert, frag; 
        id = gl.glCreateProgram(); 
        vert = gl.glCreateShader(GL2.GL_VERTEX_SHADER); 
        frag = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER); 
        
        gl.glShaderSource(vert, 1, new String[] { shader.getVertexSource() }, new int[] { shader.getVertexSource().length() }, 0);
        gl.glCompileShader(vert);
        gl.glGetShaderiv(vert, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
            log.warning("Vertex Shader Compile Error: " + new String(text).trim());
        }
        
        gl.glShaderSource(frag, 1, new String[] { shader.getFragmentSource() }, new int[] { shader.getFragmentSource().length() }, 0);
        gl.glCompileShader(frag);
        gl.glGetShaderiv(frag, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetShaderInfoLog(frag, 512, length, 0, text, 0);
            log.warning("Fragment Shader Compile Error: " + new String(text).trim());
        }
        
        gl.glAttachShader(id, vert);
        gl.glAttachShader(id, frag);
        
        // TODO bind attributes here
        
        gl.glLinkProgram(id);
        gl.glGetProgramiv(id, GL2.GL_LINK_STATUS, status, 0);
        if (status[0] != GL.GL_TRUE) {
            gl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
            log.warning("Link Error: " + new String(text).trim());
        }
        
        pollAttributes(gl); 
        
        gl.glDeleteShader(vert);
        gl.glDeleteShader(frag);
    }
    
    private void pollAttributes(GL2 gl) { 
        layout = new JoglInputLayout(); 
        
        int[] tmp = new int[3]; 
        gl.glGetProgramiv(id, GL2.GL_ACTIVE_ATTRIBUTES, tmp, 0);
        
        int numAttributes = tmp[0]; 
        
        System.out.println("Num Attributes: " + numAttributes);
        
        for (int i = 0; i < numAttributes; i++) { 
            byte[] bytes = new byte[512]; 
            gl.glGetActiveAttrib(id, i, bytes.length, tmp, 0, tmp, 1, tmp, 2, bytes, 0);
            
            String name = new String(bytes).trim(); 
            
            int digit = 0; 
            String attr = ""; 
            
            if (Character.isDigit(name.charAt(name.length() - 2))) { 
                digit = Integer.parseInt(name.substring(name.length() - 2)); 
                attr = name.substring(0, name.length() - 2); 
            }
            else { 
                digit = Integer.parseInt(name.substring(name.length() - 1)); 
                attr = name.substring(0, name.length() - 1); 
            }
            
            Attribute attribute = JoglConvert.getAttributeFromString(attr); 
            
            if (attribute == null) { 
                throw new EngineException("Unknown attribute type: " + attr); 
            }
            
            layout.attributes.add(attribute); 
            layout.indices.add(digit); 
            layout.location.add(gl.glGetAttribLocation(id, name)); 
        }
    }
    
}
