package oasis.entity;

import oasis.core.Oasis;

public class GraphicsBegin extends Behavior {

    public static final int PRIORITY = 0; 
    
    public GraphicsBegin() {
        super(PRIORITY);
    }
    
    @Override
    public void render() {
        Oasis.getGraphics().begin(); 
    }

}
