package oasis.audio.joal;

import com.jogamp.openal.AL;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioSource;
import oasis.math.Vector3;

public class JoalAudioSource implements AudioSource {

    private JoalAudioDevice ad; 
    
    private JoalAudioBuffer buffer; 
    private Vector3 position; 
    
    private int[] id = new int[1]; 
    
    public JoalAudioSource(JoalAudioDevice ad) {
        this.ad = ad; 
        position = new Vector3(); 
        ad.al.alGenSources(1, id, 0); 
    }
    
    @Override
    public void dispose() {
        ad.al.alDeleteSources(1, id, 0);
        ad.checkError();
    }

    @Override
    public AudioBuffer getBuffer() {
        return buffer; 
    }

    @Override
    public void setBuffer(AudioBuffer buffer) {
        this.buffer = (JoalAudioBuffer) buffer; 
        
        if (this.buffer != null) {
            ad.al.alSourcei(id[0], AL.AL_BUFFER, this.buffer.getId());
            ad.checkError("set source buffer not null");
        }
        else {
            ad.al.alSourcei(id[0], AL.AL_BUFFER, 0);
            ad.checkError("set source buffer null");
        }
    }
    
    public Vector3 getPosition() {
        return position; 
    }
    
    public void setPosition(Vector3 pos) {
        position.set(pos); 
        ad.al.alSource3f(id[0], AL.AL_POSITION, position.x, position.y, position.z);
        ad.checkError();
    }

    @Override
    public void play() {
        // TODO Auto-generated method stub
        ad.al.alSourcei(id[0], AL.AL_LOOPING, AL.AL_TRUE); 
        ad.checkError(); 
        ad.al.alSourcePlay(id[0]); 
        ad.checkError(); 
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        ad.al.alSourcePause(id[0]); 
        ad.checkError(); 
    }

    @Override
    public boolean isPlaying() {
        int[] tmp = new int[1]; 
        ad.al.alGetSourcei(id[0], AL.AL_SOURCE_STATE, tmp, 0);
        ad.checkError();
        System.out.printf("%x\n", tmp[0]);
        return tmp[0] == AL.AL_PLAYING; 
    }

}
