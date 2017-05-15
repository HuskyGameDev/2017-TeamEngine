package nhamil.oasis.core.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.Threading;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.core.Engine;
import nhamil.oasis.core.EngineListener;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.test.TestAudio;
import nhamil.oasis.core.test.TestInput;
import nhamil.oasis.graphics.jogl.JoglDisplay;
import nhamil.oasis.graphics.jogl.JoglGraphicsSystem;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.util.Timer;

public class JoglEngine implements Engine, GLEventListener, Runnable {

    public static GLContext context = null;
    
    private static final GameLogger log = new GameLogger(JoglEngine.class);

    private volatile boolean running;
    private EngineListener listener;
    private boolean resetLoop;
    private float targetFps;
    private float targetUps;
    private Thread thread;

    private JoglGraphicsSystem graphics;
    private JoglDisplay display;

    private Object contextWait = new Object();
    
    public JoglEngine() {
        running = false;
        listener = null;
        resetLoop = true;
        thread = null;
        targetFps = Engine.DEFAULT_FRAME_RATE;
        targetUps = Engine.DEFAULT_UPDATE_RATE;

        graphics = new JoglGraphicsSystem(this);
        display = graphics.getDisplay();
        
        context = display.getContext();
        
        log.debug("Assigned OpenGL context: " + context);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        log.debug("Initializing GLCanvas");
        
        GL3 gl = drawable.getGL().getGL3();
        gl.setSwapInterval(0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        synchronized (contextWait) {
            context = drawable.getContext();
            graphics.setGL(context.getGL());
            
            log.debug("Changed context: " + context.getGLVersion());
            contextWait.notify();
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        graphics.setGL(context.getGL().getGL3());
        log.debug("Disposing GLCanvas");
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    @Override
    public void start() {
        if (running) {
            log.warning("Engine is already running, cannot start another instance");
            return;
        }
        running = true;
        thread = new Thread(this, "JOGL Engine");
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
    public JoglGraphicsSystem getGraphics() {
        return graphics;
    }

    @Override
    public AudioSystem getAudio() {
        return new TestAudio();
    }

    @Override
    public InputSystem getInput() {
        return new TestInput();
    }

    public void run() {
        try {
            synchronized (contextWait) {
                log.debug("Waiting for GLContext");
                contextWait.wait();
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
                    context.makeCurrent();
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
                    context.makeCurrent();
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
                    context.makeCurrent();
                    GL3 gl = context.getGL().getGL3();
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
                    context.makeCurrent();
                    log.debug("Exit engine");
                    listener.onExit();
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            log.warning("Exception exitting JOGL Engine");
            e.printStackTrace();
        }
    }

}
