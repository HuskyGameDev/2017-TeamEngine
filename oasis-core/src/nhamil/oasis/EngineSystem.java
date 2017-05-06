package nhamil.oasis;

public interface EngineSystem {
    
    void onPreInit();
    void onInit();
    void onPostInit();
    
    void onPreUpdate(float dt);
    void onUpdate(float dt);
    void onPostUpdate(float dt);
    
    void onPreRender(float lerp);
    void onRender(float lerp);
    void onPostRender(float lerp);
    
    void onPreExit();
    void onExit();
    void onPostExit();
    
}
