package nhamil.oasis.graphics;

import java.util.HashMap;
import java.util.Map;

import nhamil.oasis.math.Vector2;
import nhamil.oasis.math.Vector3;
import nhamil.oasis.math.Vector4;

public class Vertex {

    private Map<String, Float> floats;
    private Map<String, Vector2> vec2;
    private Map<String, Vector3> vec3;
    private Map<String, Vector4> vec4;
    
    public Vertex() {
        floats = new HashMap<>();
        vec2 = new HashMap<>();
        vec3 = new HashMap<>();
        vec4 = new HashMap<>();
    }
    
    public void put(String name, float val) {
        floats.put(name, val);
    }
    
    public void put(String name, Vector2 val) {
        vec2.put(name, val.copy());
    }
    
    public void put(String name, Vector3 val) {
        vec3.put(name, val.copy());
    }
    
    public void put(String name, ColorRgba val) {
        vec4.put(name, val.asVector4());
    }
    
    public boolean hasFloat(String name) {
        return floats.get(name) != null;
    }
    
    public boolean hasVector2(String name) {
        return vec2.get(name) != null;
    }
    
    public boolean hasVector3(String name) {
        return vec3.get(name) != null;
    }
    
    public boolean hasVector4(String name) {
        return vec4.get(name) != null;
    }
    
    public float getFloat(String name) {
        Float val = floats.get(name);
        return val == null ? 0.0f : val.floatValue();
    }
    
    public Vector2 getVector2(String name) {
        Vector2 val = vec2.get(name);
        return val == null ? new Vector2() : val.copy();
    }
    
    public Vector3 getVector3(String name) {
        Vector3 val = vec3.get(name);
        return val == null ? new Vector3() : val.copy();
    }
    
    public Vector4 getVector4(String name) {
        Vector4 val = vec4.get(name);
        return val == null ? new Vector4() : val.copy();
    }
    
}
