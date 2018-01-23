package oasis.entity;

public interface ComponentFactory<T extends EntityComponent> {

    T create(); 
    
}
