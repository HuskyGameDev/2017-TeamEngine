package nhamil.oasis.core;

import nhamil.oasis.audio.AudioSystem;
import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.input.InputSystem;

public interface Engine {

    public static final float DEFAULT_UPDATE_RATE = 60.0f;
    public static final float DEFAULT_FRAME_RATE = 60.0f;
    
    void start();
    
    void stop();
    
    void setEngineListener(EngineListener listener);
    
    void setFrameRate(float fps);
    
    void setUpdateRate(float ups);
    
    GraphicsSystem getGraphics();
    
    AudioSystem getAudio();
    
    InputSystem getInput();
    
}
