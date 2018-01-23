package oasis.entity;

import java.util.ArrayList;
import java.util.List;

public class ComponentPool<T extends Component> {

    private ComponentFactory<T> factory; 
    private List<T> list = new ArrayList<>(); 
    
    public ComponentPool(ComponentFactory<T> factory) {
        this.factory = factory; 
    }
    
    public T get(int index) {
        return list.get(index); 
    }
    
    public void ensureSize(int minSize) {
        if (list.size() < minSize) {
            int grow = minSize - list.size(); 
            
            for (int i = 0; i < grow; i++) {
                list.add(factory.create()); 
            }
        }
    }
    
}
