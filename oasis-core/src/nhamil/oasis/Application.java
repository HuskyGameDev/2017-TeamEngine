package nhamil.oasis;

import nhamil.oasis.graphics.GraphicsSystem;
import nhamil.oasis.input.InputSystem;
import nhamil.oasis.sound.SoundSystem;

public abstract class Application implements EngineSystem {

    protected Engine engine;
    protected GraphicsSystem graphics;
    protected SoundSystem sound;
    protected InputSystem input;

    public void setEngine(Engine engine) {
        this.engine = engine;
        this.graphics = engine.getGraphicsSystem();
        this.sound = engine.getSoundSystem();
        this.input = engine.getInputSystem();
    }

    @Override
    public void onPreInit() {}

    @Override
    public void onPostInit() {}

    @Override
    public void onPreUpdate(float dt) {}

    @Override
    public void onPostUpdate(float dt) {}

    @Override
    public void onPreRender(float lerp) {}

    @Override
    public void onPostRender(float lerp) {}

    @Override
    public void onPreExit() {}

    @Override
    public void onPostExit() {}

}
