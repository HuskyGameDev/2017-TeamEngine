package oasis.sound;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioFormat;
import oasis.audio.AudioListener;
import oasis.audio.Sound;
import oasis.core.Application;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.input.Keyboard;

public class SoundApp extends Application {

    private AudioListener listener; 
    private AudioBuffer buffer; 
    
    private Sound[] notes; 
    private int[] keycodes; 
    
    public static void main(String[] args) {
        Config cfg = new Config(); 
        cfg.engine = Jogl3Engine.class; 
        cfg.fps = 60; 
        cfg.ups = 60; 
        cfg.width = 800; 
        cfg.height = 600; 
        
        Application app = new SoundApp(); 
        app.start(cfg); 
    }

    @Override
    public void onInit() {
        listener = Oasis.audio.createListener(); 
        buffer = Oasis.audio.createBuffer(AudioFormat.MONO16); 
        
        short[] data = new short[100]; 
        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (Short.MAX_VALUE * Math.sin(i * 1.0 * Math.PI * 440.0 / 44100)); 
        }
        
        buffer.setData(data, 44100);

        keycodes = new int[] {
                Keyboard.KEY_Z, 
                Keyboard.KEY_S, 
                Keyboard.KEY_X, 
                Keyboard.KEY_D, 
                Keyboard.KEY_C, 
                Keyboard.KEY_V, 
                Keyboard.KEY_G, 
                Keyboard.KEY_B, 
                Keyboard.KEY_H, 
                Keyboard.KEY_N, 
                Keyboard.KEY_J, 
                Keyboard.KEY_M, 
                Keyboard.KEY_COMMA
        };
        notes = new Sound[keycodes.length]; 
        
        for (int j = 0; j < notes.length; j++) {
            notes[j] = new Sound(buffer); 
            notes[j].setPitch((float) Math.pow(Math.pow(2, 1.0 / 12.0), j)); 
            System.out.println(notes[j].getPitch() * 440);
            notes[j].setLooping(true); 
            notes[j].setGain(0.0f);
        }
    }

    @Override
    public void onUpdate(float dt) {
        for (int i = 0; i < notes.length; i++) {
            if (Oasis.keyboard.isKeyDown(keycodes[i])) {
                System.out.println("Playing " + i);
                notes[i].changeGain(1f);
                if (notes[i].getGain() > 0.3f) {
                    notes[i].setGain(0.3f); 
                }
            }
            else {
                notes[i].changeGain(-0.05f);
            }
            
            if (notes[i].getGain() > 0) {
                notes[i].play(); 
            }
            else {
                notes[i].stop(); 
            }
        }
        
    }

    @Override
    public void onRender() {
        Oasis.graphics.clearScreen(new ColorRgba(0.8f, 0.9f, 1.0f, 1.0f)); 
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        
    }
    
}
