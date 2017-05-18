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
import nhamil.oasis.graphics.Mesh;
import nhamil.oasis.graphics.ShaderProgram;
import nhamil.oasis.graphics.Vertex;
import nhamil.oasis.math.Matrix4;
import nhamil.oasis.math.Vector3;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private FrameBuffer fb;
    private ShaderProgram shader;
    private Mesh triMesh, water;
    
    private String vSource = ""
            + "#version 120\n "
            + "attribute vec3 Position; "
            + "attribute vec4 Color; "
            + "uniform mat4 Model; "
            + "uniform mat4 View; "
            + "uniform mat4 Projection; "
            + "varying vec4 FragColor; "
            + "void main() "
            + "{ "
            + "  FragColor = Color; "
            + "  gl_Position = Projection * View * Model * vec4(Position, 1.0); "
            + "}";
    private String fSource = ""
            + "#version 120\n "
            + "varying vec4 FragColor; "
            + "void main() "
            + "{ "
            + "  gl_FragColor = FragColor; "
            + "}";
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        
        fb = graphics.createFrameBuffer(2048, 2048);
        fb.setColorAttachmentType(FrameBuffer.AttachmentType.Texture);
        fb.setDepthAttachmentType(FrameBuffer.AttachmentType.Texture);
        log.info("FrameBuffer valid: " + fb.isValid());
        
        display.setResizable(true);
        display.setSize(800, 400);
        
        triMesh = graphics.createMesh();
        HeightMap map = new HeightMap(512, 512);
        map.setMesh(triMesh, new Vector3(-10, 0, -10), new Vector3(10, 1, 10));
        
        water = graphics.createMesh();
        map = new HeightMap(1, 1);
        map.setFlat(true, 0.5f);
        map.setMesh(water, new Vector3(-10, 0, -10), new Vector3(10, 1, 10));
        
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
        
        ticks++;
        angle += 5.0f / 60.0f;
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
        
        shader.bind();
        
        Matrix4 m = new Matrix4().setIdentity();
        m.setIdentity();
        shader.setUniform("View", m);
        
        m.setIdentity();
        m.multiplySelf(new Matrix4().setTranslation(new Vector3(0, -0.75f, -2)));
        m.multiplySelf(new Matrix4().setRotationX(25f));
        m.multiplySelf(new Matrix4().setRotationY(angle));
//        m.multiplySelf(new Matrix4().setRotationX(angle * 3));
        shader.setUniform("Model", m);
        
        m.setPerspective(70.0f, display.getAspectRatio(), 0.1f, 100.0f);
//        m.setIdentity();
        shader.setUniform("Projection", m);
        
        triMesh.draw();
        water.draw();
        
//        gl.glLoadIdentity();
//        gl.glTranslatef(-1.0f, 0, 0);
//        fb.getColorTexture().bind(0);
//        drawFb(gl);
//        
//        gl.glLoadIdentity();
//        gl.glTranslatef(1.0f, 0, 0);
//        fb.getDepthTexture().bind(0);
//        drawFb(gl);
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
