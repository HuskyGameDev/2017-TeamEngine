package nhamil.oasis.core;

public class EngineType {

    public static final EngineType TEST = new EngineType("Test", "nhamil.oasis.core.test.TestEngine");
    public static final EngineType AWT = new EngineType("AWT", "nhamil.oasis.core.awt.AwtEngine");
    public static final EngineType LWJGL = new EngineType("LWJGL", "nhamil.oasis.core.lwjgl.LwjglEngine");
    public static final EngineType JOGL = new EngineType("JOGL", "nhamil.oasis.core.jogl.JoglEngine");
    
    private final String name;
    private final String classLocation;
    
    public EngineType(String name, String classLocation) {
        this.name = name;
        this.classLocation = classLocation;
    }
    
    public String getName() {
        return name;
    }
    
    public String getClassLocation() {
        return classLocation;
    }
    
    public Engine createEngine() {
        try {
            return (Engine) Class.forName(classLocation).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
