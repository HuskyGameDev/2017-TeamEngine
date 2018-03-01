package oasis.scene;

import java.util.HashMap;
import java.util.Map;

import oasis.core.Logger;

public class SceneManager {

    private static final Logger log = new Logger(SceneManager.class); 
    
    private Scene current = null; 
    private Map<String, Scene> scenes = new HashMap<>(); 
    
    public SceneManager() {} 
    
    public void update(float dt) {
        if (hasCurrentScene()) {
            current.update(dt); 
        }
    } 
    
    public void render() {
        if (hasCurrentScene()) {
            current.render(); 
        }
    } 
    
    public boolean hasCurrentScene() {
        return current != null; 
    }
    
    public Scene getCurrentScene() {
        return current; 
    }
    
    public void setCurrentScene(String name) {
        Scene scene = scenes.get(name); 
        if (scene == null) {
            log.warning("Scene with name \"" + name + "\" does not exist and will be created");
            scene = createScene(name); 
        }
        
        current = scene; 
    }
    
    public Scene createAndSetScene(String name) {
        Scene s = createScene(name); 
        setCurrentScene(name); 
        return s; 
    }
    
    public Scene createScene(String name) {
        if (scenes.containsKey(name)) {
            log.warning("Scene with name \"" + name + "\" already exists and will be removed");
            removeScene(name); 
        }
        
        Scene scene = new Scene(); 
        scenes.put(name, scene); 
        
        return scene; 
    }
    
    public void removeScene(String name) {
        scenes.remove(name); 
        // TODO de-init scene 
    }
    
}
