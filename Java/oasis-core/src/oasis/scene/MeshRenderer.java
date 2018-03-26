package oasis.scene;

import oasis.core.Oasis;
import oasis.graphics.Graphics;

public class MeshRenderer extends EntitySystem {

    public static final int PRIORITY = 100000; 
    
    private Graphics graphics; 
    
    public MeshRenderer() {
        super(PRIORITY, Transform.class, MeshContainer.class);
    }
    
    @Override
    public void preRender() {
        graphics = Oasis.getGraphics(); 
    }
    
    @Override 
    public void render(Entity e) {
        Transform t = e.get(Transform.class); 
        MeshContainer mc = e.get(MeshContainer.class); 
        
        graphics.drawMesh(mc.getMesh(), 0, mc.getMaterial(), t.getMatrix());
    }
    
}
