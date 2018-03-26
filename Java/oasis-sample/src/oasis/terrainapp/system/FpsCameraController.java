package oasis.terrainapp.system;

import oasis.core.Oasis;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Quaternion;
import oasis.math.Vector3;
import oasis.scene.Camera;
import oasis.scene.Entity;
import oasis.scene.EntitySystem;
import oasis.scene.Transform;
import oasis.terrainapp.TerrainGenerator;
import oasis.terrainapp.component.FpsCamera;
import oasis.terrainapp.component.Velocity;

public class FpsCameraController extends EntitySystem {

    public FpsCameraController() {
        super(DEFAULT_PRIORITY, Transform.class, Camera.class, FpsCamera.class, Velocity.class); 
    }
    
    @Override
    public void render(Entity e) {
        Transform t = e.get(Transform.class); 
        Camera c = e.get(Camera.class);  
        
        Oasis.getGraphics().setCamera(c, t);
    }
    
    @Override
    public void update(Entity e, float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        Mouse mouse = Oasis.getMouse(); 
        
        Transform tfm = e.get(Transform.class); 
        FpsCamera cam = e.get(FpsCamera.class);  
        Velocity vel = e.get(Velocity.class);  
        
        Vector3 camPos = tfm.getPosition(); 
        Quaternion camRot = tfm.getRotation(); 
        
        Vector3 dir = new Vector3(); 
        
        if (keys.isKeyDown(Keyboard.KEY_I) || keys.isKeyDown(Keyboard.KEY_W)) {
            dir.addSelf(new Vector3(0, 0, -1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_K) || keys.isKeyDown(Keyboard.KEY_S)) {
            dir.addSelf(new Vector3(0, 0, 1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_J) || keys.isKeyDown(Keyboard.KEY_A)) {
            dir.addSelf(new Vector3(-1, 0, 0)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_L) || keys.isKeyDown(Keyboard.KEY_D)) {
            dir.addSelf(new Vector3(1, 0, 0)); 
        }
        
        float scale = 1.0f; 
        
        if (keys.isKeyDown(Keyboard.KEY_SHIFT)) {
            scale *= 16.0f; 
        }
        
        float minY = TerrainGenerator.heightAtPosition(camPos.x, camPos.z) + 2; 
        
        dir.rotateSelf(camRot).setY(0); 
        
        if (camPos.y < minY) {
            camPos.y = minY; 
            vel.velocity.y = 0; 
        }
        
        if (keys.isKeyJustDown(Keyboard.KEY_SPACE)) {
//            System.out.println("just down");
            vel.velocity.addSelf(new Vector3(0, 10, 0)); 
        }
//        if (keys.isKeyDown(Keyboard.KEY_SHIFT)) {
//            dir.addSelf(new Vector3(0, -1, 0)); 
//        }
        
        dir.normalizeSelf().multiplySelf(8.5f * dt * scale); 
        camPos.addSelf(dir); 
        
        tfm.setPosition(camPos); 
//        listener.setPosition(camera.getPosition()); 
        
        cam.yaw += -mouse.getDx() * 0.001f; 
        cam.pitch += -mouse.getDy() * 0.001f; 
        Quaternion q = new Quaternion(); 
        Camera.setRotation(cam.yaw, cam.pitch, q);
        tfm.setRotation(q);
        
        mouse.center(); 
    }
    
}
