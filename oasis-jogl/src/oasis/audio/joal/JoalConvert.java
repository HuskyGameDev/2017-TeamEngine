package oasis.audio.joal;

import com.jogamp.openal.AL;

import oasis.audio.AudioFormat;

public class JoalConvert {

    private static final int[] bufferFormat = new int[AudioFormat.values().length]; 
    
    static {
        bufferFormat[AudioFormat.MONO8.ordinal()] = AL.AL_FORMAT_MONO8;
        bufferFormat[AudioFormat.MONO16.ordinal()] = AL.AL_FORMAT_MONO16; 
        bufferFormat[AudioFormat.STEREO8.ordinal()] = AL.AL_FORMAT_STEREO8; 
        bufferFormat[AudioFormat.STEREO16.ordinal()] = AL.AL_FORMAT_STEREO16; 
    }
    
    public static final int getBufferFormat(AudioFormat format) {
        return format == null ? AL.AL_FORMAT_MONO8 : bufferFormat[format.ordinal()]; 
    }
    
}
