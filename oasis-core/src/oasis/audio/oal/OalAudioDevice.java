package oasis.audio.oal;

import java.util.ArrayList;
import java.util.List;

import oasis.audio.AudioClip;
import oasis.audio.AudioDevice;
import oasis.audio.AudioListener;
import oasis.audio.AudioSource;
import oasis.core.OasisException;
import oasis.math.Vector3;

public class OalAudioDevice implements AudioDevice {

    private Oal oal; 
    private List<OalAudioSource> sources = new ArrayList<>(); 
    
    private Vector3 position = new Vector3(); 
    
    public OalAudioDevice(Oal oal) {
        this.oal = oal; 
    }

    public void preUpdate() {
        AudioListener listener = AudioListener.getCurrent(); 
        
        if (listener == null) {
            position.set(0); 
        }
        else {
            position.set(listener.getPosition()); 
        }
        
        for (int i = 0; i < sources.size(); i++) {
            OalAudioSource source = sources.get(i); 
            
//            AudioClip clip = source.getClip(); 
//            
//            if (clip != null && !clip.is3D()) {
//                source.setRealGain(source.getGain()); 
//                continue; 
//            }
            
            float gain = source.getGain(); 
            Vector3 relPos = source.getPosition().subtract(position); 
            float dist = relPos.length(); 
            float min = source.getMinDistance(); 
            float max = source.getMaxDistance(); 
            
//            System.out.println(gain + " " + dist + " " + min + " " + max);
            
            switch (source.getAttenuation()) {
            default: 
                throw new OasisException("Unknown audio attenuation: " + source.getAttenuation()); 
            case OFF: 
                source.setRealGain(gain); 
                break; 
            case LINEAR: 
                if (dist <= min) {
                    source.setRealGain(gain); 
                }
                else if (dist >= max) {
                    source.setRealGain(0.0f); 
                }
                else {
                    source.setRealGain(gain * (1.0f - (dist - min) / (max - min)));
                }
            }
        }
    }
    
    public void postUpdate() {} 
    
    @Override
    public AudioClip createClip(int length, int frequency, boolean stereo, boolean sixteenBit, boolean is3D) {
        return new OalAudioClip(oal, length, frequency, stereo, sixteenBit, is3D); 
    }

    @Override
    public AudioSource createSource() {
        sources.add(new OalAudioSource(oal)); 
        return sources.get(sources.size() - 1); 
    }

    @Override
    public AudioListener createListener() {
        return new OalAudioListener(); 
    }

}
