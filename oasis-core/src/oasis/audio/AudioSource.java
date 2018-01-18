package oasis.audio;

import oasis.core.Oasis;
import oasis.math.Vector3;

public abstract class AudioSource {

    protected final Vector3 position = new Vector3(); 
    protected boolean loop = false; 
    protected float gain = 1.0f; 
    protected float pitch = 1.0f; 
    protected float minDistance = 0.0f; 
    protected float maxDistance = 100.0f; 
    protected AudioAttenuation atten = AudioAttenuation.LOGARITHMIC; 
    
    private AudioClip clip; 
    
    public static AudioSource create() {
        return Oasis.getAudioDevice().createSource(); 
    }
    
    public abstract void play(); 
    
    public abstract void stop(); 
    
    public abstract void pause(); 
    
    public abstract boolean isPlaying(); 
    
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
    
}
