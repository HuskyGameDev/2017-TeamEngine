package oasis.audio.joal;

import oasis.audio.AudioListener;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;

public class JoalAudioListener implements AudioListener {

    private JoalAudioDevice ad; 
    
    private Vector3f position; 
    private Quaternionf orientation; 
    
    public JoalAudioListener(JoalAudioDevice ad) {
        this.ad = ad; 
        position = new Vector3f();
        orientation = new Quaternionf(); 
    }
    
    public Vector3f getPosition() { 
        return new Vector3f(position); 
    }
    
    public void setPosition(Vector3f pos) {
        position.set(pos); 
        ad.updateListener(this); 
    }
    
    public Quaternionf getOrientation() {
        return new Quaternionf(orientation); 
    }

    public void setOrientation(Quaternionf q) {
        orientation.set(q); 
        ad.updateListener(this); 
    }
    
    @Override
    public void dispose() {
        
    }
    
}
