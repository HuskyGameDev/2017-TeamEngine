package oasis.graphics;

public enum TextureFormat {
    RGBA8(false), 
    RGBA16F(false), 
    RGBA32F(false), 
    DEPTH24(true), 
    DEPTH32(true);

    private final boolean depth;

    private TextureFormat(boolean depth) {
        this.depth = depth;
    }

    public boolean isDepthFormat() {
        return depth;
    }
}
