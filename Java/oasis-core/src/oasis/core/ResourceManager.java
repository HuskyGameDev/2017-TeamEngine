package oasis.core;

import java.util.HashMap;
import java.util.Map;

import oasis.audio.AudioClip;
import oasis.core.loader.ObjLoader;
import oasis.core.loader.ShaderLoader;
import oasis.core.loader.TextureLoader;
import oasis.core.loader.WavLoader;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;

/**
 * 
 * Loads and caches resources 
 * 
 * @author Nicholas Hamilton 
 *
 */
public final class ResourceManager {

    private static final Logger log = new Logger(ResourceManager.class); 
    
    private static final Map<String, Class<? extends ResourceLoader<?>>> loaders = new HashMap<>(); 
    
    private static final Map<String, Object> resources = new HashMap<>(); 
    
    static {
        addLoader(ObjLoader.class, "obj");
        addLoader(TextureLoader.class, "png", "jpg", "jpeg"); 
        addLoader(ShaderLoader.class, "glsl"); 
        addLoader(WavLoader.class, "wav"); 
    }
    
    private ResourceManager() {} 
    
    /**
     * Add a custom resource loader
     * 
     * @param loader The loader class, the class should have a
     *               default constructor 
     * @param filetypes filetypes supported by the loader 
     */
    public static <T extends ResourceLoader<?>> void addLoader(Class<T> loader, String... filetypes) {
        for (String filetype : filetypes) {
            loaders.put(filetype.toLowerCase(), loader); 
        }
    }
    
    public static <T extends ResourceLoader<?>> void removeLoader(String filetype) {
        loaders.remove(filetype.toLowerCase()); 
    }
    
    public static Object load(String file, boolean reload) {
        if (file == null) {
            throw new OasisException("File name cannot be null"); 
        }
        
        if (resources.containsKey(file)) {
            log.debug("Found resource " + file); 
            if (reload) {
                log.debug("Found resource but reloading anyways"); 
                resources.remove(file); 
            }
            else {
                return resources.get(file); 
            }
        }
        
        int lastPeriod = file.lastIndexOf('.'); 
        
        String extension; 
        
        if (lastPeriod == -1) {
            extension = ""; 
        }
        else {
            extension = file.substring(lastPeriod + 1).toLowerCase(); 
        }
        
        Class<? extends ResourceLoader<?>> clazz = loaders.get(extension); 
        
        if (clazz != null) {
            ResourceLoader<?> loader; 
            try {
                loader = clazz.newInstance(); 
            }
            catch (Exception e) {
                log.warning("Could not create resource loader for extension: \"" + extension + "\"");
                return null; 
            }
            
            Object res = null; 
            try {
                res = loader.load(file); 
                resources.put(file, res); 
                return res; 
            }
            catch (Exception e) {
                log.warning("Could not load resource from \"" + file + "\"");
                return null; 
            }
        }
        else {
            log.warning("No resource loader is available for extension: \"" + extension + "\""); 
            return null; 
        }
    }
    
    public static Object load(String name) {
        return load(name, false); 
    }
    
    public static Mesh loadMesh(String name) {
        return (Mesh) load(name); 
    }
    
    public static Texture2D loadTexture2D(String name) {
        return (Texture2D) load(name); 
    }
    
    public static Shader loadShader(String name) {
        return (Shader) load(name); 
    }
    
    public static AudioClip loadAudioClip(String name) {
        return (AudioClip) load(name); 
    }
    
}
