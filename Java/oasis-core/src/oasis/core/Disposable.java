package oasis.core;

/**
 * Disposable object. Mostly used for native resources
 * for graphics and audio. 
 * 
 * @author Nicholas Hamilton
 *
 */
public interface Disposable {

    /**
     * Dispose of the native resource
     */
    void dispose(); 
    
    /**
     * Checks if the native resource is disposed 
     * 
     * @return if native resource is disposed
     */
    boolean isDisposed(); 
    
}
