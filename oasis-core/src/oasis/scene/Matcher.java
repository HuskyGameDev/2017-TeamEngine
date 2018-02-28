package oasis.scene;

import java.util.ArrayList;
import java.util.List;

public class Matcher {

    private static final Matcher empty = new Matcher(); 
    
    private List<Class<? extends EntityComponent>> require = new ArrayList<>(); 
    private List<Class<? extends EntityComponent>> exclude = new ArrayList<>(); 
    
    private Matcher() {} 
    
    @SafeVarargs
    public static Builder require(Class<? extends EntityComponent>... require) {
        Builder b = new Builder(); 
        return b.require(require); 
    }
    
    @SafeVarargs
    public static Builder exclude(Class<? extends EntityComponent>... exclude) {
        Builder b = new Builder(); 
        return b.exclude(exclude); 
    }
    
    public static Matcher empty() {
        return empty; 
    }
    
    public boolean matches(Entity node) {
        for (int i = 0; i < require.size(); i++) {
            if (!node.hasComponent(require.get(i))) return false; 
        }
        for (int i = 0; i < exclude.size(); i++) {
            if (node.hasComponent(exclude.get(i))) return false; 
        }
        return true; 
    }
    
    public static class Builder {
        
        private Matcher matcher = new Matcher(); 
        
        private Builder() {} 
        
        @SuppressWarnings("unchecked")
        public Builder require(Class<? extends EntityComponent>... require) {
            if (require != null) {
                for (int i = 0; i < require.length; i++) {
                    matcher.require.add(require[i]); 
                }
            }
            return this; 
        }
        
        @SuppressWarnings("unchecked")
        public Builder exclude(Class<? extends EntityComponent>... exclude) {
            if (exclude != null) {
                for (int i = 0; i < exclude.length; i++) {
                    matcher.exclude.add(exclude[i]); 
                }
            }
            return this; 
        }
        
        public Matcher create() {
            Matcher m = matcher; 
            matcher = null; 
            return m; 
        }
        
    } 
    
}
