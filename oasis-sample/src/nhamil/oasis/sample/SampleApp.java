package nhamil.oasis.sample;

import nhamil.oasis.core.Application;
import nhamil.oasis.core.Config;
import nhamil.oasis.core.EngineType;
import nhamil.oasis.core.GameLogger;
import nhamil.oasis.core.Oasis;

public class SampleApp extends Application {

    private static final GameLogger log = new GameLogger(SampleApp.class);
    
    @Override
    public void onInit() {
        log.info("Initializing...");
        log.info("Graphics System: " + graphics);
        log.info("Sound System: " + audio);
        log.info("Input System: " + input);
        log.info("Done!");
    }

    @Override
    public void onUpdate(float dt) {
        log.debug("Update: dt = " + dt);
        stop();
    }

    @Override
    public void onRender(float lerp) {
        log.debug("Render: lerp = " + lerp);
    }

    @Override
    public void onExit() {
        log.info("Closing application");
    }
    
    public static void main(String[] args) {
        log.info(Oasis.getEngineInfo());
        
        Config cfg = new Config();
        cfg.engine = EngineType.TEST;
        cfg.fps = 60.0f;
        cfg.ups = 60.0f;
        
        Application app = new SampleApp();
        app.start(cfg);
    }

}
