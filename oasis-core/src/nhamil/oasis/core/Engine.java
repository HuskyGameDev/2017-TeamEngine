package nhamil.oasis.core;

import nhamil.oasis.graphics.Display;
import nhamil.oasis.graphics.GraphicsContext;

public interface Engine {

    public static final float DEFAULT_UPDATE_RATE = 60.0f;
    public static final float DEFAULT_FRAME_RATE = 60.0f;
    
    void start();
    
    void stop();
    
    void setEngineListener(EngineListener listener);
    
    void setFrameRate(float fps);
    
    void setUpdateRate(float ups);
    
    Display getDisplay();
    
    GraphicsContext getGraphics();
    
}
