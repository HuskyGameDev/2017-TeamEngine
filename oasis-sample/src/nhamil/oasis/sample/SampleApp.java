package nhamil.oasis.sample;

import nhamil.oasis.Application;
import nhamil.oasis.Engine;
import nhamil.oasis.GameLogger;
import nhamil.oasis.Oasis;
import nhamil.oasis.testengine.TestEngineFactory;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        log.info("Sound System: " + sound);
        log.info("Input System: " + input);
        log.info("Done!");
    }

    @Override
    public void onUpdate(float dt) {
        engine.stop();
    }

    @Override
    public void onRender(float lerp) {
        
    }

    @Override
    public void onExit() {
        log.info("Closing application");
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        SampleApp app = new SampleApp();
        Engine engine = TestEngineFactory.create(app);
        engine.start();
    }

}
