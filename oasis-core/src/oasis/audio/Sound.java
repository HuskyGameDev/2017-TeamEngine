package oasis.audio;

import oasis.core.Oasis;
import oasis.math.Vector3;

public class Sound {

    private AudioSource source; 
    
    public Sound(AudioBuffer buffer) {
        source = Oasis.audio.createSource(); 
        source.setBuffer(buffer); 
    }
    
    public Sound(String file) {
        source = Oasis.audio.createSource(); 
        source.setBuffer(Oasis.audio.loadWAVBuffer(file));
    }
    
    public AudioFormat getFormat() {
        return source.getBuffer().getFormat(); 
    }
    
    public void setMinDistance(float dist) {
        source.setMinDistance(dist); 
    }
    
    public float getMinDistance() {
        return source.getMinDistance(); 
    }
    
    public void setMaxDistance(float dist) {
        source.setMaxDistance(dist); 
    }
    
    public float getMaxDistance() {
        return source.getMaxDistance(); 
    }
    
    public void setPosition(Vector3 pos) {
        source.setPosition(pos); 
    }
    
    public Vector3 getPosition() {
        return source.getPosition(); 
    }
    
    public void setGain(float gain) {
        source.setGain(gain); 
    }
    
    public float getGain() {
        return source.getGain(); 
    }
    
    public void changeGain(float dg) {
        source.setGain(source.getGain() + dg);
    }
    
    public void setPitch(float pitch) {
        source.setPitch(pitch); 
    }
    
    public float getPitch() {
        return source.getPitch(); 
    }
    
    public void setLooping(boolean loop) {
        source.setLooping(loop); 
    }
    
    public boolean isLooping() {
        return source.isLooping(); 
    }
    
    public void play() {
        Oasis.audio.addSource(source); 
        source.play(); 
    }
    
    public void stop() {
        source.stop(); 
        Oasis.audio.removeSource(source);
    }
    
    public void pause() {
        source.pause(); 
        Oasis.audio.removeSource(source); 
    }
    
    public boolean isPlaying() {
        return source.isPlaying(); 
    }
    
}
