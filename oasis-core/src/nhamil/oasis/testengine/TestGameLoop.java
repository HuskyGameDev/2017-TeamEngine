package nhamil.oasis.testengine;

import nhamil.oasis.GameLoop;

public class TestGameLoop extends GameLoop {

    @Override
    protected void run() {
        init();
        
        while (isRunning()) {
            update(0.0f);
            render(0.0f);
        }
        
        exit();
    }

}
