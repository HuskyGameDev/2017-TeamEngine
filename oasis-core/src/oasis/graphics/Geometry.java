package oasis.graphics;

public abstract class Geometry extends GraphicsResource {

    /**
     * Types of geometry to draw 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum Primitive {
        
        /**
         * Draw points, uses one vertex for each
         */
        POINT_LIST(1), 
        
        /** 
         * Draw lines, uses two vertices for each 
         */
        LINE_LIST(2), 
        
        /**
         * Draw line strip, first line uses two 
         * vertices, and each line after uses the 
         * last vertex and one more 
         */
        LINE_STRIP(2), 
        
        /**
         * Draw triangles, uses three vertices for each 
         */
        TRIANGLE_LIST(3), 
        
        /**
         * Draw triangle strip, first triangle uses three 
         * vertices, and each triangle after uses the last 
         * two vertices and one more 
         */
        TRIANGLE_STRIP(3); 
        
        private final int points; 
        
        private Primitive(int points) { 
            this.points = points; 
        }
        
        /**
         * Number of vertices for one of the primitive type 
         * @return
         */
        public int getPointCount() { 
            return points; 
        }
        
    }
    
    private Primitive primitive = Primitive.TRIANGLE_LIST; 
    private IndexBuffer ib; 
    private VertexBuffer[] vbs; 
    
    public Geometry() {
        vbs = new VertexBuffer[0]; 
    }
    
    public Primitive getPrimitive() {
        return primitive; 
    }
    
    public boolean isValid() {
        return vbs.length > 0; 
    }
    
    public IndexBuffer getIndexBuffer() {
        return ib; 
    }
    
    public VertexBuffer[] getVertexBuffers() {
        return vbs.clone(); 
    }
    
    public int getVertexBufferCount() {
        return vbs.length; 
    }
    
    public VertexBuffer getVertexBuffer(int index) {
        return vbs[index]; 
    }
    
    public void setPrimitive(Primitive prim) {
        this.primitive = prim; 
    }
    
    public void setIndexBuffer(IndexBuffer ib) {
        this.ib = ib; 
    }
    
    public void setVertexBuffers(VertexBuffer... vbs) {
        this.vbs = vbs.clone(); 
    }
    
    public void setVertexBuffer(VertexBuffer vb) {
        setVertexBuffers(vb); 
    }

    public boolean hasIndexBuffer() {
        return ib != null;
    }
    
}
