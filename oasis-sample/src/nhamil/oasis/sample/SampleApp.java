package nhamil.oasis.sample;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;

import nhamil.oasis.core.Application;
import nhamil.oasis.core.Config;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.Oasis;
import nhamil.oasis.core.jogl.JoglEngine;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.FrameBuffer;
import nhamil.oasis.graphics.Texture;
import nhamil.oasis.math.FastMath;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    private FrameBuffer fb;
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        log.info("Sound System: " + audio);
        log.info("Input System: " + input);
        
        fb = new FrameBuffer(192, 108);
        fb.setColorBuffer(new Texture(192, 108));
        fb.setDepthBuffer(new Texture(192, 108));
        
        display.setSize(800, 800);
        display.setResizable(true);
        
        log.info("Done!");
    }

    float angle = 0.0f;
    
    @Override
    public void onUpdate(float dt) {
        if (display.isClosed()) stop();
        
        angle += 5.0f / 60.0f;
    }

    @Override
    public void onRender(float lerp) {
        // TODO don't actually use OpenGL here, not everything is implemented yet so I need it for testing
        GL2 gl = (GL2) GLContext.getCurrentGL();
        
        renderer.setFrameBuffer(fb);
        renderer.setClearColor(new ColorRgba(0.5f, 0.5f, 1.0f, 1.0f));
        renderer.clearScreen();

        gl.glLoadIdentity();
        
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); gl.glVertex3f(-0.95f, -0.5f, 0.0f); 
            gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); gl.glVertex3f( 0.5f, -0.5f, 0.0f); 
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); gl.glVertex3f(-0.5f,  0.5f, 0.0f); 
            
            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f); gl.glVertex3f(-0.5f,  0.5f, 0.0f); 
            gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f); gl.glVertex3f( 0.5f, -0.5f, 0.0f); 
            gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); gl.glVertex3f( 0.5f,  0.5f, 0.0f); 
            
        gl.glEnd();
        
        renderer.setFrameBuffer(null);
        renderer.setClearColor(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f));
        renderer.clearScreen();
        
        gl.glLoadIdentity();
        gl.glRotatef(FastMath.cos(angle) * 10.0f, 0, 0, 1);
        
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBindTexture(GL.GL_TEXTURE_2D, fb.getColorBuffer().getId());
        
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); 
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.2f, -1.2f, 0.0f); 
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.2f, -1.2f, 0.0f); 
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.2f,  1.2f, 0.0f); 
            
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.2f,  1.2f, 0.0f); 
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.2f, -1.2f, 0.0f); 
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.2f,  1.2f, 0.0f); 
            
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
