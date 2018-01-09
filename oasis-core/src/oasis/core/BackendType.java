package oasis.core;

public class BackendType {

    private static final Logger log = new Logger(BackendType.class); 
    
    public static final BackendType JOGL3_DESKTOP = new BackendType("Jogl3-Desktop", "oasis.core.jogl3.Jogl3Backend"); 
    public static final BackendType NULL = new BackendType("Null", null); 
    
    public static final BackendType AUTO = new AutoBackendType(new BackendType[] {
            JOGL3_DESKTOP 
    }); 
    
    private String path; 
    private String name; 
    
    public BackendType(String name, String path) {
        this.name = name; 
        this.path = path; 
    }
    
    public String getName() {
        return name; 
    }
    
    public String getPath() {
        return path; 
    }
    
    public Backend create() {
        Backend p = createPlatform(path); 
        
        if (p == null) {
            log.warning("Could not create platform from class: " + path); 
        }
        
        return p; 
    }
    
    private static Backend createPlatform(String path) {
        try {
            log.debug("Trying to create platform: " + path); 
            Backend p = (Backend) Class.forName(path).newInstance(); 
            
            if (p != null) {
                log.debug("Success!");
                return p; 
            }
        } catch (Exception e) {
        }
        
        log.debug("Failed"); 
        return null; 
    }
    
    public String toString() {
        return name; 
    }
    
    private static class AutoBackendType extends BackendType {
        
        private static final Logger log = new Logger(AutoBackendType.class); 
        
        private BackendType[] types; 
        
        public AutoBackendType(BackendType... types) {
            super("Auto", null); 
            this.types = types.clone(); 
        }
        
        public Backend create() {
            for (BackendType type : types) {
                Backend p = createPlatform(type.getPath()); 
                
                if (p != null) return p; 
            }
            
            log.warning("Could not find any suitable platform"); 
            return null; 
        }
        
    }
    
}
