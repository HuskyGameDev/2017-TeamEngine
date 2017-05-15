package nhamil.oasis.sample;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

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
        log.info("Sound System: " + audio);
        log.info("Input System: " + input);
        
        fb = renderer.createFrameBuffer(800, 600, true, true);
        
        display.setResizable(true);
        
        log.info("Done!");
    }

    float angle = 0.0f;
    
    @Override
    public void onUpdate(float dt) {
        if (display.isClosed()) {
            stop();
        }
        
        angle += 5.0f / 60.0f;
    }
    
    @Override
    public void onRender() {
        GL2 gl = (GL2) ((JoglEngine) engine).getGraphics().getJoglContext().getGL();
        
        renderer.setFrameBuffer(fb);
        renderer.setClearColor(new ColorRgba(0.5f, 0.5f, 1.0f, 1.0f));
        renderer.clearScreen();

        gl.glLoadIdentity();

        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.95f, -0.5f, -0.5f);
            gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.5f, -0.5f);
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, -0.5f);
    
            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
            gl.glVertex3f(-0.5f, 0.5f, 0.5f);
            gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, -0.4f, 0.5f);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glEnd();

        renderer.setFrameBuffer(null);
        renderer.setClearColor(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f));
        renderer.clearScreen();

        gl.glLoadIdentity();
        gl.glRotatef(FastMath.cos(angle) * 10.0f, 0, 0, 1);

        renderer.setTexture(0, fb.getTexture());
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glBegin(GL.GL_TRIANGLES);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-0.5f, -0.5f, 0.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0.5f, -0.5f, 0.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-0.5f, 0.5f, 0.0f);
    
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-0.5f, 0.5f, 0.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0.5f, -0.5f, 0.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(0.5f, 0.5f, 0.0f);
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
