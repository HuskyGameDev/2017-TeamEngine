package oasis.testapp;

import oasis.core.Oasis;
import oasis.entity.Camera;
import oasis.entity.ComponentId;
import oasis.entity.Entity;
import oasis.entity.EntityBehavior;
import oasis.entity.EntityManager;
import oasis.entity.Transform;
import oasis.input.Keyboard;
import oasis.input.Mouse;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class FpsCameraController extends EntityBehavior {

    private ComponentId<Transform> transformId; 
    private ComponentId<FpsCamera> fpsCameraId; 
    private ComponentId<Camera> cameraId; 
    
    public FpsCameraController() {
        super(DEFAULT_PRIORITY, Transform.class, Camera.class, FpsCamera.class); 
    }
    
    @Override
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
        fpsCameraId = em.getComponentId(FpsCamera.class); 
        cameraId = em.getComponentId(Camera.class); 
    }
    
    @Override
    public void render(Entity e) {
        Transform t = e.get(transformId); 
        Camera c = e.get(cameraId); 
        
        Oasis.getGraphics().setCamera(c, t);
    }
    
    @Override
    public void update(Entity e, float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        Mouse mouse = Oasis.getMouse(); 
        
        Transform tfm = e.get(transformId); 
        FpsCamera cam = e.get(fpsCameraId); 
        
        Vector3 camPos = tfm.getPosition(); 
        Quaternion camRot = tfm.getRotation(); 
        
        Vector3 dir = new Vector3(); 
        
        if (keys.isKeyDown(Keyboard.KEY_I)) {
            dir.addSelf(new Vector3(0, 0, -1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_K)) {
            dir.addSelf(new Vector3(0, 0, 1)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_J)) {
            dir.addSelf(new Vector3(-1, 0, 0)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_L)) {
            dir.addSelf(new Vector3(1, 0, 0)); 
        }
        
        dir.rotateSelf(camRot).setY(0); 
        
        if (keys.isKeyDown(Keyboard.KEY_SPACE)) {
            dir.addSelf(new Vector3(0, 1, 0)); 
        }
        if (keys.isKeyDown(Keyboard.KEY_SHIFT)) {
            dir.addSelf(new Vector3(0, -1, 0)); 
        }
        
        dir.normalizeSelf().multiplySelf(30 * dt); 
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
