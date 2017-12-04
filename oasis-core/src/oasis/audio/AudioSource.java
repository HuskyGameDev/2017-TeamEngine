package oasis.audio;

import oasis.math.Vector3;

public interface AudioSource {

    void dispose(); 
    
    AudioBuffer getBuffer(); 
    
    void setBuffer(AudioBuffer buffer); 
    
    Vector3 getPosition(); 
    
    void setPosition(Vector3 pos); 
    
    void play(); 
    
    void stop(); 
    
    boolean isPlaying(); 
    
}
