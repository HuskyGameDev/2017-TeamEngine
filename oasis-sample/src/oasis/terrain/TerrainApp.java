package oasis.terrain;

import oasis.core.Application;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.graphics.ColorRgba;

public class TerrainApp extends Application {

    public static void main(String[] args) {
        Config cfg = new Config(); 
        cfg.engine = Jogl3Engine.class; 
        cfg.fps = 60; 
        cfg.ups = 60; 
        cfg.width = 800; 
        cfg.height = 600; 
        
        Application app = new TerrainApp(); 
        app.start(cfg); 
    }

    @Override
    public void onInit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUpdate(float dt) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRender() {
        Oasis.graphics.clearScreen(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f)); 
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        
    }
    
}
