package oasis.ecsapp.component;

import oasis.entity.Component;

public class Velocity extends Component {

    public float dx, dy; 
    
    public void activate() {
        dx = dy = 0; 
    } 
    
}
