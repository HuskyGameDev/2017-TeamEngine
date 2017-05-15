package nhamil.oasis.core;

public interface EngineListener {

    void onInit();
    
    void onUpdate(float dt);
    
    void onRender();
    
    void onExit();
    
}
