package oasis.audio.oal;

import java.nio.ByteBuffer;

import oasis.audio.AudioClip;

public class OalAudioClip extends AudioClip {

    private Oal oal; 
    private int[] id; 
    
    public OalAudioClip(Oal oal, int len, int freq, boolean stereo, boolean _16bit, boolean _3D) {
        super(len, freq, stereo, _16bit, _3D); 
        this.oal = oal;
    }
    
    private void validate() {
        if (id[0] == 0) {
            oal.alGenBuffers(1, id, 0); 
        }
    }
    
    @Override
    public void setData(byte[] in) {
        validate(); 
        ByteBuffer buffer = ByteBuffer.wrap(in); 
        buffer.position(in.length - 1); 
        buffer.flip(); 
        oal.alBufferData(id[0], OalConvert.getFormat(stereo, sixteenBit), buffer, in.length - 1, frequency);
    }

}
