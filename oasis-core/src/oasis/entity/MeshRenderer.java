package oasis.entity;

import oasis.core.Oasis;
import oasis.graphics.Graphics;

public class MeshRenderer extends EntityBehavior {

    public static final int PRIORITY = 100000; 
    
    private ComponentId<Transform> transformId; 
    private ComponentId<MeshContainer> meshId; 
    
    private Graphics graphics; 
    
    public MeshRenderer() {
        super(PRIORITY, Transform.class, MeshContainer.class);
    }
    
    @Override
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
        meshId = em.getComponentId(MeshContainer.class); 
    }
    
    @Override
    public void preRender() {
        graphics = Oasis.getGraphics(); 
    }
    
    @Override 
    public void render(Entity e) {
        Transform t = e.get(transformId); 
        MeshContainer mc = e.get(meshId); 
        
        graphics.drawMesh(mc.getMesh(), 0, mc.getMaterial(), t.getMatrix());
    }
    
}
