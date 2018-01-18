package oasis.core.jogl3;

import oasis.audio.AudioDevice;
import oasis.audio.jogl3.Jogl3DebugOal;
import oasis.audio.oal.OalAudioDevice;
import oasis.core.Backend;
import oasis.core.Config;
import oasis.core.DefaultDirectBufferAllocator;
import oasis.core.DirectBufferAllocator;
import oasis.core.Display;
import oasis.core.Logger;
import oasis.graphics.GraphicsDevice;
import oasis.input.Keyboard;
import oasis.input.Mouse;

public class Jogl3Backend implements Backend {

    private static final Logger log = new Logger(Jogl3Backend.class); 
    
    private Jogl3Window window; 
    private DirectBufferAllocator alloc; 
    private OalAudioDevice oal; 
    
    public Jogl3Backend() {
        window = new Jogl3Window(); 
        alloc = new DefaultDirectBufferAllocator(); 
        oal = new OalAudioDevice(new Jogl3DebugOal()); 
    }
    
    @Override
    public void applyConfig(Config config) {
        window.setSize(config.width, config.height); 
    }

    @Override
    public void preInit() {
        log.debug("preInit"); 
        window.show(); 
        window.getCanvas().setFocusable(true); 
        window.getCanvas().requestFocus(); 
    }

    @Override
    public void postInit() {
        // TODO Auto-generated method stub
        log.debug("postInit");
    }

    @Override
    public void preUpdate(float dt) {
        window.getKeyboard().update(); 
        window.getMouse().update(); 
    }

    @Override
    public void postUpdate(float dt) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preRender() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postRender() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preExit() {
        // TODO Auto-generated method stub
        log.debug("preExit"); 
    }

    @Override
    public void postExit() {
        log.debug("postExit"); 
        window.hide(); 
    }

    @Override
    public void runOnMainThread(Runnable r) {
        window.invoke(r); 
    }

    @Override
    public DirectBufferAllocator getDirectBufferAllocator() {
        return alloc; 
    }

    @Override
    public Display getDisplay() {
        return window; 
    }

    @Override
    public Keyboard getKeyboard() {
        return window.getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return window.getMouse();
    }

    @Override
    public GraphicsDevice getGraphicsDevice() {
        return window.getGraphics(); 
    }

    @Override
    public AudioDevice getAudioDevice() {
        return oal; 
    }

}
