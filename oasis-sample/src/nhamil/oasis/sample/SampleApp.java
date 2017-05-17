package nhamil.oasis.sample;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

import nhamil.oasis.core.Application;
import nhamil.oasis.core.Config;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.Oasis;
import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.FrameBuffer;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.graphics.Texture;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private FrameBuffer fb;
    private ShaderProgram shader;
    
    private String vSource = ""
            + "#version 120\n "
            + "uniform mat4 ModelView;"
            + "void main() "
            + "{ "
            + "  gl_FrontColor = gl_Color; "
            + "  gl_Position = gl_ModelViewProjectionMatrix * ModelView * gl_Vertex; "
            + "}";
    private String fSource = ""
            + "#version 120\n "
            + "void main() "
            + "{ "
            + "  gl_FragColor = gl_Color; "
            + "}";
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        
        fb = graphics.createFrameBuffer(2048, 2048);
        fb.setColorAttachmentType(FrameBuffer.AttachmentType.Texture);
        fb.setDepthAttachmentType(FrameBuffer.AttachmentType.Texture);
        fb.getColorTexture().setFilter(Texture.Filter.Linear, Texture.Filter.Linear);
        fb.getColorTexture().setWrap(Texture.Wrap.Clamp, Texture.Wrap.Clamp);
        log.info("FrameBuffer valid: " + fb.isValid());
        
        display.setResizable(true);
        display.setSize(640, 400);
        
        shader = graphics.createShaderProgram(vSource, fSource);
        log.info("Shader valid: " + shader.isValid());
        
        log.info("Done!");
    }

    float angle = 0.0f;
    int ticks = 0;
    
    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }
        
        GL2 gl = GLContext.getCurrentGL().getGL2();
        GLU glu = new GLU();
        
        ticks++;
        angle += 5.0f / 60.0f;
        
        fb.bind();
        fb.setClearColor(new ColorRgba(0.8f, 0.8f, 0.8f, 1.0f));
        fb.clear();

        shader.bind();
        shader.setUniform("ModelView", new Matrix4().setScale(new Vector3(1.0f, 1.0f, 1.0f)));
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(70.0f, 1.0f, 1.2f, 5.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -2);
        gl.glRotatef(angle * 10, 0, 1, 0);
        
        Matrix4 m = new Matrix4().setIdentity();
//        m.multiplySelf(new Matrix4().setTranslation(new Vector3(0, 0, -2)));
//        m.multiplySelf(new Matrix4().setScale(new Vector3(2, 2, 2)));
        shader.setUniform("ModelView", m);
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.5f, -0.5f, 0.0f);
            gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.5f, 0.0f);
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, 0.0f);
    
            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.5f, 0.5f, 0.5f);
            gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.4f, 0.2f);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glEnd();
        
        shader.unbind();
        fb.unbind();
    }
    
    @Override
    public void onRender() {
        GL2 gl = GLContext.getCurrentGL().getGL2();
        GLU glu = new GLU();
        
//        graphics.setFramebuffer(null);
        graphics.setScreenClearColor(new ColorRgba(0.4f, 0.7f, 1.0f, 1.0f));
        graphics.clearScreen();
//        graphics.setShaderProgram(null);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
//        glu.gluPerspective(70.0f, display.getAspectRatio(), 0.1f, 1000.0f);
        glu.gluOrtho2D(-display.getAspectRatio(), display.getAspectRatio(), -1, 1);
        gl.glEnable(GL.GL_TEXTURE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-1.0f, 0, 0);
        fb.getColorTexture().bind(0);
        drawFb(gl);
        
        gl.glLoadIdentity();
        gl.glTranslatef(1.0f, 0, 0);
        fb.getDepthTexture().bind(0);
        drawFb(gl);
    }
    
    private void drawFb(GL2 gl) {
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-0.9f, -0.9f, 0.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0.9f, -0.9f, 0.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-0.9f, 0.9f, 0.0f);
    
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-0.9f, 0.9f, 0.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0.9f, -0.9f, 0.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(0.9f, 0.9f, 0.0f);
        gl.glEnd();
    }

    @Override
    public void onExit() {
        log.info("Closing application");
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        Config cfg = new Config();
        cfg.engine = JoglEngine.class;
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
