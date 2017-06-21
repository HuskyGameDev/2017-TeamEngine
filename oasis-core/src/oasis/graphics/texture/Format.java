package oasis.graphics.texture;

public enum Format {
    RGBA(false), 
    RGBA8(false), 
    RGBA16F(false), 
    RGBA32F(false), 
    DEPTH(true),
    DEPTH24(true), 
    DEPTH32(true);

    private final boolean depth;

    private Format(boolean depth) {
        this.depth = depth;
    }

    public boolean isDepthFormat() {
        return depth;
    }
}
