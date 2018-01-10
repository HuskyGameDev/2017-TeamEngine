package oasis.graphics;

public class GraphicsState {

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
