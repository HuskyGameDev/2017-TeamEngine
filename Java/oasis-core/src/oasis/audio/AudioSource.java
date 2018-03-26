package oasis.audio;

import oasis.core.Oasis;
import oasis.math.Vector3;

/**
 * 
 * Source of audio in the scene 
 * 
 * @author Nicholas Hamilton 
 *
 */
public abstract class AudioSource {

    protected final Vector3 position = new Vector3(); 
    protected boolean loop = false; 
    protected float gain = 1.0f; 
    protected float pitch = 1.0f; 
    protected float minDistance = 1.0f; 
    protected float maxDistance = 100.0f; 
    protected AudioAttenuation atten = AudioAttenuation.LINEAR; 
    
    protected AudioClip clip; 
    
    public static AudioSource create() {
        return Oasis.getAudioDevice().createSource(); 
    }
    
    public abstract void play(); 
    
    public abstract void stop(); 
    
    public abstract void pause(); 
    
    public abstract boolean isPlaying(); 
    
    public AudioAttenuation getAttenuation() {
        return atten; 
    }
    
    public Vector3 getPosition() {
        return new Vector3(position); 
    }
    
    public boolean shouldLoop() {
        return loop; 
    }
    
    public float getGain() {
        return gain; 
    }
    
    public float getPitch() {
        return pitch; 
    }
    
    public float getMinDistance() {
        return minDistance; 
    }
    
    public float getMaxDistance() {
        return maxDistance; 
    }
    
    public AudioClip getClip() {
        return clip; 
    }
    
    public void setPosition(Vector3 pos) {
        position.set(pos); 
    }
    
    public void setLoop(boolean loop) {
        this.loop = loop; 
    }
    
    public void setGain(float gain) {
        this.gain = gain; 
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch; 
    }
    
    public void setClip(AudioClip clip) {
        this.clip = clip; 
    }
    
    public void setMinDistance(float min) {
        this.minDistance = min; 
    }
    
    public void setMaxDistance(float max) {
        this.maxDistance = max; 
    }
    
    public void setAttenuation(AudioAttenuation atten) {
        this.atten = atten == null ? AudioAttenuation.OFF : atten; 
    }
    
}
