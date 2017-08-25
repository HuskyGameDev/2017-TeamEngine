package oasis.input;

public interface Mouse {

    float getX(); 
    float getY(); 
    
    boolean isButtonDown(int button); 
    boolean isButtonJustDown(int button); 
    
}
