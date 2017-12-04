package oasis.audio;

import oasis.math.Vector3f;

public interface AudioListener {

    void dispose(); 
    
    Vector3f getPosition(); 
    
    void setPosition(Vector3f pos); 
    
}
