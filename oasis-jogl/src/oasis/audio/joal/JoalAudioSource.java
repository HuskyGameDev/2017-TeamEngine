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
        ad.al.alSourcePlay(id[0]); 
    }

    @Override
    public void pause() {
        ad.al.alSourcePause(id[0]);
    }
    
    @Override
    public void stop() {
        ad.al.alSourceStop(id[0]); 
    }

    @Override
    public boolean isPlaying() {
        return getInt(AL.AL_SOURCE_STATE) == AL.AL_PLAYING; 
    }

    @Override
    public void setLooping(boolean loop) {
        ad.al.alSourcei(id[0], AL.AL_LOOPING, loop ? AL.AL_TRUE : AL.AL_FALSE); 
    }

    @Override
    public boolean isLooping() {
        return getInt(AL.AL_LOOPING) == AL.AL_TRUE; 
    }

    @Override
    public void setGain(float gain) {
        ad.al.alSourcef(id[0], AL.AL_GAIN, gain); 
    }

    @Override
    public float getGain() {
        return getFloat(AL.AL_GAIN); 
    }
    
    private int getInt(int value) {
        int[] tmp = new int[1]; 
        ad.al.alGetSourcei(id[0], value, tmp, 0);
        return tmp[0]; 
    }
    
    private float getFloat(int value) {
        float[] tmp = new float[1]; 
        ad.al.alGetSourcef(id[0], value, tmp, 0);
        return tmp[0]; 
    }

    @Override
    public void setPitch(float pitch) {
        ad.al.alSourcef(id[0], AL.AL_PITCH, pitch);
    }

    @Override
    public float getPitch() {
        return getFloat(AL.AL_PITCH); 
    }

}
