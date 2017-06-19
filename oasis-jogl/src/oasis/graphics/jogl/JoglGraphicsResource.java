package oasis.graphics.jogl;

public abstract class JoglGraphicsResource {

    protected JoglGraphicsDevice graphics; 
    
    public JoglGraphicsResource(JoglGraphicsDevice graphics) { 
        this.graphics = graphics; 
    }
    
}
