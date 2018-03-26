package oasis.audio;

import oasis.core.Oasis;

/**
 * 
 * Holds audio data 
 * 
 * @author Nicholas Hamilton 
 *
 */
public abstract class AudioClip {

    protected int length; 
    protected int frequency; 
    protected boolean stereo; 
    protected boolean sixteenBit; 
    protected boolean is3D; 
    
    /** 
     * Create an audio clip 
     * 
     * @param length in frames 
     * @param frequency 
     * @param stereo 
     * @param sixteenBit
     * @param is3D if this is false, position will not matter  
     * @return
     */
    public static AudioClip create(int length, int frequency, boolean stereo, boolean sixteenBit, boolean is3D) {
        return Oasis.getAudioDevice().createClip(length, frequency, stereo, sixteenBit, is3D); 
    }
    
    public AudioClip(int len, int freq, boolean stereo, boolean sixteenBit, boolean is3D) {
        this.length = len; 
        this.frequency = freq; 
        this.stereo = stereo; 
        this.sixteenBit = sixteenBit; 
        this.is3D = is3D; 
    }
    
    /**
     * Set the audio data 
     */
    public abstract void setData(float[] in);
    
    /**
     * Set the audio data 
     */
    public abstract void setData(byte[] in); 
    
    public int getLength() {
        return length; 
    }
    
    public int getFrequency() {
        return frequency; 
    }
    
    public boolean isStereo() {
        return stereo; 
    }
    
    public boolean isSixteenBit() {
        return sixteenBit; 
    }
    
    public boolean is3D() {
        return is3D; 
    }
    
}
