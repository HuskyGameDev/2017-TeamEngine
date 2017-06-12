package oasis.util;

public final class ClassName {

    private ClassName() {}
    
    public static String get(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }
    
}
