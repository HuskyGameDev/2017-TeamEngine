package oasis.graphics;

/**
 * Pixel format
 * 
 * @author Nicholas Hamilton
 *
 */
public enum TextureFormat {

    /**
     * Packed int with 8 bits for each of red, green, blue, alpha, in that order
     */
    RGBA8(8, false),

    /**
     * Half-floats (16 bit) values for red, green, blue, alpha
     */
    RGBA16F(64, false),

    /**
     * 32 bit float values for red, green, blue, alpha
     */
    RGBA32F(128, false),

    /**
     * 16 bit depth 
     */
    DEPTH16(64, true), 
    
    /**
     * 24 bit depth values
     */
    DEPTH24(96, true),

    /**
     * 24 bit depth values with 8 bits for stencil
     */
    DEPTH24STENCIL8(128, true),

    /**
     * 32 bit depth values
     */
    DEPTH32(128, true);

    private final boolean depth;
    private final int bytes;

    private TextureFormat(int bytes, boolean depth) {
        this.bytes = bytes;
        this.depth = depth;
    }

    public int getBytesPerPixel() {
        return bytes;
    }

    /**
     * Check if format is a depth format
     * 
     * @return If format is depth format
     */
    public boolean isDepthFormat() {
        return depth;
    }

    /**
     * Check if format is a depth and stencil format
     * 
     * @return If format is depth-stencil format
     */
    public boolean isDepthStencilFormat() {
        return this == DEPTH24STENCIL8;
    }

}