package oasis.graphics.sprite;

import oasis.core.EngineException;
import oasis.graphics.Attribute;
import oasis.graphics.BlendMode;
import oasis.graphics.BufferUsage;
import oasis.graphics.ColorRgba;
import oasis.graphics.CullMode;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexElement;
import oasis.graphics.VertexFormat;
import oasis.math.MathUtil;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;

/**
 * Basic 2D sprite rendering class.  
 * 
 * Shaders have the following uniforms available to them: <br> 
 * 1. Projection: mat4 - Orthographic projection matrix <br> 
 * 2. Texture: sampler2D - Current texture being drawn <br> 
 * 3. Color: vec4 - Current tint of sprites <br> 
 * 4. InvTextureSize: vec2 - Inverse size of texture <br> 
 * 
 * @author Nicholas Hamilton
 *
 */
public class SpriteBatch {

    /**
     * Format used in sprite batch 
     */
    public static final VertexFormat format = new VertexFormat(new VertexElement[] {
            new VertexElement(Attribute.POSITION, 2), 
            new VertexElement(Attribute.TEXTURE, 2) 
    }); 
    
    private static Shader defaultShader = null; 
    
    private static final String defaultVs = ""
            + "#version 120 \n"
            + "attribute vec2 aPosition; "
            + "attribute vec2 aTexCoord; "
            + "varying vec2 vTexCoord; "
            + "uniform mat4 Projection; "
            + "void main() { "
            + "  vTexCoord = aTexCoord; "
            + "  gl_Position = Projection * vec4(aPosition, 0.0, 1.0); "
            + "} "; 
    private static final String defaultFs = ""
            + "#version 120 \n"
            + "varying vec2 vTexCoord; "
            + "uniform sampler2D Texture; "
            + "uniform vec4 Color; "
            + "void main() { "
            + "  gl_FragColor = Color * texture2D(Texture, vTexCoord); "
            + "} "; 
    
    private GraphicsDevice gd; 
    private VertexBuffer vbo; 
    private IndexBuffer ibo; 
    private VertexArray vao; 
    
    private boolean drawing = false; 
    private Texture2D lastTex = null; 
    private Shader shader; 
    private float[] verts; 
    private int curSprites; 
    private int maxSprites; 
    private int vertOffset; 
    private Matrix4 projection; 
    private ColorRgba tint = new ColorRgba(1, 1, 1, 1); 
    
    /**
     * Constructor. Default shader will be used if [shader] is null 
     * 
     * @param gd Graphics device 
     * @param shader Shader to use
     * @param maxSprites Maximum number of sprites before batch will be flushed 
     */
    public SpriteBatch(GraphicsDevice gd, Shader shader, int maxSprites) {
        this.gd = gd; 
        this.shader = shader == null ? getDefaultShader(gd) : shader; 
        this.curSprites = 0; 
        this.maxSprites = maxSprites; 
        this.vertOffset = 4 * format.getFloatCount(); 
        this.verts = new float[maxSprites * vertOffset]; 
        
        this.vbo = gd.createVertexBuffer(format, BufferUsage.DYNAMIC); 
        this.ibo = gd.createIndexBuffer(BufferUsage.STATIC); 
        this.vao = gd.createVertexArray(); 
        
        vao.setVertexBuffer(vbo);
        vao.setIndexBuffer(ibo);
        
        // indices never change
        int[] inds = new int[maxSprites * 6]; 
        for (int i = 0; i < maxSprites; i++) {
            inds[i*6 + 0] = i*4 + 0;
            inds[i*6 + 1] = i*4 + 1; 
            inds[i*6 + 2] = i*4 + 2; 
            inds[i*6 + 3] = i*4 + 0; 
            inds[i*6 + 4] = i*4 + 2; 
            inds[i*6 + 5] = i*4 + 3; 
        }
        ibo.setData(inds);
    }
    
    /**
     * Creates sprite batch with default shader 
     * 
     * @param gd Graphics device 
     * @param maxSprites Max sprites before a flush 
     */
    public SpriteBatch(GraphicsDevice gd, int maxSprites) {
        this(gd, null, maxSprites); 
    }
    
    /**
     * Creates sprite batch with default shader 
     * 
     * @param gd Graphics device 
     */
    public SpriteBatch(GraphicsDevice gd) {
        this(gd, null, 1024); 
    }
    
    private static Shader getDefaultShader(GraphicsDevice gd) {
        if (defaultShader != null) {
            return defaultShader; 
        }
        
        defaultShader = gd.createShader(defaultVs, defaultFs); 
        
        return defaultShader; 
    }
    
    /**
     * Sets shader of sprite batch. Default will be used if null
     * 
     * @param shader Shader 
     */
    public void setShader(Shader shader) {
        if (drawing) {
            flush(); 
        }
        
        if (shader != null) {
            this.shader = shader; 
        }
        else {
            shader = getDefaultShader(gd); 
        }
    }
    
    /**
     * Begin sprite batch rendering. Must be called before attempting
     * to draw 
     */
    public void begin() {
        if (drawing) {
            throw new EngineException("SpriteBatch is already begun drawing"); 
        }
        
        drawing = true; 
        buildMatrices(); 
    }
    
    /**
     * End sprite batch rendering. Call this when you are done rendering 
     */
    public void end() {
        if (!drawing) {
            throw new EngineException("SpriteBatch is already ended drawing"); 
        }
        
        flush(); 
        drawing = false; 
    }
    
    /**
     * Manually flush sprite batch data. This will draw all sprites in the buffer 
     * and empty it 
     */
    public void flush() {
        if (!drawing) {
            throw new EngineException("SpriteBatch is not drawing"); 
        }
        
        if (curSprites == 0) {
            return;
        }
        
        vbo.setVertices(verts);
        
        gd.setVertexArray(vao);
        gd.setShader(shader);
        gd.setTexture(0, lastTex);
        gd.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        gd.setCullMode(CullMode.NONE);
        
        shader.setInt("Texture", 0);
        shader.setVector2("InvTextureSize", new Vector2(1.0f / lastTex.getWidth(), 1.0f / lastTex.getHeight()));
        shader.setVector4("Color", tint.toVector4());
        shader.setMatrix4("Projection", projection); 
        gd.setDepthTestEnabled(false);
        gd.drawElements(Primitive.TRIANGLE_LIST, 0, curSprites * 6);
        
        curSprites = 0; 
    }
    
    /**
     * Get the current texture 
     * 
     * @return Current texture 
     */
    public Texture2D getTexture2D() {
        return lastTex; 
    }
    
    /**
     * Get the current tint
     * 
     * @return Current tint 
     */
    public ColorRgba getTint() {
        return new ColorRgba(tint); 
    }
    
    /**
     * Set the tint to draw textures with 
     * 
     * @param color Color 
     */
    public void setTint(ColorRgba color) {
        if (!tint.equals(color)) {
            if (drawing) {
                flush(); 
            }
            tint = color; 
        }
    }
    
    /**
     * Draw a texture
     * 
     * @param texture Texture to draw 
     * @param x X coordinate 
     * @param y Y coordinate 
     * @param w Width 
     * @param h Height 
     */
    public void draw(Texture2D texture, float x, float y, float w, float h) {
        draw(texture, x, y, w, h, 0, 0, 1, 1, 0, false, false); 
    }
    
    /**
     * Draw a texture 
     * 
     * @param texture Texture to draw 
     * @param x X coordinate 
     * @param y Y coordinate 
     * @param w Width 
     * @param h Height 
     * @param flipX Should the texture be flipped horizontally 
     * @param flipY Should the texture be flipped vertically 
     */
    public void draw(Texture2D texture, float x, float y, float w, float h, boolean flipX, boolean flipY) {
        draw(texture, x, y, w, h, 0, 0, 1, 1, 0, flipX, flipY); 
    }
    
    /**
     * Draw a texture 
     * 
     * @param texture Texture to draw 
     * @param x X coordinate 
     * @param y Y coordinate 
     * @param w Width 
     * @param h Height 
     * @param angle Rotation in degrees CCW 
     * @param flipX Should the texture be flipped horizontally 
     * @param flipY Should the texture be flipped vertically 
     */
    public void draw(Texture2D texture, float x, float y, float w, float h, float angle, boolean flipX, boolean flipY) {
        draw(texture, x, y, w, h, w * 0.5f, h * 0.5f, 1, 1, angle, flipX, flipY); 
    }
    
    /**
     * Draw a texture 
     * 
     * @param texture Texture to draw 
     * @param x X coordinate 
     * @param y Y coordinate 
     * @param w Width 
     * @param h Height 
     * @param originX X position of origin 
     * @param originY Y position of origin 
     * @param angle Rotation in degrees CCW around origin 
     * @param flipX Should the texture be flipped horizontally 
     * @param flipY Should the texture be flipped vertically 
     */
    public void draw(Texture2D texture, float x, float y, float w, float h, float originX, float originY, float angle, boolean flipX, boolean flipY) {
        draw(texture, x, y, w, h, originX, originY, 1, 1, angle, flipX, flipY); 
    }
    
    /**
     * Draw a texture 
     * 
     * @param texture Texture to draw 
     * @param x X coordinate 
     * @param y Y coordinate 
     * @param w Width 
     * @param h Height 
     * @param originX X position of origin 
     * @param originY Y position of origin 
     * @param scaleX Horizontal scaling from origin 
     * @param scaleY Vertical scaling from origin 
     * @param angle Rotation in degrees CCW around origin 
     * @param flipX Should the texture be flipped horizontally 
     * @param flipY Should the texture be flipped vertically 
     */
    public void draw(Texture2D texture, float x, float y, float w, float h, float originX, float originY, float scaleX, float scaleY, float angle, boolean flipX, boolean flipY) {
        check(texture); 
        
        float x1, y1, x2, y2, x3, y3, x4, y4; 
        float u1, v1, u2, v2; 
        
        u1 = flipX ? 1.0f : 0.0f; 
        v1 = flipY ? 1.0f : 0.0f; 
        u2 = flipX ? 0.0f : 1.0f; 
        v2 = flipY ? 0.0f : 1.0f; 
        
        float xOff = x + originX; 
        float yOff = y + originY; 
        
        x1 = (0 - originX) * scaleX; 
        y1 = (0 - originY) * scaleY; 
        x2 = (w - originX) * scaleX; 
        y2 = (0 - originY) * scaleY; 
        x3 = (w - originX) * scaleX; 
        y3 = (h - originY) * scaleY; 
        x4 = (0 - originX) * scaleX; 
        y4 = (h - originY) * scaleY; 
        
        if (angle != 0) {
            float cos = MathUtil.cos(angle); 
            float sin = MathUtil.sin(angle); 
            
            float x1_, y1_, x2_, y2_, x3_, y3_, x4_, y4_; 
            
            x1_ = x1 * cos - y1 * sin; 
            y1_ = x1 * sin + y1 * cos; 
            x2_ = x2 * cos - y2 * sin; 
            y2_ = x2 * sin + y2 * cos; 
            x3_ = x3 * cos - y3 * sin; 
            y3_ = x3 * sin + y3 * cos; 
            x4_ = x4 * cos - y4 * sin; 
            y4_ = x4 * sin + y4 * cos; 
            
            x1 = x1_; 
            y1 = y1_; 
            x2 = x2_;
            y2 = y2_; 
            x3 = x3_; 
            y3 = y3_; 
            x4 = x4_;
            y4 = y4_; 
        }
        
        addSprite(xOff + x1, yOff + y1, xOff + x2, yOff + y2, xOff + x3, yOff + y3, xOff + x4, yOff + y4, u1, v1, u2, v2); 
    }
    
    private void buildMatrices() {
        projection = Matrix4.createOrthographic(new Vector3(0, -1, 0), new Vector3(gd.getWidth(), gd.getHeight(), 1)); 
    }
    
    private void check(Texture2D texture) {
        if (!drawing) {
            throw new EngineException("Cannot draw, begin() must first be called"); 
        }
        
        if (lastTex != texture) {
            flush(); 
            lastTex = texture; 
        }
    }
    
    private void addSprite(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float u1, float v1, float u2, float v2) {
        verts[curSprites*vertOffset + 0] = x1; 
        verts[curSprites*vertOffset + 1] = y1; 
        verts[curSprites*vertOffset + 2] = u1; 
        verts[curSprites*vertOffset + 3] = v1; 
        
        verts[curSprites*vertOffset + 4] = x2; 
        verts[curSprites*vertOffset + 5] = y2; 
        verts[curSprites*vertOffset + 6] = u2; 
        verts[curSprites*vertOffset + 7] = v1; 
        
        verts[curSprites*vertOffset + 8] = x3; 
        verts[curSprites*vertOffset + 9] = y3; 
        verts[curSprites*vertOffset + 10] = u2; 
        verts[curSprites*vertOffset + 11] = v2; 
        
        verts[curSprites*vertOffset + 12] = x4; 
        verts[curSprites*vertOffset + 13] = y4; 
        verts[curSprites*vertOffset + 14] = u1; 
        verts[curSprites*vertOffset + 15] = v2; 
        
        curSprites++; 
        if (curSprites == maxSprites) {
            flush(); 
        }
    }
    
}
