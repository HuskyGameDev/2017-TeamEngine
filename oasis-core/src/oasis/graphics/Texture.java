package oasis.graphics;

import java.nio.ByteBuffer;

import oasis.graphics.texture.Rgba32fCodec;
import oasis.graphics.texture.Rgba8Codec;
import oasis.math.Vector4;

public abstract class Texture extends GraphicsResource {

    public interface Codec {

        void setPixelInt(ByteBuffer buffer, int rgba); 
        
        int getPixelInt(ByteBuffer buffer); 
        
        void setPixelVector4(ByteBuffer buffer, Vector4 in); 
        
        void getPixelVector4(ByteBuffer buffer, Vector4 out); 
        
    }
    
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
        RGBA8(8, false, new Rgba8Codec()), 
        
        /**
         * Half-floats (16 bit) values for red, green, blue, alpha 
         */
        RGBA16F(64, false, null), 
        
        /**
         * 32 bit float values for red, green, blue, alpha 
         */
        RGBA32F(128, false, new Rgba32fCodec()), 
        
        /**
         * 24 bit depth values
         */
        DEPTH24(96, true, null), 
        
        /**
         * 24 bit depth values with 8 bits for stencil 
         */
        DEPTH24STENCIL8(128, true, null), 
        
        /**
         * 32 bit depth values
         */
        DEPTH32(128, true, null);

        private final boolean depth;
        private final int bytes; 
        private final Codec codec; 
        
        private Format(int bytes, boolean depth, Codec codec) {
            this.bytes = bytes; 
            this.depth = depth;
            this.codec = codec; 
        }
        
        public Codec getCodec() {
            return codec; 
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
        CLAMP_EDGE, 
        
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
    
    public Texture(Format format, int width, int height) {
        this.format = format; 
        this.width = width; 
        this.height = height; 
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
    
    public void setMinFilter(MinFilter min) {
        minFilter = min; 
    }
    
    public void setMagFilter(MagFilter mag) {
        magFilter = mag; 
    }
    
    public void setFilters(MinFilter min, MagFilter mag) {
        setMinFilter(min); 
        setMagFilter(mag); 
    }
    
    public void setUWrapMode(WrapMode u) {
        wrapU = u; 
    }
    
    public void setVWrapMode(WrapMode v) {
        wrapV = v; 
    }
    
    public void setWrapModes(WrapMode u, WrapMode v) {
        setUWrapMode(u); 
        setVWrapMode(v); 
    }
    
}
