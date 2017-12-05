package oasis.audio;

public interface AudioBuffer {

    void dispose(); 
    
    AudioFormat getFormat(); 
    
    void setData(byte[] data, int freq); 
    void setData(short[] data, int freq); 
    
}
