package oasis.audio.joal;

import oasis.audio.AudioListener;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class JoalAudioListener implements AudioListener {

    private JoalAudioDevice ad; 
    
    private Vector3 position; 
    private Quaternion orientation; 
    
    public JoalAudioListener(JoalAudioDevice ad) {
        this.ad = ad; 
        position = new Vector3();
        orientation = new Quaternion(); 
    }
    
    public Vector3 getPosition() { 
        return new Vector3(position); 
    }
    
    public void setPosition(Vector3 pos) {
        position.set(pos); 
        ad.updateListener(this); 
    }
    
    public Quaternion getOrientation() {
        return new Quaternion(orientation); 
    }

    public void setOrientation(Quaternion q) {
        orientation.set(q); 
        ad.updateListener(this); 
    }
    
    @Override
    public void dispose() {
        
    }
    
}
