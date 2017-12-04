package oasis.audio;

import oasis.math.Vector3;

public interface AudioListener {

    void dispose(); 
    
    Vector3 getPosition(); 
    
    void setPosition(Vector3 pos); 
    
}
