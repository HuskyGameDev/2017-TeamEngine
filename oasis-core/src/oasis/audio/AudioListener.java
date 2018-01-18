package oasis.audio;

import oasis.core.Oasis;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public abstract class AudioListener {

    public static AudioListener create() {
        return Oasis.getAudioDevice().createListener(); 
    }
    
    protected final Vector3 position = new Vector3(); 
    protected final Quaternion rotation = new Quaternion(); 
    protected float volume = 1.0f; 
    
    public Vector3 getPosition() {
        return new Vector3(position); 
    }
    
    public Quaternion getRotation() {
        return new Quaternion(rotation); 
    }
    
    public void setPosition(Vector3 pos) {
        position.set(pos); 
    }
    
    public void setRotation(Quaternion rot) {
        rotation.set(rot); 
    }
    
}
