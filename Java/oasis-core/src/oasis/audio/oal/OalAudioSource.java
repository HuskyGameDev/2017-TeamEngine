package oasis.audio.oal;

import oasis.audio.AudioClip;
import oasis.audio.AudioSource;

public class OalAudioSource extends AudioSource {

    private Oal oal; 
    private int[] id = new int[1]; 
    private boolean playing = false; 
    
    private float realGain = 0.0f; 
    
    public OalAudioSource(Oal oal) {
        this.oal = oal; 
    }
    
    private void validate() {
        if (id[0] == 0) {
            oal.alGenSources(1, id, 0); 
        }
    }
    
    @Override
    public void setClip(AudioClip clip) {
        super.setClip(clip); 
        
        validate(); 
        if (this.clip == null) {
            oal.alSourcei(id[0], Oal.AL_BUFFER, 0); 
        }
        else {
            OalAudioClip c = (OalAudioClip) clip; 
            
            int bufferId = c.getId(); 
            
            oal.alSourcei(id[0], Oal.AL_BUFFER, bufferId); 
        }
    }
    
    @Override
    public void play() {
        if (!isPlaying()) {
            validate(); 
            oal.alSourcePlay(id[0]); 
            playing = true; 
        }
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
    public void setLoop(boolean loop) {
        super.setLoop(loop); 
        
        validate(); 
        oal.alSourcei(id[0], Oal.AL_LOOPING, this.loop ? Oal.AL_TRUE : Oal.AL_FALSE); 
    }
    
    public void setRealGain(float gain) {
        this.realGain = gain; 
        
        validate(); 
        oal.alSourcef(id[0], Oal.AL_GAIN, this.realGain); 
    }
    
    @Override
    public void setPitch(float pitch) {
        super.setPitch(pitch); 
        
        validate(); 
        oal.alSourcef(id[0], Oal.AL_PITCH, this.pitch); 
    }

}
