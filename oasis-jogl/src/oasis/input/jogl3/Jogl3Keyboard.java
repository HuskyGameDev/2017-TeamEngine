package oasis.input.jogl3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import oasis.input.Keyboard;

public class Jogl3Keyboard implements Keyboard, KeyListener {

    public static final int MAX_KEYS = 1024; 
    
    private boolean[] down; 
    private int[] keys; 
    
    public Jogl3Keyboard() {
        down = new boolean[MAX_KEYS]; 
        keys = new int[MAX_KEYS]; 
    }
    
    public void update() {
        for (int i = 0; i < MAX_KEYS; i++) {
            if (down[i]) {
                keys[i]++; 
            }
            else {
                keys[i] = 0; 
            }
        }
    }
    
    @Override
    public boolean isKeyDown(int keycode) {
        return keys[keycode] > 0; 
    }

    @Override
    public boolean isKeyJustDown(int keycode) {
        return keys[keycode] == 1; 
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        log.debug("Key pressed: " + e.getKeyCode()); 
        down[e.getKeyCode()] = true; 
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        log.debug("Key released: " + e.getKeyCode()); 
        down[e.getKeyCode()] = false; 
    }

    @Override
    public void keyTyped(KeyEvent e) {}

}
