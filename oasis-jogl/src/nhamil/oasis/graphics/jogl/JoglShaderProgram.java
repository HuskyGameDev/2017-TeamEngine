package nhamil.oasis.graphics.jogl;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;

public class JoglShaderProgram implements ShaderProgram {

    private static final GameLogger log = new GameLogger(JoglShaderProgram.class);
    
    private JoglGlWrapper gl;
    
    private int id;
    private boolean valid;
    
    public JoglShaderProgram(String vSource, String fSource, JoglGlWrapper gl) {
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
    public void setFloat(String name, float f) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformFloat(gl.uniformLocation(id, name), f);
        gl.useProgram(old);
    }

    @Override
    public void setVector2(String name, Vector2 r) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformVector2(gl.uniformLocation(id, name), r.x, r.y);
        gl.useProgram(old);
    }

    @Override
    public void setVector3(String name, Vector3 r) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformVector3(gl.uniformLocation(id, name), r.x, r.y, r.z);
        gl.useProgram(old);
    }

    @Override
    public void setVector3(String name, ColorRgba col) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformVector3(gl.uniformLocation(id, name), col.getRed(), col.getGreen(), col.getBlue());
        gl.useProgram(old);
    }

    @Override
    public void setVector4(String name, ColorRgba col) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformVector4(gl.uniformLocation(id, name), col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
        gl.useProgram(old);
    }

    @Override
    public void setMatrix4(String name, Matrix4 r) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformMatrix4(gl.uniformLocation(id, name), false, r.get(new float[16]));
        gl.useProgram(old);
    }

    @Override
    public void setTextureUnit(String name, int i) {
        int old = gl.getBoundProgram();
        gl.useProgram(id);
        gl.uniformInt(gl.uniformLocation(id, name), i);
        gl.useProgram(old);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    
    
}
