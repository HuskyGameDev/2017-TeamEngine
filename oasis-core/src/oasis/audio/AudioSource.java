package oasis.audio;

import oasis.math.Vector3;

/**
 * 
 * Holds and plays AudioBuffers 
 * 
 * Do NOT implement this, instead use AudioSources provided
 * by AudioDevice 
 * 
 * @author Nicholas Hamilton 
 *
 */

// TODO have method to check if current
public interface AudioSource {

    /**
     * Dispose of resource 
     */
    void dispose(); 
    
    /**
     * @return current audio buffer
     */
    AudioBuffer getBuffer(); 
    
    /**
     * set current audio buffer 
     */
    void setBuffer(AudioBuffer buffer); 
    
    /**
     * @return position in world 
     */
    Vector3 getPosition(); 
    
    /**
     * set position in world 
     */
    void setPosition(Vector3 pos); 
    
    /**
     * play sound, only works if source is added to AudioDevice 
     */
    void play(); 
    
    /**
     * pause sound, keeps current position in buffer 
     */
    void pause(); 
    
    /**
     * stop sound, resets position in buffer 
     */
    void stop(); 
    
    /**
     * @return if sound is playing 
     */
    boolean isPlaying(); 
    
    /**
     * set if sound should loop
     */
    void setLooping(boolean loop); 
    
    /**
     * @return is sound should loop
     */
    boolean isLooping(); 
    
    /**
     * set volume between 0 and 1 
     */
    void setGain(float gain); 
    
    /**
     * @return volume 
     */
    float getGain(); 
    
    /**
     * set pitch of sound, default is 1
     */
    void setPitch(float pitch);
    
    /**
     * @return pitch of sound 
     */
    float getPitch(); 
    
    /**
     * Set minimum distance, any closer than this
     * and the sound is played at the set gain level. 
     * 
     * Only works for MONO8 and MONO16 buffers 
     * 
     * @param dist distance 
     */
    void setMinDistance(float dist); 
    
    /**
     * @return distance for set volume 
     */
    float getMinDistance(); 
    
    /**
     * Set maximum distance, any farther and the 
     * sound cannot be heard. 
     * 
     * Only works for MONO8 and MONO16 buffers 
     * 
     * @param dist distance 
     */
    void setMaxDistance(float dist); 
    
    /**
     * @return distance for sound to be inaudible 
     */
    float getMaxDistance(); 
    
}
