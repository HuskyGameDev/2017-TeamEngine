package oasis.graphics;

public class NullUniformValue implements UniformValue {

    public static final UniformValue VALUE = new NullUniformValue(); 
    
    private static final Uniform UNIFORM = new Uniform(Uniform.Type.UNKNOWN, ""); 
    
    @Override
    public Uniform getUniform() {
        return UNIFORM; 
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {}

    @Override
    public void clear() {}

}
