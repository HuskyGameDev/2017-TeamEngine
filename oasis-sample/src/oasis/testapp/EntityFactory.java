package oasis.testapp;

import oasis.entity.Camera;
import oasis.entity.Entity;
import oasis.entity.EntityManager;
import oasis.entity.Light;
import oasis.entity.MeshContainer;
import oasis.entity.Transform;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.math.Mathf;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class EntityFactory {

    private EntityManager em; 
    
    public EntityFactory(EntityManager em) {
        this.em = em; 
    }
    
    public Entity createSunLightEntity() {
        Entity e = em.createEntity(); 
        
        Transform t = e.add(Transform.class); 
        Light l = e.add(Light.class); 
        e.add(SunLightTag.class); 
        
        t.setRotation(Quaternion.direction(new Vector3(0, -1, -1)));
        l.setColor(new Vector3(0.8f));
        l.setType(Light.Type.DIRECTIONAL); 
        
        return e; 
    }
    
    public Entity createCameraEntity() {
        Entity e = em.createEntity(); 
        
        Camera c = e.add(Camera.class); 
        Transform t = e.add(Transform.class); 
        e.add(FpsCamera.class); 
        
        t.setPosition(new Vector3(0, 3, 10));
        c.setFov(Mathf.toRadians(70.0f)); 
        t.setRotation(Quaternion.direction(new Vector3(0, 0, -1f)));
        
        return e; 
    }
    
    public Entity createMeshEntity(boolean move, Vector3 position, Mesh mesh, Material material) {
        Entity e = em.createEntity(); 
        
        if (move) {
            Spring s = e.add(Spring.class); 
            s.origin.set(position); 
            s.time = (float) Math.random() * 10; 
            s.speed = (float) Math.random(); 
        }
        
        Transform t = e.add(Transform.class); 
        MeshContainer mc = e.add(MeshContainer.class); 
        
        t.setPosition(position); 
        mc.setMesh(mesh); 
        mc.setMaterial(material); 
        
        return e; 
    }
    
}
