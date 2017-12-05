package oasis.audio.joal;

import java.nio.ByteBuffer;

import com.jogamp.openal.AL;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioFormat;

public class JoalAudioBuffer implements AudioBuffer {

    private JoalAudioDevice ad; 
    
    private AudioFormat fmt; 
    private int[] id = new int[1]; 
    
    public JoalAudioBuffer(JoalAudioDevice ad, AudioFormat fmt) {
        this.ad = ad; 
        
        ad.al.alGenBuffers(1, id, 0); 
        ad.checkError();
    }
    
    public AudioFormat getFormat() {
        return fmt; 
    }
    
    public int getId() {
        return id[0]; 
    }
    
    @Override
    public void dispose() {
        ad.al.alDeleteBuffers(1, id, 0); 
        ad.checkError();
    }

    @Override
    public void setData(byte[] data, int freq) {
        ByteBuffer buffer = ByteBuffer.wrap(data); 
        buffer.position(data.length - 1); 
        buffer.flip(); 
        ad.al.alBufferData(id[0], AL.AL_FORMAT_MONO8, buffer, data.length, freq);
        ad.checkError();
    }

}
