package oasis.graphics.sprite;

import oasis.core.EngineException;
import oasis.graphics.Attribute;
import oasis.graphics.BufferUsage;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexElement;
import oasis.graphics.VertexFormat;
import oasis.math.Matrix4;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class SpriteBatch {

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
            + "uniform mat4 uProjection; "
            + "void main() { "
            + "  vTexCoord = aTexCoord; "
            + "  gl_Position = uProjection * vec4(aPosition, 0.0, 1.0); "
            + "} "; 
    private static final String defaultFs = ""
            + "#version 120 \n"
            + "varying vec2 vTexCoord; "
            + "uniform sampler2D uTexture; "
            + "uniform vec4 uColor; "
            + "void main() { "
            + "  gl_FragColor = uColor * texture2D(uTexture, vTexCoord); "
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
    
    public SpriteBatch(GraphicsDevice gd, int maxSprites) {
        this(gd, null, maxSprites); 
    }
    
    public SpriteBatch(GraphicsDevice gd) {
        this(gd, null, 1000); 
    }
    
    private static Shader getDefaultShader(GraphicsDevice gd) {
        if (defaultShader != null) {
            return defaultShader; 
        }
        
        defaultShader = gd.createShader(defaultVs, defaultFs); 
        
        return defaultShader; 
    }
    
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
    
    public void begin() {
        if (drawing) {
            throw new EngineException("SpriteBatch is already begun drawing"); 
        }
        
        drawing = true; 
        buildMatrices(); 
    }
    
    public void end() {
        if (!drawing) {
            throw new EngineException("SpriteBatch is already ended drawing"); 
        }
        
        flush(); 
        drawing = false; 
    }
    
    public void flush() {
        if (!drawing) {
            throw new EngineException("SpriteBatch is not drawing"); 
        }
        
        if (curSprites == 0) {
            return;
        }
        
        vbo.setData(verts);
        
        gd.setVertexArray(vao);
        gd.setShader(shader);
        gd.setTexture(0, lastTex);
        
        shader.setInt("uTexture", 0);
        shader.setFloat("uInvTextureWidth", 1.0f / lastTex.getWidth());
        shader.setFloat("uInvTextureHeight", 1.0f / lastTex.getHeight());
        shader.setVector4("uColor", new Vector4(1.0f, 1.0f, 1.0f, 1.0f));
        shader.setMatrix4("uProjection", projection); 
        gd.drawElements(Primitive.TRIANGLE_LIST, 0, curSprites * 6);
        
        curSprites = 0; 
    }
    
    public void draw(Texture2D texture, float x, float y, float w, float h) {
        check(texture); 
        addSprite(x, y, x + w, y + h, 0, 0, 1, 1); 
    }
    
    private void buildMatrices() {
        projection = Matrix4.createOrthographic(new Vector3(0, 0, 0), new Vector3(gd.getWidth(), gd.getHeight(), 1)); 
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
    
    private void addSprite(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2) {
        // TODO add verts
        verts[curSprites*vertOffset + 0] = x1; 
        verts[curSprites*vertOffset + 1] = y1; 
        verts[curSprites*vertOffset + 2] = u1; 
        verts[curSprites*vertOffset + 3] = v1; 
        
        verts[curSprites*vertOffset + 4] = x2; 
        verts[curSprites*vertOffset + 5] = y1; 
        verts[curSprites*vertOffset + 6] = u2; 
        verts[curSprites*vertOffset + 7] = v1; 
        
        verts[curSprites*vertOffset + 8] = x2; 
        verts[curSprites*vertOffset + 9] = y2; 
        verts[curSprites*vertOffset + 10] = u2; 
        verts[curSprites*vertOffset + 11] = v2; 
        
        verts[curSprites*vertOffset + 12] = x1; 
        verts[curSprites*vertOffset + 13] = y2; 
        verts[curSprites*vertOffset + 14] = u1; 
        verts[curSprites*vertOffset + 15] = v2; 
        
        curSprites++; 
        if (curSprites == maxSprites) {
            flush(); 
        }
    }
    
}
