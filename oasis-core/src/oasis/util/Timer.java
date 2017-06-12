package oasis.util;

public class Timer {

    private static final double SECOND = 1000000000.0;
    
    private long start;
    
    public Timer() {
        reset();
    }
    
    public void reset() {
        start = System.nanoTime();
    }
    
    public double getTime() {
        return (System.nanoTime() - start) / SECOND;
    }
    
}
