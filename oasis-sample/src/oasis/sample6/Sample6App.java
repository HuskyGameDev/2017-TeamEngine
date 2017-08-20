package oasis.sample6;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.jogl.Jogl3Engine;

public class Sample6App extends Application {

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate(float dt) {
    }

    @Override
    public void onRender() {
    }

    @Override
    public void onExit() {
    }
    
    public static void main(String[] args) {
        Config config = new Config(); 
        config.engine = Jogl3Engine.class; 
        config.fps = 60.0f; 
        config.ups = 60.0f; 
        Application app = new Sample6App(); 
        app.start(config);
    }

}
