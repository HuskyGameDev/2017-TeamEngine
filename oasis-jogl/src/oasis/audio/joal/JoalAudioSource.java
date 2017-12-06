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
    
    private float gain = 1.0f;
    private boolean playing = false; 
    private boolean looping = false; 
    private float pitch = 1.0f; 
    private float minDist = 1.0f; 
    private float maxDist = Float.MAX_VALUE; 
    
    public JoalAudioSource(JoalAudioDevice ad) {
        this.ad = ad; 
        position = new Vector3(); 
        ad.al.alGenSources(1, id, 0); 
//        ad.al.alSourcef(id[0], AL.AL_ROLLOFF_FACTOR, 40f);
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
    
    public float getMinDistance() {
        return getFloat(AL.AL_REFERENCE_DISTANCE); //minDist; 
    }
    
    public float getMaxDistance() {
        return getFloat(AL.AL_MAX_DISTANCE); //maxDist; 
    }
    
    public void setMinDistance(float dist) {
        ad.al.alSourcef(id[0], AL.AL_REFERENCE_DISTANCE, dist);
        minDist = dist; 
    }
    
    public void setMaxDistance(float dist) {
        ad.al.alSourcef(id[0], AL.AL_MAX_DISTANCE, dist);
        maxDist = dist; 
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
        if (ad.isPlayable(this) && !isPlaying()) ad.al.alSourcePlay(id[0]); 
        playing = true; 
    }

    @Override
    public void pause() {
        playing = false; 
        ad.al.alSourcePause(id[0]);
    }
    
    @Override
    public void stop() {
        playing = false; 
        ad.al.alSourceStop(id[0]); 
    }

    @Override
    public boolean isPlaying() {
        return playing; //getInt(AL.AL_SOURCE_STATE) == AL.AL_PLAYING; 
    }

    @Override
    public void setLooping(boolean loop) {
        looping = loop; 
        ad.al.alSourcei(id[0], AL.AL_LOOPING, loop ? AL.AL_TRUE : AL.AL_FALSE); 
    }

    @Override
    public boolean isLooping() {
        return looping; //getInt(AL.AL_LOOPING) == AL.AL_TRUE; 
    }

    @Override
    public void setGain(float gain) {
        if (gain < 0.0) gain = 0; 
        if (gain > 1.0) gain = 1; 
        this.gain = gain; 
        ad.al.alSourcef(id[0], AL.AL_GAIN, gain); 
    }

    @Override
    public float getGain() {
        return gain; //getFloat(AL.AL_GAIN); 
    }
    
//    private int getInt(int value) {
//        int[] tmp = new int[1]; 
//        ad.al.alGetSourcei(id[0], value, tmp, 0);
//        return tmp[0]; 
//    }
    
    private float getFloat(int value) {
        float[] tmp = new float[1]; 
        ad.al.alGetSourcef(id[0], value, tmp, 0);
        return tmp[0]; 
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch; 
        ad.al.alSourcef(id[0], AL.AL_PITCH, pitch);
    }

    @Override
    public float getPitch() {
        return pitch; //getFloat(AL.AL_PITCH); 
    }

}
