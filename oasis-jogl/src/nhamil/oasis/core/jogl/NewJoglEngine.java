//package nhamil.oasis.core.jogl;
//
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GL3;
//import com.jogamp.opengl.Threading;
//
//import nhamil.oasis.audio.AudioSystem;
//import nhamil.oasis.core.Engine;
//import nhamil.oasis.core.EngineListener;
//import nhamil.oasis.core.GameLogger;
//import nhamil.oasis.core.test.TestAudio;
//import nhamil.oasis.core.test.TestInput;
//import nhamil.oasis.graphics.GraphicsSystem;
//import nhamil.oasis.graphics.jogl.NewJoglDisplay;
//import nhamil.oasis.input.InputSystem;
//import nhamil.oasis.util.Timer;
//
//public class NewJoglEngine implements Engine, Runnable {
//
//    private static final GameLogger log = new GameLogger(NewJoglEngine.class);
//
//    private volatile boolean running;
//    private EngineListener listener;
//    private boolean resetLoop;
//    private float targetFps;
//    private float targetUps;
//    private Thread thread;
//    
//    private NewJoglDisplay display;
//    
//    private Object contextWait = new Object();
//    
//    public NewJoglEngine() {
//        display = new NewJoglDisplay();
//    }
//    
//    @Override
//    public void start() {
//        if (running) {
//            log.warning("Engine is already running, cannot start another instance");
//            return;
//        }
//        running = true;
//        thread = new Thread(this, "JOGL Engine");
//        thread.start();
//    }
//
//    @Override
//    public void stop() {
//        if (!running) {
//            log.warning("Engine is already stopped, cannot stop");
//            return;
//        }
//        running = false;
//    }
//
//    @Override
//    public void setEngineListener(EngineListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    public void setFrameRate(float fps) {
//        targetFps = fps;
//        resetLoop = true;
//    }
//
//    @Override
//    public void setUpdateRate(float ups) {
//        targetUps = ups;
//        resetLoop = true;
//    }
//
//    @Override
//    public GraphicsDevice getGraphics() {
//        return null;
//    }
//
//    @Override
//    public AudioSystem getAudio() {
//        return new TestAudio();
//    }
//
//    @Override
//    public InputSystem getInput() {
//        return new TestInput();
//    }
//
//    public void run() {
//        try {
//            synchronized (contextWait) {
//                log.debug("Waiting for GLContext");
//                contextWait.wait();
//            }
//        }
//        catch (Exception e) {
//            log.warning("Exception waiting for GLContext");
//        }
//        
//        init();
//
//        Timer secondTimer = new Timer();
//        Timer time = new Timer();
//        
//        double tickTimer = 0.0;
//        double skipTicks = 1.0 / targetUps;
//        int ticks = 0;
//        
//        double frameTimer = 0.0;
//        double skipFrames = 1.0 / targetFps;
//        int frames = 0;
//        
//        while (running) {
//            if (display.shouldClose()) {
//                stop();
//            }
//            
//            if (resetLoop) {
//                resetLoop = false;
//                
//                tickTimer = time.getTime();
//                skipTicks = 1.0 / targetUps;
//                
//                frameTimer = time.getTime();
//                skipFrames = 1.0 / targetFps;
//            }
//            
//            int loops = 0;
//            while (tickTimer < time.getTime() && loops++ < 10) {
//                update(0.0f);
//                ticks++;
//                tickTimer += skipTicks;
//            }
//            
//            if (frameTimer < time.getTime()) {
//                render();
//                frames++;
//                frameTimer += skipFrames;
//            }
//            
//            if (secondTimer.getTime() >= 1.0) {
//                secondTimer.reset();
//                log.info("Time: " + String.format("%1.0f", time.getTime()) + "s, Frames: " + frames + ", Ticks: " + ticks);
//                frames = ticks = 0;
//            }
//        }
//
//        exit();
//
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            log.warning("Interrupted exception exiting JOGL Engine thread");
//        }
//    }
//    
//    private void init() {
//        try {
//            Threading.invokeOnOpenGLThread(true, new Runnable() {
//                public void run() {
//                    display.getContext().makeCurrent();
//                    listener.onInit();
//                }
//            });
//        } catch (Exception e) {
//            log.warning("Exception initializing JOGL Engine");
//            e.printStackTrace();
//        }
//    }
//
//    private void update(final float dt) {
//        try {
//            Threading.invokeOnOpenGLThread(true, new Runnable() {
//                public void run() {
//                    display.getContext().makeCurrent();
//                    listener.onUpdate(dt);
//                }
//            });
//        } catch (Exception e) {
//            log.warning("Exception updating JOGL Engine");
//            e.printStackTrace();
//        }
//    }
//
//    private void render() {
//        try {
//            Threading.invokeOnOpenGLThread(true, new Runnable() {
//                public void run() {
//                    display.getContext().makeCurrent();
//                    GL3 gl = display.getContext().getGL().getGL3();
//                    gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
//                    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
//                    listener.onRender();
//                }
//            });
//        } catch (Exception e) {
//            log.warning("Exception rendering JOGL Engine");
//            e.printStackTrace();
//        }
//        display.swapBuffers();
//    }
//
//    private void exit() {
//        try {
//            Threading.invokeOnOpenGLThread(true, new Runnable() {
//                public void run() {
//                    display.getContext().makeCurrent();
//                    log.debug("Exit engine");
//                    listener.onExit();
//                    System.exit(0);
//                }
//            });
//        } catch (Exception e) {
//            log.warning("Exception exitting JOGL Engine");
//            e.printStackTrace();
//        }
//    }
//
//}
