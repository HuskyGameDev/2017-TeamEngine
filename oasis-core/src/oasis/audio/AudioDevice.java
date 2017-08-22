package oasis.audio;

public interface AudioDevice {

    // resource creation
    
    Listener createListener(); 
    Source createSource(); 
    
    // getters
    
    Listener getListener(); 
    Source getSource(int index); 
    int getMaxSourceCount(); 
    
    // setters
    
    void setListener(Listener listener); 
    void setSource(int index, Source source); 
    void clearSources(); 
    
}
