package nhamil.oasis.graphics.jogl;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;
import nhamil.oasis.math.Vector4;

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
        
        log.debug("Attribute location of \"Position\": " + gl.getAttribLocation(id, "Position"));
        log.debug("Attribute location of \"Normal\": " + gl.getAttribLocation(id, "Normal"));
        log.debug("Attribute location of \"TexCoord\": " + gl.getAttribLocation(id, "TexCoord"));
        log.debug("Attribute location of \"Color\": " + gl.getAttribLocation(id, "Color"));
        
        gl.deleteShader(vId);
        gl.deleteShader(fId);
    }
    
    public void bind() {
        gl.useProgram(id);
    }
    
    public void unbind() {
        gl.useProgram(0);
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
    public void setUniform(String name, float f) {
        bind();
        gl.uniformFloat(gl.uniformLocation(id, name), f);
    }

    @Override
    public void setUniform(String name, Vector2 r) {
        bind();
        gl.uniformVector2(gl.uniformLocation(id, name), r.getX(), r.getY());
    }

    @Override
    public void setUniform(String name, Vector3 r) {
        bind();
        gl.uniformVector3(gl.uniformLocation(id, name), r.getX(), r.getY(), r.getZ());
    }

    @Override
    public void setUniform(String name, Vector4 r) {
        bind();
        gl.uniformVector4(gl.uniformLocation(id, name), r.getX(), r.getY(), r.getZ(), r.getW());
    }

    @Override
    public void setUniform(String name, ColorRgba col, boolean justRgb) {
        bind();
        if (justRgb) {
            gl.uniformVector3(gl.uniformLocation(id, name), col.getRed(), col.getGreen(), col.getBlue());
        } else {
            gl.uniformVector4(gl.uniformLocation(id, name), col.getRed(), col.getGreen(), col.getBlue(),
                    col.getAlpha());
        }
    }

    @Override
    public void setUniform(String name, Matrix4 r) {
        bind();
        gl.uniformMatrix4(gl.uniformLocation(id, name), false, r.get(new float[16]));
    }

    @Override
    public void setTextureUnit(String name, int unit) {
        bind();
        gl.uniformInt(gl.uniformLocation(id, name), unit);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

}
