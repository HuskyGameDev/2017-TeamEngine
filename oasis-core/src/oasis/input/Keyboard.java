package oasis.input;

/**
 * Keyboard interface 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Keyboard {

    // alphabet
    public static final int KEY_A = 0x41; 
    public static final int KEY_B = 0x42; 
    public static final int KEY_C = 0x43; 
    public static final int KEY_D = 0x44; 
    public static final int KEY_E = 0x45; 
    public static final int KEY_F = 0x46; 
    public static final int KEY_G = 0x47; 
    public static final int KEY_H = 0x48; 
    public static final int KEY_I = 0x49; 
    public static final int KEY_J = 0x4A; 
    public static final int KEY_K = 0x4B; 
    public static final int KEY_L = 0x4C; 
    public static final int KEY_M = 0x4D; 
    public static final int KEY_N = 0x4E; 
    public static final int KEY_O = 0x4F; 
    public static final int KEY_P = 0x50; 
    public static final int KEY_Q = 0x51; 
    public static final int KEY_R = 0x52; 
    public static final int KEY_S = 0x53; 
    public static final int KEY_T = 0x54; 
    public static final int KEY_U = 0x55; 
    public static final int KEY_V = 0x56; 
    public static final int KEY_W = 0x57; 
    public static final int KEY_X = 0x58; 
    public static final int KEY_Y = 0x59; 
    public static final int KEY_Z = 0x5A; 
    
    // numbers 
    public static final int KEY_0 = 0x30; 
    public static final int KEY_1 = 0x31; 
    public static final int KEY_2 = 0x32; 
    public static final int KEY_3 = 0x33; 
    public static final int KEY_4 = 0x34; 
    public static final int KEY_5 = 0x35; 
    public static final int KEY_6 = 0x36; 
    public static final int KEY_7 = 0x37; 
    public static final int KEY_8 = 0x38; 
    public static final int KEY_9 = 0x39; 
    
    /**
     * Checks if a key is down
     * 
     * @param keycode Key code
     * @return If key is currently down 
     */
    boolean isKeyDown(int keycode); 
    
    /**
     * Checks if a key was just pressed during 
     * this update 
     * 
     * @param keycode Key code 
     * @return If key was just pressed 
     */
    boolean isKeyJustDown(int keycode); 
    
}
