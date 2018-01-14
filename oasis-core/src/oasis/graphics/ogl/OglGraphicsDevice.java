package oasis.graphics.ogl;

import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.graphics.FrontFace;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.GraphicsState;
import oasis.graphics.Mesh;
import oasis.graphics.RenderTarget;
import oasis.graphics.RenderTexture;
import oasis.graphics.Shader;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.math.Vector4;

public class OglGraphicsDevice implements GraphicsDevice {

    private Ogl ogl; 
    
    private int[] vao = new int[1]; 
    
    private GraphicsState curState = null; 
    
    private OglShader curShader = null; 
    private OglRenderTarget curRt = null; 
    
    public OglGraphicsDevice(Ogl ogl) {
        this.ogl = ogl; 
    }
    
    protected static <T> T safeCast(Object res, Class<T> realType) {
        if (res == null) {
            throw new OasisException("Resource is null"); 
        }
        
        try {
            @SuppressWarnings("unchecked")
            T t = (T) res;
            
            return t; 
        }
        catch (Exception e) {
            throw new OasisException("Invalid resource used: " + res); 
        }
    }

    @Override
    public void preRender() {
        ogl.glClearColor(0.7f, 0.8f, 1.0f, 0.0f); 
        ogl.glClear(Ogl.GL_COLOR_BUFFER_BIT | Ogl.GL_DEPTH_BUFFER_BIT); 
        
        if (vao[0] == 0) {
            ogl.glGenVertexArrays(1, vao, 0); 
            ogl.glBindVertexArray(vao[0]); 
        }
        
        ogl.glEnable(Ogl.GL_BLEND);
        ogl.glBlendEquation(Ogl.GL_FUNC_ADD); 
        ogl.glDepthFunc(Ogl.GL_LEQUAL); 
        ogl.glCullFace(Ogl.GL_BACK); 
        
        setRenderTarget(null); 
    }

    @Override
    public void postRender() {
        // don't need to do anything so far 
    }

    @Override
    public void applyState(GraphicsState state) {
        if (curState == null || curState.getDestBlendMode() != state.getDestBlendMode() || curState.getSourceBlendMode() != state.getSourceBlendMode()) {
            int src = OglConvert.getBlendMode(state.getSourceBlendMode()); 
            int dst = OglConvert.getBlendMode(state.getDestBlendMode()); 
            ogl.glBlendFunc(src, dst); 
        }
        
        if (curState == null || curState.getFillMode() != state.getFillMode()) {
            ogl.glPolygonMode(Ogl.GL_FRONT_AND_BACK, OglConvert.getFillMode(state.getFillMode()));
        }
        
        if (curState == null || curState.getFrontFace() != state.getFrontFace()) {
            if (state.getFrontFace() == FrontFace.BOTH || state.getFrontFace() == null) {
                ogl.glDisable(Ogl.GL_CULL_FACE);
            }
            else {
                ogl.glEnable(Ogl.GL_CULL_FACE);
                ogl.glFrontFace(OglConvert.getFrontFace(state.getFrontFace())); 
            }
        }
        
        if (curState == null || curState.isDepthTestEnabled() != state.isDepthTestEnabled()) {
            if (state.isDepthTestEnabled()) {
                ogl.glEnable(Ogl.GL_DEPTH_TEST); 
            }
            else {
                ogl.glDisable(Ogl.GL_DEPTH_TEST); 
            }
        }
        
        if (curState == null || curState.isDepthWriteEnabled() != state.isDepthWriteEnabled()) {
            ogl.glDepthMask(state.isDepthTestEnabled()); 
        }
        
        curState = new GraphicsState(state); 
    }

    @Override
    public void setRenderTarget(RenderTarget rt) {
        if (rt == null) {
            OglRenderTarget.bind(ogl, 0); 
            ogl.glViewport(0, 0, Oasis.getDisplay().getWidth(), Oasis.getDisplay().getHeight()); 
        }
        else if (!rt.isValid()) {
            throw new OasisException("Invalid render target set: " + rt); 
        }
        else {
            OglRenderTarget ort = safeCast(rt, OglRenderTarget.class); 
            
            int fbo = ort.getFboId(); 
            
            OglRenderTarget.bind(ogl, fbo); 
            ogl.glViewport(0, 0, rt.getWidth(), rt.getHeight()); 
        }
    }

    @Override
    public void useShader(Shader shader) {
        if (shader == null) {
            curShader = null; 
        }
        else if (!shader.isValid()) {
            throw new OasisException("Invalid shader cannot be used: " + shader); 
        }
        else {
            curShader = safeCast(shader, OglShader.class); 
            
            curShader.bindAndApplyUniforms(); 
        }
    }

    @Override
    public void drawMesh(Mesh mesh, int submesh) {
        if (mesh == null) {
            throw new OasisException("Cannot draw a null mesh"); 
        }
        if (curShader == null) {
            throw new OasisException("Cannot draw mesh without a shader: " + mesh); 
        }
        
        OglMesh omesh = safeCast(mesh, OglMesh.class); 
        
        curShader.bindAndApplyUniforms(); 
        omesh.draw(submesh); 
    }

    @Override
    public void clearBuffers(Vector4 color, boolean depth) {
        ogl.glClearColor(color.x, color.y, color.z, color.w);
        
        int flags = Ogl.GL_COLOR_BUFFER_BIT; 
        
        if (depth) flags |= Ogl.GL_DEPTH_BUFFER_BIT; 
        
        ogl.glClear(flags); 
    }

    @Override
    public Mesh createMesh() {
        return new OglMesh(ogl); 
    }

    @Override
    public Texture2D createTexture2D(TextureFormat format, int width, int height) {
        return new OglTexture2D(ogl, format, width, height); 
    }

    @Override
    public RenderTexture createRenderTexture(TextureFormat format, int width, int height) {
        return new OglRenderTexture(ogl, format, width, height); 
    }

    @Override
    public Shader createShader(String vs, String fs) {
        return new OglShader(ogl, vs, fs); 
    }

    @Override
    public RenderTarget createRenderTarget() {
        return new OglRenderTarget(ogl); 
    }

}
