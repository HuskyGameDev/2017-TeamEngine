package oasis.audio;

public interface AudioDevice {

    // resource creation
    
    Listener createListener(); 
    Sound createSound(); 
    
    // getters
    
    Listener getListener(); 
    Sound getSound(int index); 
    int getMaxSoundCount(); 
    
    // setters
    
    void setListener(Listener listener); 
    void setSound(int index, Sound source); 
    void clearSounds(); 
    
}
