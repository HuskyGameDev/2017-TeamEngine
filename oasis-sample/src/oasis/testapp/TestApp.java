package oasis.testapp;

import oasis.core.BackendType;
import oasis.core.BasicGame;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.graphics.Material;
import oasis.math.Vector3;

public class TestApp extends BasicGame {

    private EntityFactory factory; 
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        conf.ups = 59.97f; 
        conf.fps = 60.0f; 
        
        Oasis.start(conf, new TestApp());
    }
    
    @Override
    public void initGame() {
        Resources.load(); 
        
        factory = new EntityFactory(entityManager); 
        
        ambientColor.set(0.2f); 
        
        entityManager.registerComponent(Spring.class); 
        entityManager.registerComponent(FpsCamera.class); 
        entityManager.registerComponent(SunLightTag.class); 
        
        entityManager.addBehavior(new SpringBehavior()); 
        entityManager.addBehavior(new FpsCameraController());
        entityManager.addBehavior(new SunLightRotator()); 
        
        populateScene(); 
        
        Oasis.getMouse().setCursorVisible(false); 
        
//        Resources.musicSource.play(); 
    }
    
    private void populateScene() {
        Material[] mats = new Material[] {
                Resources.stoneMat, 
                Resources.grassMat, 
                Resources.goldMat, 
                Resources.silverMat, 
                Resources.platinumMat, 
                Resources.bluePlasticMat, 
                Resources.pinkRubberMat, 
                Resources.emeraldMat
        }; 
        
        factory.createCameraEntity(); 
        factory.createSunLightEntity(); 
        factory.createMeshEntity(false, new Vector3(0, 0, 0), Resources.terrainMesh, Resources.grassMat);
        
        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                factory.createMeshEntity(true, new Vector3(i * 2.2f, 10, j * 2.2f), Resources.sphereMesh, mats[(int) (Math.random() * mats.length)]); 
            }
        }
    }

}
