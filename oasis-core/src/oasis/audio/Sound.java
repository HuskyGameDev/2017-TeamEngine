package oasis.audio;

import oasis.core.Oasis;

public class Sound {

    private AudioSource source; 
    
    public Sound(AudioBuffer buffer) {
        source = Oasis.audio.createSource(); 
        source.setBuffer(buffer); 
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
