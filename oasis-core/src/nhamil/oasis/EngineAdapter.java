package nhamil.oasis;

public abstract class EngineAdapter implements EngineSystem {

    public void onPreInit() {}
    public void onInit() {}
    public void onPostInit() {}
    
    public void onPreUpdate(float dt) {}
    public void onUpdate(float dt) {}
    public void onPostUpdate(float dt) {}
    
    public void onPreRender(float lerp) {}
    public void onRender(float lerp) {}
    public void onPostRender(float lerp) {}
    
    public void onPreExit() {}
    public void onExit() {}
    public void onPostExit() {}

}
