package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.Bitmap;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.Framebuffer;
import nhamil.oasis.graphics.GraphicsContext;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.graphics.Texture;

public class JoglGraphicsContext implements GraphicsContext {

    private static final GameLogger log = new GameLogger(JoglGraphicsContext.class);
    
    private JoglDisplay display;
    private JoglGlWrapper gl;
    private Framebuffer framebuffer;
    
    private JoglShaderProgram defaultShader;
    
    public JoglGraphicsContext(JoglDisplay display, JoglGlWrapper gl) {
        this.display = display;
        this.gl = gl;
    }
    
    public JoglShaderProgram getDefaultShader() {
        return defaultShader;
    }
    
    public void init() {
        createDefaultShader();
    }
    
    public void newFrame() {
        setFramebuffer(null);
        setShaderProgram(null);
        setTexture(0, null);
    }
    
    @Override
    public int getWidth() {
        return display.getWidth();
    }

    @Override
    public int getHeight() {
        return display.getHeight();
    }

    @Override
    public void setClearColor(ColorRgba color) {
        gl.clearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void clearScreen() {
        if (framebuffer != null) {
            if (framebuffer.hasTexture()) ((JoglTexture) framebuffer.getTexture()).clear();
        }
        
        gl.clear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public void setFramebuffer(Framebuffer fb) {
        this.framebuffer = fb;
        if (fb != null) {
            gl.bindFramebuffer(((JoglFramebuffer) fb).getId());
            gl.viewport(0, 0, fb.getWidth(), fb.getHeight());
        }
        else {
            gl.bindFramebuffer(0);
            gl.viewport(0, 0, getWidth(), getHeight());
        }
    }
    
    @Override
    public void setTexture(int unit, Texture tex) {
        gl.activeTexture(unit);
        gl.enableTexture();
        if (tex != null) {
            gl.bindTexture(((JoglTexture) tex).getId());
        }
        else {
            gl.bindTexture(0);
        }
    }

    @Override
    public Framebuffer createFramebuffer(int width, int height, boolean colorBuffer, boolean depthBuffer) {
        JoglFramebuffer fb = new JoglFramebuffer(width, height, colorBuffer, depthBuffer, gl);
        return fb;
    }

    @Override
    public ShaderProgram createShaderProgram(String vSource, String fSource) {
        return new JoglShaderProgram(vSource, fSource, gl);
    }

    @Override
    public Texture createTexture(int width, int height) {
        return null;
    }

    @Override
    public Texture createTexture(Bitmap bmp) {
        return null;
    }

    @Override
    public void setShaderProgram(ShaderProgram shader) {
        if (shader == null) {
            gl.useProgram(0);
        }
        else {
            gl.useProgram(((JoglShaderProgram) shader).getId());
        }
    }
    
    private void createDefaultShader() {
//        String vFile = Oasis.DEFAULT_SHADER_FOLDER + "default_color.vert";
//        String fFile = Oasis.DEFAULT_SHADER_FOLDER + "default_color.frag";
//        log.debug("Vertex File: " + vFile);
//        log.debug("Fragment File: " + fFile);
//        
//        String vs = getFileContents(vFile);
//        String fs = getFileContents(fFile);
//        
//        log.debug("Vertex source " + (vs.equals("") ? "not found" : "found"));
//        log.debug("Fragment source " + (fs.equals("") ? "not found" : "found"));
//        
//        defaultShader = (JoglShaderProgram) createShaderProgram(vs, fs);
    }

//    private String getFileContents(String file) {
//        StringBuilder sb = new StringBuilder();
//        try {
//            BufferedReader vRead = new BufferedReader(new FileReader(file));
//            
//            String line;
//            while ((line = vRead.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            
//            vRead.close();
//        } catch (Exception e) {
//            log.warning("Problem reading file " + file);
//        }
//        return sb.toString();
//    }
}
