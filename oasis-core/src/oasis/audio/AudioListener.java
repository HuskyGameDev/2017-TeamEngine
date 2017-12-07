package oasis.audio;

import oasis.math.Quaternion;
import oasis.math.Vector3;

/**
 * 
 * Represents properties of object that hears sound 
 * 
 * Do NOT implement this, instead use AudioListeners provided
 * by AudioDevice 
 * 
 * @author Nicholas Hamilton 
 *
 */
public interface AudioListener {

    /** 
     * Dispose resource 
     */
    void dispose(); 
    
    /** 
     * @return listener position 
     */
    Vector3 getPosition(); 
    
    /**
     * set listener position 
     */
    void setPosition(Vector3 pos); 
    
    /**
     * @return get listener orientation 
     */
    Quaternion getOrientation(); 
    
    /**
     * set listener orientation 
     */
    void setOrientation(Quaternion rot); 
    
}
