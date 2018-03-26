package oasis.audio.oal;

public class OalConvert {

    private OalConvert() {}

    public static int getFormat(boolean stereo, boolean sixteenBit) {
        if (stereo) {
            if (sixteenBit) {
                return Oal.AL_FORMAT_STEREO16; 
            }
            else {
                return Oal.AL_FORMAT_STEREO8; 
            }
        }
        else {
            if (sixteenBit) {
                return Oal.AL_FORMAT_MONO16; 
            }
            else {
                return Oal.AL_FORMAT_MONO8; 
            }
        }
    } 
    
}
