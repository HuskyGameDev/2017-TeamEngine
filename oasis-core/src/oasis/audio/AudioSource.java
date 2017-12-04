package oasis.audio;

import oasis.math.Vector3f;

public interface AudioSource {

    void dispose(); 
    
    AudioBuffer getBuffer(); 
    
    void setBuffer(AudioBuffer buffer); 
    
    Vector3f getPosition(); 
    
    void setPosition(Vector3f pos); 
    
    void play(); 
    
    void stop(); 
    
    boolean isPlaying(); 
    
}
