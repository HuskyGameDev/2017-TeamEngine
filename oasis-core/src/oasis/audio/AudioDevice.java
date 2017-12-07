package oasis.audio;

/**
 * 
 * Manages audio resources 
 * 
 * @author Nicholas Hamilton 
 *
 */
public interface AudioDevice {

    // resource creation
    
    /**
     * @return AudioListener 
     */
    AudioListener createListener(); 
    
    /**
     * @return AudioSource 
     */
    AudioSource createSource(); 
    
    /**
     * @param fmt audio format 
     * @return AudioBuffer with corresponding format 
     */
    AudioBuffer createBuffer(AudioFormat fmt); 
    
    /**
     * Loads an AudioBuffer with WAV data
     * 
     * @param file file name 
     * @return AudioBuffer with WAV data already set 
     */
    AudioBuffer loadWAVBuffer(String file); 
    
    // getters
    
    /**
     * @return the current listener 
     */
    AudioListener getListener(); 
    
    /**
     * @return audio source at index 
     */
    AudioSource getSource(int index); 
    
    /**
     * @return current source count 
     */
    int getSourceCount(); 
    
    /**
     * @return maximum number of current sources 
     */
    int getMaxSourceCount(); 
    
    // setters
    
    /**
     * set current listener 
     */
    void setListener(AudioListener listener); 
    
    /**
     * make a source current 
     */
    void addSource(AudioSource source);
    
    /**
     * make a source no longer current 
     */
    void removeSource(AudioSource source); 
    
    /** 
     * remove all sources form being current 
     */
    void clearSources(); 
    
}
