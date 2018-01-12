package oasis.core;

public interface ResourceLoader<T> {

    T load(String filename); 
    
}
