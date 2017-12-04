package oasis.audio.joal;

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
import oasis.math.Quaternionf;
import oasis.math.Vector3f;

public class JoalAudioDevice implements AudioDevice {

    private static final GameLogger log = new GameLogger(JoalAudioDevice.class); 
    
    protected AL al; 
    
    private JoalAudioListener listener; 
    private List<JoalAudioSource> sources; 
    
    public JoalAudioDevice() {
        al = ALFactory.getAL(); 
        ALut.alutInit(); 
        log.info("AL Vendor: " + al.alGetString(AL.AL_VENDOR));
        log.info("AL Renderer: " + al.alGetString(AL.AL_RENDERER));
        log.info("AL Version: " + al.alGetString(AL.AL_VERSION));
        
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
            sources.add(s); 
        }
        else {
            // TODO 
        }
    }
    
    @Override
    public void removeSource(AudioSource source) {
        JoalAudioSource s = (JoalAudioSource) source; 
        
        if (s == null) return; 
        
        sources.remove(s); 
    }

    @Override
    public void clearSources() {
        // TODO 
    }

    @Override
    public AudioBuffer createBuffer(AudioFormat fmt) {
        return new JoalAudioBuffer(this, fmt); 
    }
    
    protected void updateListener(JoalAudioListener jal) {
        if (listener == jal) {
            Vector3f pos = jal.getPosition(); 
            Quaternionf rot = jal.getOrientation(); 
            
            al.alListener3f(AL.AL_POSITION, pos.x, pos.y, pos.z);
        }
    }

}
