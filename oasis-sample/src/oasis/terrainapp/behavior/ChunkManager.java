package oasis.terrainapp.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.entity.EntityManager;
import oasis.entity.MeshContainer;
import oasis.entity.Transform;
import oasis.math.Vector2;
import oasis.math.Vector2i;
import oasis.math.Vector3;
import oasis.terrainapp.EntityFactory;
import oasis.terrainapp.Resources;
import oasis.terrainapp.TerrainGenerator;
import oasis.terrainapp.component.FpsCamera;
import oasis.util.QuickHash;

public class ChunkManager extends EntityBehavior {

    private class Chunk {
        public Vector2i pos = new Vector2i(); 
        Entity terrain; 
        List<Entity> trees = new ArrayList<>(); 
        
        public Chunk(Vector2i pos) {
            this.pos.set(pos); 
            
            final float SIZE = TerrainGenerator.SIZE; 
            
            terrain = factory.createMeshEntity(new Vector3(pos.x * SIZE, 0, pos.y * SIZE), TerrainGenerator.generate(pos.x, pos.y, (int) SIZE / 4), Resources.grassMat); 
        
            final int treeCount = QuickHash.compute(pos.x, pos.y) % 8; 
            
            Random r = new Random(QuickHash.compute(pos.x, pos.y)); 
            
            Vector3 p = new Vector3(); 
            for (int i = 0; i < treeCount; i++) {
                p.x = (float) (r.nextFloat() - 0.5f + pos.x) * SIZE; 
                p.z = (float) (r.nextFloat() - 0.5f + pos.y) * SIZE; 
                p.y = TerrainGenerator.heightAtPosition(p.x, p.z) - 0.5f; 
                
                trees.add(factory.createMeshEntity(p, Resources.leavesMesh, Resources.leafMat)); 
                trees.add(factory.createMeshEntity(p, Resources.treeMesh, Resources.woodMat)); 
            }
        }
        
        public void delete() {
            terrain.get(MeshContainer.class).getMesh().dispose(); 
            terrain.get(MeshContainer.class).setMesh(null); 
            getManager().destroyEntity(terrain); 
            for (int i = 0; i < trees.size(); i++) {
                getManager().destroyEntity(trees.get(i)); 
            }
        }
    } 
    
    public static final int RADIUS = 4; 
    
    private Map<Vector2i, Chunk> chunks = new HashMap<>(); 
    
    private EntityFactory factory; 
    
    public ChunkManager() {
        super(Transform.class, FpsCamera.class); 
    }
    
    public void updateIds(EntityManager em) {
        factory = new EntityFactory(em); 
    }
    
    public void update(Entity e, float dt) {
        Transform camTfm = e.get(Transform.class); 
        Vector3 camPos3 = camTfm.getPosition(); 
        Vector2 camPos = new Vector2(
                camPos3.x / TerrainGenerator.SIZE, 
                camPos3.z / TerrainGenerator.SIZE); 
        
        Chunk[] cs = chunks.values().toArray(new Chunk[chunks.values().size()]); 
        for (Chunk c : cs) {
            Vector2 pos = new Vector2(c.pos);  
            if (camPos.subtract(pos).length() > RADIUS) {
                c.delete(); 
                chunks.remove(c.pos); 
                System.out.println("delete");
                break; 
            }
        }
        
        gen: 
        for (int y = -RADIUS; y <= RADIUS; y++) {
            for (int x = -RADIUS; x <= RADIUS; x++) {
                Vector2 pos = new Vector2(camPos.x + x, camPos.y + y); 
                Vector2i key = new Vector2i((int) Math.floor(pos.x), (int) Math.floor(pos.y)); 
                pos.set(key.x, key.y); 
                
                if (camPos.subtract(pos).length() <= RADIUS && !chunks.containsKey(key)) {
                    Chunk c = new Chunk(key); 
                    chunks.put(key, c); 
                    break gen; 
                }
            }
        }
    }
    
}
