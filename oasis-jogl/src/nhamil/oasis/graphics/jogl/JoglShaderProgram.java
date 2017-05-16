package nhamil.oasis.graphics.jogl;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;

public class JoglShaderProgram implements ShaderProgram {

    private static final GameLogger log = new GameLogger(JoglShaderProgram.class);
    
    private String vSource;
    private String fSource;
    private JoglGlWrapper gl;
    
    private int id;
    private boolean valid;
    
    public JoglShaderProgram(String vSource, String fSource, JoglGlWrapper gl) {
        this.vSource = vSource;
        this.fSource = fSource;
        this.gl = gl;
        
        int vId, fId;
        valid = true;
        
        vId = gl.createShaderVertex();
        if (!gl.setSourceAndCompile(vId, vSource)) {
            log.warning("Failed to compile vertex shader");
            gl.deleteShader(vId);
            valid = false;
        }
        
        fId = gl.createShaderFragment();
        if (!gl.setSourceAndCompile(fId, fSource)) {
            log.warning("Failed to compile fragment shader");
            gl.deleteShader(vId);
            gl.deleteShader(fId);
            valid = false;
        }
        
        id = gl.createProgram();
        if (!gl.attachAndLink(id, vId, fId)) {
            log.warning("Failed to link shaders to program");
            gl.deleteProgram(id);
            valid = false;
        }
        
        gl.deleteShader(vId);
        gl.deleteShader(fId);
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public void dispose() {
        gl.deleteProgram(id);
        id = 0;
    }

    @Override
    public void setInt(String name, int i) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setFloat(String name, float f) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setVector2(String name, Vector2 r) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setVector3(String name, Vector3 r) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setVector3(String name, ColorRgba col) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setVector4(String name, ColorRgba col) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMatrix4(String name, Matrix4 r) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTextureUnit(String name, int i) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    
    
}
