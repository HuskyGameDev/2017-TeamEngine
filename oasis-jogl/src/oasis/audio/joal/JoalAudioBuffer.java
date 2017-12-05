package oasis.audio.joal;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioFormat;

public class JoalAudioBuffer implements AudioBuffer {

    private JoalAudioDevice ad; 
    
    private AudioFormat fmt; 
    private int[] id = new int[1]; 
    
    public JoalAudioBuffer(JoalAudioDevice ad, AudioFormat fmt) {
        this.ad = ad; 
        this.fmt = fmt; 
        
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
        ad.al.alBufferData(id[0], JoalConvert.getBufferFormat(fmt), buffer, data.length - 1, freq);
        ad.checkError();
    }
    
    @Override
    public void setData(short[] data, int freq) {
        ShortBuffer buffer = ShortBuffer.wrap(data); 
        buffer.position(data.length - 1); 
        buffer.flip(); 
        ad.al.alBufferData(id[0], JoalConvert.getBufferFormat(fmt), buffer, data.length * 2 - 2, freq);
        ad.checkError();
    }

}
