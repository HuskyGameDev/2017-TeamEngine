package oasis.entity;

public class ComponentId<T extends Component> {

    private Class<T> type; 
    private int id; 
    
    public ComponentId(Class<T> type, int id) {
        this.id = id; 
        this.type = type; 
    }
    
    public Class<T> getType() {
        return type; 
    }
    
    public int getId() {
        return id; 
    }
    
}
