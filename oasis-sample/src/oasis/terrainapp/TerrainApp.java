package oasis.terrainapp;

import oasis.core.BackendType;
import oasis.core.BasicGame;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.terrainapp.behavior.ChunkManager;
import oasis.terrainapp.behavior.FpsCameraController;
import oasis.terrainapp.behavior.Gravity;
import oasis.terrainapp.behavior.Movement;
import oasis.terrainapp.behavior.SunLightRotator;
import oasis.terrainapp.component.FpsCamera;
import oasis.terrainapp.component.SunLightTag;
import oasis.terrainapp.component.Velocity;

public class TerrainApp extends BasicGame {

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
        
        ambientColor.set(0.2f); 
        
        entityManager.registerComponent(FpsCamera.class); 
        entityManager.registerComponent(SunLightTag.class); 
        entityManager.registerComponent(Velocity.class);
        
        entityManager.addBehavior(new Gravity()); 
        entityManager.addBehavior(new Movement());
        entityManager.addBehavior(new FpsCameraController()); 
        entityManager.addBehavior(new SunLightRotator()); 
        entityManager.addBehavior(new ChunkManager());
        
        addEntities(); 
        
        Oasis.getMouse().setCursorVisible(false); 
    }
    
    private void addEntities() {
        EntityFactory factory = new EntityFactory(entityManager); 
        
        factory.createCameraEntity(); 
        
        factory.createSunLightEntity(); 
        
//        final int RADIUS = 1; 
//        final float SIZE = TerrainGenerator.SIZE; 
//        
//        for (int x = -RADIUS; x <= RADIUS; x++) {
//            for (int y = -RADIUS; y <= RADIUS; y++) {
//                factory.createMeshEntity(new Vector3(x * SIZE, 0, y * SIZE), TerrainGenerator.generate(x, y, (int) SIZE / 4), Resources.grassMat); 
//            }
//        }
//        
//        Vector3 pos = new Vector3(); 
//        for (int i = 0; i < 20 * RADIUS * RADIUS; i++) {
//            pos.x = (float) (Math.random() * 2 - 1) * SIZE * RADIUS; 
//            pos.z = (float) (Math.random() * 2 - 1) * SIZE * RADIUS; 
//            pos.y = TerrainGenerator.heightAtPosition(pos.x, pos.z) - 0.5f; 
//            
//            factory.createMeshEntity(pos, Resources.treeMesh, Resources.woodMat); 
//            factory.createMeshEntity(pos, Resources.leavesMesh, Resources.leafMat); 
//        }
    }

}
