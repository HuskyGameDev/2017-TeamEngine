package oasis.audio;

public interface AudioDevice {

    AudioClip createClip(int length, int frequency, boolean stereo, boolean sixteenBit, boolean is3D); 
    
    AudioSource createSource(); 
    
    AudioListener createListener(); 
    
}
