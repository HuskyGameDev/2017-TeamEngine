package oasis.audio.jogl3;

import java.nio.Buffer;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

import oasis.audio.oal.Oal;
import oasis.core.Logger;

public class Jogl3DebugOal implements Oal {

    private static final Logger log = new Logger(Jogl3DebugOal.class); 
    
    private AL al; 
    
    public Jogl3DebugOal() {
        al = ALFactory.getAL(); 
        ALut.alutInit(); 
    }

    private void checkError(String command) {
        int err = al.alGetError(); 
        
        if (err != Oal.AL_NO_ERROR) {
            log.warning("AL Error: " + err + " " + command); 
        }
    }
    
    @Override
    public void alGenBuffers(int count, int[] ids, int offset) {
        al.alGenBuffers(count, ids, offset);
        checkError("alGenBuffers"); 
    }

    @Override
    public void alDeleteBuffers(int count, int[] ids, int offset) {
        al.alDeleteBuffers(count, ids, offset); 
        checkError("alDeleteBuffers"); 
    }

    @Override
    public void alBufferData(int id, int format, Buffer data, int length, int freq) {
        al.alBufferData(id, format, data, length, freq); 
        checkError("alBufferData"); 
    }

    @Override
    public void alGenSources(int count, int[] ids, int offset) {
        al.alGenSources(count, ids, offset); 
        checkError("alGenSources"); 
    }

    @Override
    public void alDeleteSources(int count, int[] ids, int offset) {
        al.alDeleteSources(count, ids, offset); 
        checkError("alDeleteSource"); 
    }

    @Override
    public void alSourcei(int id, int property, int value) {
        al.alSourcei(id, property, value);
        checkError("alSourcei"); 
    }

    @Override
    public void alSourcef(int id, int property, float value) {
        al.alSourcef(id, property, value);
        checkError("alSourcef"); 
    }

    @Override
    public void alSource3f(int id, int property, float x, float y, float z) {
        al.alSource3f(id, property, x, y, z);
        checkError("alSource3f"); 
    }

    @Override
    public void alSourcePlay(int id) {
        al.alSourcePlay(id); 
        checkError("alSourcePlay"); 
    }

    @Override
    public void alSourceStop(int id) {
        al.alSourceStop(id); 
        checkError("alSourceStop"); 
    }

    @Override
    public void alSourcePause(int id) {
        al.alSourcePause(id); 
        checkError("alSourcePause"); 
    }

    @Override
    public void alDistanceModel(int model) {
        al.alDistanceModel(model); 
        checkError("alDistanceModel"); 
    }

    @Override
    public void alListenerfv(int property, float[] values, int offset) {
        al.alListenerfv(property, values, offset); 
        checkError("alListenerfv"); 
    }
    
}
