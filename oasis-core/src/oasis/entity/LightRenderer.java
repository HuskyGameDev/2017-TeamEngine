package oasis.entity;

import oasis.core.Oasis;

public class LightRenderer extends EntityBehavior {

    public static final int PRIORITY = 1; 
    
    private ComponentId<Transform> transformId; 
    private ComponentId<Light> lightId; 
    
    public LightRenderer() {
        super(PRIORITY, Transform.class, Light.class); 
    }
    
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
        lightId = em.getComponentId(Light.class); 
    }
    
    public void render(Entity e) {
        Oasis.getGraphics().addLight(e.get(lightId), e.get(transformId));
    }
    
}
