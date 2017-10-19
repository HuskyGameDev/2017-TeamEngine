package oasis.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * List of objects with reusable IDs
 * 
 * @author Nicholas Hamilton 
 *
 * @param <T>
 */
public class IdList<T> {

    private ArrayList<T> items; 
    private Queue<Integer> freeIds; 
    
    public IdList() {
        items = new ArrayList<>(); 
        freeIds = new LinkedList<>(); 
    }
    
    public int add(T item) { 
        if (freeIds.isEmpty()) {
            items.add(item); 
            return items.size(); 
        }
        else {
            int id = freeIds.remove(); 
            items.set(id - 1, item); 
            return id; 
        }
    }
    
    public T get(int id) { 
        return (id <= 0 || id > items.size()) ? null : items.get(id - 1); 
    }
    
    public void remove(int id) {
        if (id > 0 && id <= items.size() && items.get(id - 1) != null) { 
            items.set(id - 1, null); 
            freeIds.add(id); 
        }
    }
    
}