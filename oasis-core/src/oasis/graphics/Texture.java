package oasis.graphics;

public abstract class Texture extends GraphicsResource<NativeTextureResource> {

    /**
     * Pixel format 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum Format {
        
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
        
        private Format(int bytes, boolean depth) {
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
    
    /**
     * Type of texture 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum Type {
        
        /**
         * 2D texture 
         */
        TEXTURE_2D, 
        
        /**
         * Cubemap (6 2D textures) 
         */
        TEXTURE_CUBE, 
        
        /**
         * Array of 2D textures 
         */
        TEXTURE_2D_ARRAY, 
        
        /**
         * 3D texture 
         */
        TEXTURE_3D, 
    }
    
    /**
     * Magnification filter for textures. Used 
     * when an image is drawn larger than it 
     * actually is
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum MagFilter {
        
        /**
         * Sharp, pixelated sampling of textures. 
         * Looks good for pixel art
         */
        NEAREST, 
        
        /**
         * Blurs the sampling of textures. Looks good 
         * on higher-res images
         */
        LINEAR;
        
    }

    /**
     * Minification filter for textures. Used
     * when an image is drawn smaller than it 
     * actually is 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum MinFilter {
        
        /**
         * Pixelated 
         */
        NEAREST, 
        
        /**
         * Linearly interpolated, blurry 
         */
        LINEAR, 
        
        /**
         * Pixelated, with mipmaps 
         */
        NEAREST_MIPMAP_NEAREST, 
        
        /**
         * Pixelated, with linearly interpolated mipmaps 
         */
        NEAREST_MIPMAP_LINEAR, 
        
        /**
         * Linearly interpolated, with mipmaps 
         */
        LINEAR_MIPMAP_NEAREST, 
        
        /**
         * Linearly interpolated, with linearly interpolated
         * mipmaps. Also known as Tri-linear filtering 
         */
        LINEAR_MIPMAP_LINEAR;
        
    }
    
    /**
     * Wrap mode for sampling textures 
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum WrapMode {
        
        /**
         * When sampling past the edge of a texture, return 
         * the color of the edge of the texture 
         */
        CLAMP_TO_EDGE, 
        
        /**
         * When sampling past the edge of a texture, return 
         * value of the coordinate % 1 
         */
        REPEAT;
        
    }
    
    private Format format; 
    private int width; 
    private int height; 
    private MinFilter minFilter = MinFilter.LINEAR_MIPMAP_LINEAR; 
    private MagFilter magFilter = MagFilter.LINEAR; 
    private WrapMode wrapU = WrapMode.REPEAT; 
    private WrapMode wrapV = WrapMode.REPEAT; 
    private int levels = 4; 
    
    protected boolean needsUpdate = true; 
    protected boolean needsParamUpdate = true; 
    
    public Texture(Format format, int width, int height) {
        this.format = format; 
        this.width = width; 
        this.height = height; 
    }
    
    public void upload() {
        if (needsUpdate) {
            super.upload(); 
            needsUpdate = false; 
            needsParamUpdate = false; 
        }
        else if (needsParamUpdate) {
            getNativeResource().updateParams(); 
            needsParamUpdate = false; 
        }
    }
    
    public Format getFormat() {
        return format; 
    }
    
    public int getWidth() {
        return width; 
    }
    
    public int getHeight() {
        return height; 
    }
    
    public MinFilter getMinFilter() {
        return minFilter; 
    }
    
    public MagFilter getMagFilter() {
        return magFilter; 
    }
    
    public WrapMode getUWrapMode() {
        return wrapU; 
    }
    
    public WrapMode getVWrapMode() {
        return wrapV; 
    }
    
    public int getMipmapLevels() {
        return levels; 
    }
    
    public void setMinFilter(MinFilter min) {
        minFilter = min; 
        needsParamUpdate = true; 
    }
    
    public void setMagFilter(MagFilter mag) {
        magFilter = mag; 
        needsParamUpdate = true; 
    }
    
    public void setFilters(MinFilter min, MagFilter mag) {
        setMinFilter(min); 
        setMagFilter(mag); 
    }
    
    public void setUWrapMode(WrapMode u) {
        wrapU = u; 
        needsParamUpdate = true; 
    }
    
    public void setVWrapMode(WrapMode v) {
        wrapV = v; 
        needsParamUpdate = true; 
    }
    
    public void setWrapModes(WrapMode u, WrapMode v) {
        setUWrapMode(u); 
        setVWrapMode(v); 
    }
    
    public void setMipmapLevels(int levels) {
        this.levels = levels; 
        needsParamUpdate = true; 
    }
    
}
