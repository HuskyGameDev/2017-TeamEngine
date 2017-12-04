package oasis.audio.joal;

import oasis.audio.AudioListener;
import oasis.math.Vector3f;

public class JoalAudioListener implements AudioListener {

    private Vector3f position; 
    
    public JoalAudioListener() {
        position = new Vector3f();
    }
    
    public Vector3f getPosition() { 
        return position.copy(); 
    }
    
    public void setPosition(Vector3f pos) {
        position.set(pos); 
    }

    @Override
    public void dispose() {
        
    }
    
}
