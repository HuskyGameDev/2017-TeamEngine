package oasis.terrainapp;

import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.math.Mathf;
import oasis.math.Quaternion;
import oasis.math.Vector3;
import oasis.scene.Camera;
import oasis.scene.Entity;
import oasis.scene.Light;
import oasis.scene.MeshContainer;
import oasis.scene.Scene;
import oasis.scene.Transform;
import oasis.terrainapp.component.FpsCamera;
import oasis.terrainapp.component.SunLightTag;
import oasis.terrainapp.component.Velocity;

public class EntityFactory {

    private Scene scene; 
    
    public EntityFactory(Scene scene) {
        this.scene = scene; 
    }
    
    public Entity createSunLightEntity() {
        Entity e = scene.createEntity(); 
        
        Transform t = e.attach(new Transform()); 
        MeshContainer mc = e.attach(new MeshContainer()); 
        Light l = e.attach(new Light());  
        e.attach(new SunLightTag()); 
        
        mc.setMaterial(Resources.sunMat);
        mc.setMesh(Resources.sphereMesh);
        t.setRotation(Quaternion.direction(new Vector3(0, -1, -1)));
        l.setColor(new Vector3(0.8f));
        l.setType(Light.Type.DIRECTIONAL); 
        
        return e; 
    }
    
    public Entity createCameraEntity() {
        Entity e = scene.createEntity(); 
        
        Camera c = e.attach(new Camera()); 
        Transform t = e.attach(new Transform()); 
        e.attach(new Velocity()); 
        
        e.attach(new FpsCamera()); 
        
        t.setPosition(new Vector3(0, TerrainGenerator.heightAtPosition(0, 0) + 2, 0));
        c.setFov(Mathf.toRadians(70.0f)); 
        t.setRotation(Quaternion.direction(new Vector3(0, 0, -1f)));
        
        return e; 
    }
    
    public Entity createMeshEntity(Vector3 position, Mesh mesh, Material material) {
        Entity e = scene.createEntity(); 
        
        Transform t = e.attach(new Transform()); 
        MeshContainer mc = e.attach(new MeshContainer()); 
        
        t.setPosition(position); 
        mc.setMesh(mesh); 
        mc.setMaterial(material); 
        
        return e; 
    }
    
    public Entity createMeshEntity(Vector3 position, float angle, Mesh mesh, Material material) {
        Entity e = scene.createEntity(); 
        
        Transform t = e.attach(new Transform()); 
        MeshContainer mc = e.attach(new MeshContainer()); 
        
        t.setPosition(position); 
        t.setRotation(Quaternion.axisAngle(new Vector3(0, 1, 0), angle)); 
        mc.setMesh(mesh); 
        mc.setMaterial(material); 
        
        return e; 
    }
    
}
