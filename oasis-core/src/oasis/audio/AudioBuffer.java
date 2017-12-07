package oasis.audio;

/**
 * 
 * Holds PCM audio data 
 * 
 * Do NOT implement this, only use AudioBuffers created from 
 * AudioDevice
 * 
 * @author Nicholas Hamilton 
 *
 */
public interface AudioBuffer {

    /**
     * Dispose resource
     */
    void dispose(); 
    
    /**
     * @return Audio format used by buffer 
     */
    AudioFormat getFormat(); 
    
    /**
     * Set buffer data 
     * 
     * @param data data in bytes
     * @param freq frequency
     */
    void setData(byte[] data, int freq); 
    
    /**
     * Set buffer data 
     * 
     * @param data data in shorts
     * @param freq frequency
     */
    void setData(short[] data, int freq); 
    
}
