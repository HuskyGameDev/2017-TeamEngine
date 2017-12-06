package oasis.audio;

import oasis.math.Quaternion;
import oasis.math.Vector3;

public interface AudioListener {

    void dispose(); 
    
    Vector3 getPosition(); 
    
    void setPosition(Vector3 pos); 
    
    Quaternion getOrientation(); 
    
    void setOrientation(Quaternion rot); 
    
}
