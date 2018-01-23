package oasis.entity;

public interface ComponentFactory<T extends Component> {

    T create(); 
    
}
