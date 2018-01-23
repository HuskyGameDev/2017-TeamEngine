package oasis.entity;

import oasis.core.Oasis;

public class GraphicsFinish extends Behavior {

    public static final int PRIORITY = Integer.MAX_VALUE; 
    
    public GraphicsFinish() {
        super(PRIORITY);
    }
    
    @Override
    public void render() {
        Oasis.getGraphics().finish(); 
    }

}
