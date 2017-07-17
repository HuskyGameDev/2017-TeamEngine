package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;

import oasis.core.EngineException;
import oasis.graphics.ColorRgba;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.Shader;
import oasis.graphics.vertex.Mesh;

public class JoglGraphicsDevice implements GraphicsDevice {

    protected GL2 gl; 
    protected JoglContext context; 
    
    private JoglShader curShader = null;
    private JoglTexture2D[] curTextures = new JoglTexture2D[16]; 
    
    public JoglGraphicsDevice() { 
        context = new JoglContext(this); 
    }
    
    protected void setOglContext(GL gl) { 
        this.gl = gl.getGL2(); 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.getGL3().glPolygonMode(GL3.GL_FRONT_AND_BACK, GL3.GL_FILL);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_CCW);
    }
    
    @Override
    public void clearScreen(ColorRgba color) {
        gl.glClearColor(color.r, color.g, color.b, color.a);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }
    
//    @Override
//    public void setTexture(int unit, NativeTexture texture) { 
//        curTextures[unit] = (JoglTexture2D) texture; 
//    }

    @Override
    public void setShader(Shader shader) {
        curShader = (JoglShader) shader; 
    }

    @Override
    public void drawMesh(Mesh mesh) {
        if (curShader == null) { 
            throw new EngineException("Graphics device must have a shader set in order to render"); 
        }
        
        if (mesh == null) { 
            throw new EngineException("Mesh must not be null"); 
        }

        // TODO finish
        
        context.bindProgram(curShader.getId());
        for (int i = 0; i < curTextures.length; i++) { 
            if (curTextures[i] != null) { 
                curTextures[i].bind(i);
            }
        }
        
        ((JoglMesh) mesh).bindAndDraw(); 
    }
    

    @Override
    public Shader createShaderFromSource(String vertex, String fragment) {
        return new JoglShader(this, vertex, fragment);
    }

    @Override
    public Mesh createMesh() {
        return new JoglMesh(this);
    }


}
