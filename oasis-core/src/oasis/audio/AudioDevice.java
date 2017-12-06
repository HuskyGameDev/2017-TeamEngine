package oasis.audio;

public interface AudioDevice {

    // resource creation
    
    AudioListener createListener(); 
    AudioSource createSource(); 
    AudioBuffer createBuffer(AudioFormat fmt); 
    AudioBuffer loadWAVBuffer(String file); 
    
    // getters
    
    AudioListener getListener(); 
    AudioSource getSource(int index); 
    int getSourceCount(); 
    int getMaxSourceCount(); 
    
    // setters
    
    void setListener(AudioListener listener); 
    void addSource(AudioSource source);
    void removeSource(AudioSource source); 
    void clearSources(); 
    
}
