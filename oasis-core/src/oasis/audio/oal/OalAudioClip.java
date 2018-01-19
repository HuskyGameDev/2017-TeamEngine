package oasis.audio.oal;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import oasis.audio.AudioClip;
import oasis.math.Mathf;

public class OalAudioClip extends AudioClip {

    private Oal oal; 
    private int[] id = new int[1]; 
    
    public OalAudioClip(Oal oal, int len, int freq, boolean stereo, boolean _16bit, boolean _3D) {
        super(len, freq, stereo, _16bit, _3D); 
        this.oal = oal;
    }
    
    public int getId() {
        validate(); 
        return id[0]; 
    }
    
    private void validate() {
        if (id[0] == 0) {
            oal.alGenBuffers(1, id, 0); 
        }
    }
    
    public void setData(byte[] in) {
        validate(); 
        ByteBuffer buffer = ByteBuffer.wrap(in); 
        buffer.position(in.length); 
        buffer.flip(); 
        oal.alBufferData(id[0], OalConvert.getFormat(stereo, sixteenBit), buffer, in.length - 0, frequency);
    }
    
    public void setData(short[] in) {
        validate(); 
        ShortBuffer buffer = ShortBuffer.wrap(in); 
        buffer.position(in.length - 1); 
        buffer.flip(); 
        oal.alBufferData(id[0], OalConvert.getFormat(stereo, sixteenBit), buffer, in.length * 2 - 4, frequency);
    }

    @Override
    public void setData(float[] in) {
        if (sixteenBit) {
            short[] data = new short[in.length]; 
            
            for (int i = 0; i < data.length; i++) {
                data[i] = (short) Mathf.clamp(-32768, 32767, (in[i] * 32768)); 
            }
            
            setData(data); 
        }
        else {
            byte[] data = new byte[in.length]; 
            
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) Mathf.clamp(-128, 127, (in[i] * 128)); 
            }
            
            setData(data); 
        }
    }

}
