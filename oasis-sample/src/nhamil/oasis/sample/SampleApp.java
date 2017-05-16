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
import nhamil.oasis.graphics.Framebuffer;
import nhamil.oasis.math.FastMath;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private Framebuffer fb;
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        
        fb = graphics.createFramebuffer(4096, 4096, true, true);
        
        display.setResizable(true);
        
        log.info("Done!");
    }

    float angle = 0.0f;
    
    @Override
    public void onUpdate(float dt) {
        if (display.shouldClose()) {
            stop();
        }
        
        angle += 5.0f / 60.0f;
    }
    
    @Override
    public void onRender() {
        GL2 gl = GLContext.getCurrentGL().getGL2();
        GLU glu = new GLU();
        
        graphics.setFrameBuffer(fb);
        graphics.setClearColor(new ColorRgba(angle * 0.2f % 1.0f, 0.5f, 1.0f, 1.0f));
        graphics.clearScreen();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(70.0f, 1.0f, 0.1f, 1000.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -2);
        gl.glRotatef(angle * 10, 0, 1, 0);
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.95f, -0.5f, -0.5f);
            gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.5f, -0.5f);
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, -0.5f);
    
            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.5f, 0.5f, -1.5f);
            gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.4f, -1.5f);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, -1.5f);
        gl.glEnd();

        graphics.setFrameBuffer(null);
        graphics.setClearColor(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f));
        graphics.clearScreen();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(70.0f, display.getAspectRatio(), 0.1f, 1000.0f);
//        glu.gluOrtho2D(-display.getAspectRatio(), display.getAspectRatio(), -1, 1);
        graphics.setTexture(0, fb.getTexture());
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -2);
        gl.glRotatef(FastMath.cos(angle * 0.2f) * 20.0f, 0, 1, 0);
        gl.glRotatef(FastMath.cos(angle * 0.33f) * 6.0f, 0, 0, 1);
        drawFb(gl);
        
        gl.glLoadIdentity();
        gl.glTranslatef(2.5f, 0, -3);
        gl.glRotatef(FastMath.cos(angle * 0.41f) * 30.0f, 0, 1, 0);
        gl.glRotatef(FastMath.cos(angle * 0.13f) * 2.0f, 0, 0, 1);
        drawFb(gl);
        
        gl.glLoadIdentity();
        gl.glTranslatef(-2.5f, 0, -3);
        gl.glRotatef(FastMath.cos(angle * 0.23f) * 10.0f, 0, 1, 0);
        gl.glRotatef(FastMath.cos(angle * 0.51f) * 10.0f, 0, 0, 1);
        drawFb(gl);
    }
    
    private void drawFb(GL2 gl) {
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
