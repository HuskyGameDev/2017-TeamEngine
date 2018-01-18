package oasis.audio.oal;

import oasis.audio.AudioSource;
import oasis.math.Vector3;

public class OalAudioSource extends AudioSource {

    private Oal oal; 
    private int[] id; 
    private boolean playing = false; 
    
    public OalAudioSource(Oal oal) {
        this.oal = oal; 
    }
    
    private void validate() {
        if (id[0] == 0) {
            oal.alGenSources(1, id, 0); 
        }
    }
    
    @Override
    public void play() {
        validate(); 
        oal.alSourcePlay(id[0]); 
        playing = true; 
    }

    @Override
    public void stop() {
        validate(); 
        oal.alSourceStop(id[0]); 
        playing = false; 
    }

    @Override
    public void pause() {
        validate(); 
        oal.alSourcePause(id[0]); 
        playing = false; 
    }

    @Override
    public boolean isPlaying() {
        return playing; 
    }
    
    @Override
    public void setPosition(Vector3 pos) {
        super.setPosition(pos); 
        
        validate(); 
        oal.alSource3f(id[0], Oal.AL_POSITION, position.x, position.y, position.z);
    }

}
