package nhamil.oasis.core.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.Threading;

import nhamil.oasis.core.Engine;
import nhamil.oasis.core.EngineListener;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.jogl.JoglDisplay;
import nhamil.oasis.graphics.jogl.JoglGraphicsContext;
import nhamil.oasis.util.Timer;

public class JoglEngine implements Engine, Runnable {

    private static final GameLogger log = new GameLogger(JoglEngine.class);

    private volatile boolean running;
    private EngineListener listener;
    private boolean resetLoop;
    private float targetFps;
    private float targetUps;
    private Thread thread;

    
    private JoglDisplay display;

    public JoglEngine() {
        running = false;
        listener = null;
        resetLoop = true;
        thread = null;
        targetFps = Engine.DEFAULT_FRAME_RATE;
        targetUps = Engine.DEFAULT_UPDATE_RATE;

        display = new JoglDisplay();
    }

    @Override
    public void start() {
        if (running) {
            log.warning("Engine is already running, cannot start another instance");
            return;
        }
        running = true;
        thread = new Thread(this, "JOGL Engine");
        display.show();
        thread.start();
    }

    @Override
    public void stop() {
        if (!running) {
            log.warning("Engine is already stopped, cannot stop");
            return;
        }
        running = false;
    }

    @Override
    public void setEngineListener(EngineListener listener) {
        this.listener = listener;
    }

    @Override
    public void setFrameRate(float fps) {
        targetFps = fps;
        resetLoop = true;
    }

    @Override
    public void setUpdateRate(float ups) {
        targetUps = ups;
        resetLoop = true;
    }

    @Override
    public JoglGraphicsContext getGraphics() {
        return display.getGraphics();
    }
    
    @Override
    public JoglDisplay getDisplay() {
        return display;
    }

    public void run() {
        try {
            synchronized (display.contextWait) {
                log.debug("Waiting for GLContext");
                display.contextWait.wait();
            }
        }
        catch (Exception e) {
            log.warning("Exception waiting for GLContext");
        }
        
        init();

        Timer secondTimer = new Timer();
        Timer time = new Timer();
        
        double tickTimer = 0.0;
        double skipTicks = 1.0 / targetUps;
        int ticks = 0;
        
        double frameTimer = 0.0;
        double skipFrames = 1.0 / targetFps;
        int frames = 0;
        
        while (running) {
            if (resetLoop) {
                resetLoop = false;
                
                tickTimer = time.getTime();
                skipTicks = 1.0 / targetUps;
                
                frameTimer = time.getTime();
                skipFrames = 1.0 / targetFps;
            }
            
            int loops = 0;
            while (tickTimer < time.getTime() && loops++ < 10) {
                update(0.0f);
                ticks++;
                tickTimer += skipTicks;
            }
            
            if (frameTimer < time.getTime()) {
                render();
                frames++;
                frameTimer += skipFrames;
            }
            
            if (secondTimer.getTime() >= 1.0) {
                secondTimer.reset();
                log.info("Time: " + String.format("%1.0f", time.getTime()) + "s, Frames: " + frames + ", Ticks: " + ticks);
                frames = ticks = 0;
            }
        }

        exit();
        System.exit(0);

        try {
            thread.join();
        } catch (InterruptedException e) {
            log.warning("Interrupted exception joining JOGL Engine thread");
        }
    }

    private void init() {
        try {
            Threading.invokeOnOpenGLThread(true, new Runnable() {
                public void run() {
                    display.updateGl();
                    listener.onInit();
                }
            });
        } catch (Exception e) {
            log.warning("Exception initializing JOGL Engine");
            e.printStackTrace();
        }
    }

    private void update(final float dt) {
        try {
            Threading.invokeOnOpenGLThread(true, new Runnable() {
                public void run() {
                    display.updateGl();
                    display.getGraphics().newFrame();
                    listener.onUpdate(dt);
                }
            });
        } catch (Exception e) {
            log.warning("Exception updating JOGL Engine");
            e.printStackTrace();
        }
    }

    private void render() {
        try {
            Threading.invokeOnOpenGLThread(true, new Runnable() {
                public void run() {
                    display.updateGl();
                    display.getGraphics().newFrame();
                    GL gl = display.getContext().getGL();
                    gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
                    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
                    listener.onRender();
                }
            });
        } catch (Exception e) {
            log.warning("Exception rendering JOGL Engine");
            e.printStackTrace();
        }
        display.swapBuffers();
    }

    private void exit() {
        try {
            Threading.invokeOnOpenGLThread(true, new Runnable() {
                public void run() {
                    display.updateGl();
                    log.debug("Exit engine");
                    listener.onExit();
                    display.hide();
                }
            });
        } catch (Exception e) {
            log.warning("Exception exitting JOGL Engine");
            e.printStackTrace();
        }
    }

}
