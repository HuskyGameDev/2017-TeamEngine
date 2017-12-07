package oasis.audio.joal;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioDevice;
import oasis.audio.AudioFormat;
import oasis.audio.AudioListener;
import oasis.audio.AudioSource;
import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class JoalAudioDevice implements AudioDevice {

    private static final GameLogger log = new GameLogger(JoalAudioDevice.class); 
    
    protected AL al; 
    
    private JoalAudioListener listener; 
    private List<JoalAudioSource> sources; 
    
    public void checkError() {
        int err = al.alGetError(); 
        if (err != AL.AL_NO_ERROR) System.out.println("AL Error : " + err);
    }
    
    public void checkError(String command) {
        int err = al.alGetError(); 
        if (err != AL.AL_NO_ERROR) System.out.println("AL Error (" + command + ") : " + err);
    }
    
    protected boolean isPlayable(JoalAudioSource s) {
        return sources.contains(s); 
    }
    
    public JoalAudioDevice() {
        al = ALFactory.getAL(); 
        ALut.alutInit(); 
        log.info("AL Vendor: " + al.alGetString(AL.AL_VENDOR));
        log.info("AL Renderer: " + al.alGetString(AL.AL_RENDERER));
        log.info("AL Version: " + al.alGetString(AL.AL_VERSION));
        
        al.alDistanceModel(AL.AL_LINEAR_DISTANCE);
        
        sources = new ArrayList<>(); 
    }
    
    @Override
    public AudioListener createListener() {
        return new JoalAudioListener(this);
    }

    @Override
    public AudioSource createSource() {
        return new JoalAudioSource(this); 
    }

    @Override
    public AudioListener getListener() {
        return listener;
    }

    @Override
    public AudioSource getSource(int index) {
        return index >= sources.size() ? null : sources.get(index); 
    }
    
    @Override
    public int getSourceCount() {
        return sources.size(); 
    }

    @Override
    public int getMaxSourceCount() {
        // TODO check max count
        return 32;
    }

    @Override
    public void setListener(AudioListener listener) {
        this.listener = (JoalAudioListener) listener; 
        updateListener(this.listener); 
    }

    @Override
    public void addSource(AudioSource source) {
        JoalAudioSource s = (JoalAudioSource) source; 
        
        if (s == null) return; 
        
        if (sources.size() < getMaxSourceCount() - 1) {
            if (!sources.contains(s)) sources.add(s); 
        }
        else {
            // TODO 
        }
    }
    
    @Override
    public void removeSource(AudioSource source) {
        JoalAudioSource s = (JoalAudioSource) source; 
        
        if (s == null) return; 
        
        s.pause(); 
        sources.remove(s); 
    }

    @Override
    public void clearSources() {
        for (int i = sources.size() - 1; i >= 0; i--) {
            if (sources.get(i) != null) {
                sources.get(i).pause(); 
            }
            sources.remove(i); 
        }
    }

    @Override
    public JoalAudioBuffer createBuffer(AudioFormat fmt) {
        return new JoalAudioBuffer(this, fmt); 
    }
    
    protected void updateListener(JoalAudioListener jal) {
        if (listener == jal) {
            Vector3 pos = jal.getPosition(); 
            Quaternion rot = jal.getOrientation(); 
            Vector3 f = rot.getForward(); 
            Vector3 u = rot.getUp(); 
            
            float[] ori = new float[] { f.x, f.y, f.z, u.x, u.y, u.z }; 
            
            al.alListenerfv(AL.AL_POSITION, new float[] { pos.x, pos.y, pos.z }, 0);
            al.alListenerfv(AL.AL_ORIENTATION, ori, 0); 
            al.alListenerfv(AL.AL_VELOCITY, new float[] { 0, 0, 0 }, 0); 
        }
    }

    @Override
    public AudioBuffer loadWAVBuffer(String file) {
        int[] format = new int[1]; 
        ByteBuffer[] data = new ByteBuffer[1]; 
        int[] size = new int[1]; 
        int[] freq = new int[1]; 
        int[] loop = new int[1]; 
        
        file = Oasis.files.find(file); 
        
        if (file == null) {
            log.warning("Could not find file: " + file); 
            return null; 
        }
        
        ALut.alutLoadWAVFile(file, format, data, size, freq, loop);
        
        JoalAudioBuffer b = createBuffer(JoalConvert.getAudioFormat(format[0])); 
        b.setData(data[0], size[0], freq[0]);
        
        return b;
    }

}
