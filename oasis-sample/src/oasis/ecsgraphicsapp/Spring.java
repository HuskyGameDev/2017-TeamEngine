package oasis.ecsgraphicsapp;

import oasis.entity.Component;
import oasis.math.Vector3;

public class Spring extends Component {

    public final Vector3 origin = new Vector3(); 
    public float offset = 0.0f; 
    public float time = 0.0f; 
    public float speed = 1.0f; 
    
    public void activate() {
        origin.set(0); 
        offset = 0; 
        time = 0; 
        speed = 1; 
    }
    
}
