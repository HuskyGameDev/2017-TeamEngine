package oasis.scene;

import oasis.core.Oasis;

public class LightRenderer extends EntitySystem {

    public static final int PRIORITY = 1; 
    
    public LightRenderer() {
        super(PRIORITY, Transform.class, Light.class); 
    }
    
    public void render(Entity e) {
        Oasis.getGraphics().addLight(e.get(Light.class), e.get(Transform.class));
    }
    
}
