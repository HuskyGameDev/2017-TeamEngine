package oasis.util;

/**
 * 
 * Shorthand to get the name of a class
 * 
 * @author Nicholas Hamilton 
 *
 */
public final class ClassName {

    private ClassName() {}
    
    public static String get(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }
    
}
