package oasis.graphics;

public class IndexBuffer extends GraphicsObject {

    public enum DataType {
        SHORT, 
        INT; 
    }
    
    private DataType type; 
    
    public IndexBuffer(GraphicsDevice graphics, DataType type) {
        super(graphics);
    }

    public DataType getDataType() { 
        return type; 
    }
    
}
