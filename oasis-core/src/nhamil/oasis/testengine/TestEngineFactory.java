package nhamil.oasis.testengine;

import nhamil.oasis.Application;
import nhamil.oasis.Engine;

public class TestEngineFactory {

    public static Engine create(Application app) {
        return new Engine(new TestGameLoop(), null, null, null, app);
    }
    
}
