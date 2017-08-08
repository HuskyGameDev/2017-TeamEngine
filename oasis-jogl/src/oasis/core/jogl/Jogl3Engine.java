package oasis.core.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.Engine;
import oasis.core.EngineException;
import oasis.core.GameLogger;
import oasis.graphics.jogl3.Jogl3Display;
import oasis.graphics.jogl3.Jogl3GraphicsDevice;

public class Jogl3Engine extends Engine {

    private static final GameLogger log = new GameLogger(Jogl3Engine.class);

    private Jogl3Display display;

    public Jogl3Engine() {
        display = new Jogl3Display();
    }

    @Override
    public Jogl3GraphicsDevice getGraphicsDevice() {
        return display.getGraphics();
    }
    
    @Override
    public Jogl3Display getDisplay() {
        return display;
    }
    
    protected void initEngine() {
        display.show();
        // create GL context before continuing to init()
        try {
            synchronized (display.contextWait) {
                log.debug("Waiting for GLContext");
                display.contextWait.wait();
            }
            Thread.sleep(100);
        }
        catch (Exception e) {
            log.warning("Exception waiting for GLContext");
        }
    }

    protected void init() {
        try {
            display.invoke(new Runnable() {
                public void run() {
                    listener.onInit();
                }
            });
        } catch (Exception e) {
            log.fatal("Exception initializing JOGL Engine: " + EngineException.getStackTrace(e));
            e.printStackTrace();
        }
    }

    protected void update(final float dt) {
        try {
            display.invoke(new Runnable() {
                public void run() {
                    listener.onUpdate(dt);
                }
            });
        } catch (Exception e) {
            log.fatal("Exception updating JOGL Engine: " + EngineException.getStackTrace(e));
            e.printStackTrace();
        }
    }

    protected void render() {
        try {
            display.invoke(new Runnable() {
                public void run() {
                    GL gl = display.getContext().getGL();
                    gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
                    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
                    listener.onRender();
                }
            });
        } catch (Exception e) {
            log.fatal("Exception rendering JOGL Engine: " + EngineException.getStackTrace(e));
            e.printStackTrace();
        }
        display.swapBuffers();
    }

    protected void exit() {
        try {
            display.invoke(new Runnable() {
                public void run() {
                    log.debug("Exit engine");
                    listener.onExit();
                    display.hide();
                }
            });
        } catch (Exception e) {
            log.fatal("Exception exiting JOGL Engine: " + EngineException.getStackTrace(e));
            e.printStackTrace();
        }
    }

}