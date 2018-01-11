package oasis.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oasis.math.Vector3;

public class LightList {

    private List<Light> lights; 
    private Vector3 ambient; 
    
    public LightList() {
        lights = new ArrayList<>(); 
        ambient = new Vector3(); 
    }
    
    public void clear() {
        lights.clear(); 
        ambient.set(0); 
    }
    
    public void add(Light light) {
        lights.add(light); 
    }
    
    public void remove(Light light) {
        Iterator<Light> it = lights.iterator(); 
        
        while (it.hasNext()) {
            if (it.next().equals(light)) {
                it.remove(); 
            }
        }
    }
    
    public void setAmbient(Vector3 color) {
        ambient.set(color); 
    }
    
    public int getLightCount() {
        return lights.size(); 
    }
    
    public Light get(int index) {
        return lights.get(index); 
    }
    
    public Vector3 getAmbient() {
        return new Vector3(ambient); 
    }
    
}