package oasis.audio;

import oasis.core.Oasis;
import oasis.math.Vector3;

/**
 * 
 * Wraps an AudioSource
 * 
 * @author Nicholas Hamilton 
 *
 */

// TODO streamed sounds 
public class Sound {

    private AudioSource source; 
    
    /**
     * Create a sound from a buffer 
     */
    public Sound(AudioBuffer buffer) {
        source = Oasis.audio.createSource(); 
        source.setBuffer(buffer); 
    }
    
    /**
     * Create a sound from a WAV file 
     */
    public Sound(String file) {
        source = Oasis.audio.createSource(); 
        source.setBuffer(Oasis.audio.loadWAVBuffer(file));
    }
    
    /**
     * @return sound format 
     */
    public AudioFormat getFormat() {
        return source.getBuffer().getFormat(); 
    }
    
    /**
     * AudioSource.setMinDistance()
     */
    public void setMinDistance(float dist) {
        source.setMinDistance(dist); 
    }
    
    /**
     * @return AudioSource.getMinDistance()
     */
    public float getMinDistance() {
        return source.getMinDistance(); 
    }
    
    /**
     * AudioSource.setMaxDistance()
     */
    public void setMaxDistance(float dist) {
        source.setMaxDistance(dist); 
    }
    
    /**
     * @return AudioSource.getMaxDistance()
     */
    public float getMaxDistance() {
        return source.getMaxDistance(); 
    }
    
    /**
     * AudioSource.setPosition()
     */
    public void setPosition(Vector3 pos) {
        source.setPosition(pos); 
    }
    
    /**
     * @return AudioSource.getPosition()
     */
    public Vector3 getPosition() {
        return source.getPosition(); 
    }
    
    /**
     * AudioSource.setGain()
     */
    public void setGain(float gain) {
        source.setGain(gain); 
    }
    
    /**
     * @return AudioSource.getGain()
     */
    public float getGain() {
        return source.getGain(); 
    }
    
    /**
     * Change gain by [dg] 
     */
    public void changeGain(float dg) {
        source.setGain(source.getGain() + dg);
    }
    
    /**
     * AudioSource.setPitch() 
     */
    public void setPitch(float pitch) {
        source.setPitch(pitch); 
    }
    
    /**
     * @return AudioSource.getPitch() 
     */
    public float getPitch() {
        return source.getPitch(); 
    }
    
    /**
     * AudioSource.setLooping() 
     */
    public void setLooping(boolean loop) {
        source.setLooping(loop); 
    }
    
    /**
     * @return AudioSource.isLooping()
     */
    public boolean isLooping() {
        return source.isLooping(); 
    }
    
    /**
     * AudioSource.play()
     */
    public void play() {
        Oasis.audio.addSource(source); 
        source.play(); 
    }
    
    /**
     * AudioSource.stop()
     */
    public void stop() {
        source.stop(); 
        Oasis.audio.removeSource(source);
    }
    
    /**
     * AudioSource.pause()
     */
    public void pause() {
        source.pause(); 
        Oasis.audio.removeSource(source); 
    }
    
    /**
     * @return AudioSource.isPlaying() 
     */
    public boolean isPlaying() {
        return source.isPlaying(); 
    }
    
}
