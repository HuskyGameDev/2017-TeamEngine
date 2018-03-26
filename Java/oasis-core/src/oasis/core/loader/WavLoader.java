package oasis.core.loader;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import oasis.audio.AudioClip;
import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.ResourceLoader;

public class WavLoader implements ResourceLoader<AudioClip> {

    private final static Logger log = new Logger(WavLoader.class); 
    
    @Override
    public AudioClip load(String file) {
        String path = Oasis.getFileSystem().find(file); 
        
        if (path == null) {
            log.warning("Could not find file: " + file);
            return null; 
        }
        
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File(path));
            
            int sampleRate = (int) in.getFormat().getSampleRate(); 
            int channels = in.getFormat().getChannels(); 
            
            int length = (int) in.getFrameLength(); 
            
            int bytesPerFrame = in.getFormat().getFrameSize(); 
            
            byte[] bytes = new byte[length * bytesPerFrame]; 
            
            in.read(bytes); 
            
            AudioClip clip = AudioClip.create(
                    bytes.length, 
                    sampleRate, 
                    channels > 1, 
                    bytesPerFrame / channels > 1, 
                    channels == 1); 
            clip.setData(bytes); 
            
            return clip; 
        } catch (Exception e) {
            e.printStackTrace();
            log.warning("Could not read WAV data: " + file); 
            return null; 
        } 
    }

}
