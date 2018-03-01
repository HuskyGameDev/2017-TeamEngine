package oasis.terrainapp;

import oasis.core.BackendType;
import oasis.core.BasicGame;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.math.Vector3;
import oasis.scene.Scene;
import oasis.terrainapp.system.ChunkManager;
import oasis.terrainapp.system.FpsCameraController;
import oasis.terrainapp.system.Gravity;
import oasis.terrainapp.system.Movement;
import oasis.terrainapp.system.SunLightRotator;

public class TerrainApp extends BasicGame {

    private Scene scene; 
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        conf.ups = 59.97f; 
        conf.fps = 60.0f; 
        
        Oasis.start(conf, new TerrainApp());
    }
    
    @Override
    public void initGame() {
        Resources.load(); 
        
        scene = Oasis.getSceneManager().createAndSetScene("main_scene"); 
        
        scene.addSystem("Gravity", new Gravity()); 
        scene.addSystem("Movement", new Movement());
        scene.addSystem("FpsCameraController", new FpsCameraController()); 
        scene.addSystem("SunLightRotator", new SunLightRotator()); 
        scene.addSystem("ChunkManager", new ChunkManager());
        
        addEntities(); 
        
        Oasis.getMouse().setCursorVisible(false); 
        Oasis.getDisplay().setResizable(true); 
    }
    
    public void preRenderGame() {
        Oasis.getGraphics().addAmbient(new Vector3(0.2f)); 
    }
    
    private void addEntities() {
        EntityFactory factory = new EntityFactory(scene); 
        
        factory.createCameraEntity(); 
        
        factory.createSunLightEntity(); 
    }

}
