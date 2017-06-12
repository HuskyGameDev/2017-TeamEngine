package oasis.graphics.vertex;

public class VertexDeclaration {

    public static final VertexDeclaration POSITION_COLOR = new VertexDeclaration(
            new ElementType(3, InputType.FLOAT, DataType.FLOAT), 
            new ElementType(4, InputType.FLOAT, DataType.FLOAT)
            ); 
    
    public enum InputType {
        FLOAT, 
        INT, 
        UNSIGNED_INT, 
        SHORT, 
        UNSIGNED_SHORT, 
        BYTE, 
        UNSIGNED_BYTE; 
    }
    
    public enum DataType {
        FLOAT, 
        INT; 
    }
    
    public static class ElementType {
        private final InputType input; 
        private final DataType type; 
        private final int count; 
        
        public ElementType(int count, InputType input, DataType type) {
            this.input = input; 
            this.type = type; 
            this.count = count; 
        }
        
        public InputType getInputType() { 
            return input; 
        }
        
        public DataType getDataType() { 
            return type; 
        }
        
        public int getCount() { 
            return count; 
        }
    }
    
    private ElementType[] elems; 
    private boolean instanced; 
    
    public VertexDeclaration(ElementType... elementTypes) {
        this(false, elementTypes); 
    }
    
    public VertexDeclaration(boolean instanced, ElementType... elementTypes) {
        this.instanced = instanced; 
        elems = elementTypes.clone(); 
    }
    
    public boolean isInstanced() {
        return instanced; 
    }
    
    public int getElementTypeCount() { 
        return elems.length; 
    }
    
    public ElementType getElementType(int index) {
        return elems[index]; 
    }
    
}
