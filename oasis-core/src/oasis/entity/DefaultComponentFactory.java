package oasis.entity;

public class DefaultComponentFactory<T extends Component> implements ComponentFactory<T> {

    private Class<T> type; 
    
    public DefaultComponentFactory(Class<T> type) {
        this.type = type; 
    }

    @Override
    public T create() {
        try {
            return type.newInstance(); 
        }
        catch (Exception e) {
            throw new RuntimeException("Could not create component " + type); 
        }
    }
    
}
