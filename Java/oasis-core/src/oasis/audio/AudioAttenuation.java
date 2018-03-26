package oasis.audio;

/** 
 * 
 * How audio should handle distance 
 * 
 * @author Nicholas Hamilton 
 *
 */
public enum AudioAttenuation {

    /** 
     * No change in volume 
     */
    OFF, 
    
    /** 
     * Volume quiets linearly from min and max distance 
     */
    LINEAR; 
    
}
