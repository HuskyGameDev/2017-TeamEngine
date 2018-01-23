package oasis.entity;

import oasis.audio.AudioSource;

public class AudioContainer extends EntityComponent {

    private AudioSource source; 
    
    public void deactivate() {
        source = null; 
    }
    
    public AudioSource getSource() {
        return source; 
    }
    
    public void setSource(AudioSource source) {
        this.source = source; 
    }
    
}
