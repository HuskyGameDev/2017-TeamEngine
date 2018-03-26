package oasis.core;

/**
 * 
 * Loads a resource from a file 
 * 
 * @author Nicholas Hamilton 
 *
 * @param <T> Resource type to load 
 */
public interface ResourceLoader<T> {

    T load(String filename); 
    
}
