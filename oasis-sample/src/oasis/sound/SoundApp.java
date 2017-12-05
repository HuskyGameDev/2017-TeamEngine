package oasis.sound;

import oasis.audio.AudioBuffer;
import oasis.audio.AudioFormat;
import oasis.audio.AudioListener;
import oasis.audio.AudioSource;
import oasis.core.Application;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.core.jogl3.Jogl3Engine;
import oasis.graphics.ColorRgba;
import oasis.math.Vector3;

public class SoundApp extends Application {

    private AudioListener listener; 
    private AudioSource source; 
    private AudioBuffer buffer; 
    
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
        source = Oasis.audio.createSource(); 
        buffer = Oasis.audio.createBuffer(AudioFormat.MONO_8); 
        
        byte[] data = new byte[44100]; 
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (127 * Math.sin(i * 0.01)); 
        }
        
        buffer.setData(data, 44100);
        source.setBuffer(buffer); 
        source.setPosition(new Vector3(0, 0, 0));
        
        Oasis.audio.setListener(listener); 
        Oasis.audio.addSource(source); 

        System.out.println("Playing: " + source.isPlaying()); 
        
        source.play(); 

        System.out.println("Playing: " + source.isPlaying()); 
    }

    @Override
    public void onUpdate(float dt) {
        // TODO Auto-generated method stub
        listener.setPosition(listener.getPosition().add(new Vector3(0, 1, 0).multiplySelf(dt)));
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
