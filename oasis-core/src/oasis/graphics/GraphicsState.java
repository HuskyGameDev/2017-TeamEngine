package oasis.graphics;

public class GraphicsState {

    /**
     * Blending modes 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum BlendMode {
        
        /**
         * All color 
         */
        ONE, 
        
        /**
         * No color 
         */
        ZERO, 
        
        /**
         * Color multiplied by the source alpha 
         */
        SRC_ALPHA, 
        
        /**
         * Color multiplied by (1 - a), where a = source alpha  
         */
        ONE_MINUS_SRC_ALPHA, 
        
    }
    
    /**
     * 
     * @author Nicholas Hamilton 
     *
     */
    public enum FillMode {

        /**
         * Normal
         */
        FILL, 
        
        /**
         * Wireframe 
         */
        LINE, 
        
        /**
         * Points 
         */
        POINT; 
        
    }
    
    /**
     * Polygon face winding, culls the opposite winding
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum FrontFace {
        
        /**
         * No culling 
         */
        BOTH, 
        
        /**
         * Show faces that are in clockwise order 
         */
        CW, 
        
        /**
         * Show faces that are in counter-clockwise order 
         */
        CCW; 
        
    }
    
    private BlendMode srcBlendMode = BlendMode.ONE; 
    private BlendMode dstBlendMode = BlendMode.ZERO; 
    private FillMode fillMode = FillMode.FILL; 
    private FrontFace frontFace = FrontFace.CCW; 
    private boolean depthWrite = true; 
    private boolean depthTest = true; 
    
    public GraphicsState() {}

    public GraphicsState(GraphicsState state) {
        if (state != null) {
            this.srcBlendMode = state.srcBlendMode; 
            this.dstBlendMode = state.dstBlendMode; 
            this.fillMode = state.fillMode; 
            this.frontFace = state.frontFace; 
            this.depthWrite = state.depthWrite; 
            this.depthTest = state.depthTest; 
        }
    }
    
    public BlendMode getSourceBlendMode() {
        return srcBlendMode; 
    }
    
    public BlendMode getDestBlendMode() {
        return dstBlendMode; 
    }
    
    public FillMode getFillMode() {
        return fillMode; 
    }
    
    public FrontFace getFrontFace() {
        return frontFace; 
    }
    
    public boolean isDepthWriteEnabled() {
        return depthWrite; 
    }
    
    public boolean isDepthTestEnabled() {
        return depthTest; 
    }
    
    public void setSourceBlendMode(BlendMode src) {
        srcBlendMode = src; 
    }
    
    public void setDestBlendMode(BlendMode dst) {
        dstBlendMode = dst; 
    }

    public void setFillMode(FillMode fillMode) {
        this.fillMode = fillMode;
    }

    public void setFrontFace(FrontFace frontFace) {
        this.frontFace = frontFace;
    }

    public void setDepthWriteEnabled(boolean depthWrite) {
        this.depthWrite = depthWrite;
    }

    public void setDepthTestEnabled(boolean depthTest) {
        this.depthTest = depthTest;
    }
    
}
