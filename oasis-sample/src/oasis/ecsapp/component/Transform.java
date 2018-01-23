package oasis.ecsapp.component;

import oasis.entity.Component;

public class Transform extends Component {

    public float x, y; 
    
    public void activate() {
        x = y = 0; 
    }
    
}
