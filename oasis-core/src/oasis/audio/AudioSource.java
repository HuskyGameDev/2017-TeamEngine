package oasis.audio;

import oasis.math.Vector3;

public interface AudioSource {

    void dispose(); 
    
    AudioBuffer getBuffer(); 
    
    void setBuffer(AudioBuffer buffer); 
    
    Vector3 getPosition(); 
    
    void setPosition(Vector3 pos); 
    
    void play(); 
    
    void pause(); 
    
    void stop(); 
    
    boolean isPlaying(); 
    
    void setLooping(boolean loop); 
    
    boolean isLooping(); 
    
    void setGain(float gain); 
    
    float getGain(); 
    
    void setPitch(float pitch);
    
    float getPitch(); 
    
}
