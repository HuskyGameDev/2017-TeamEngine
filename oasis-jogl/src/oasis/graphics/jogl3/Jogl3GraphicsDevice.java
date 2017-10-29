package oasis.graphics.jogl3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.glu.GLU;

import oasis.core.EngineException;
import oasis.graphics.BlendMode;
import oasis.graphics.BufferUsage;
import oasis.graphics.ColorRgba;
import oasis.graphics.FrontFace;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.IndexBuffer;
import oasis.graphics.Primitive;
import oasis.graphics.RenderTarget;
import oasis.graphics.Shader;
import oasis.graphics.Texture;
import oasis.graphics.Texture2D;
import oasis.graphics.VertexArray;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;

// TODO bind textures before draw call 
public class Jogl3GraphicsDevice implements GraphicsDevice {

    protected GL3 gl;
    protected Jogl3Context context; 
    
    protected Jogl3Display display; 
    
    private Jogl3VertexArray currentVao = null; 
    private Jogl3Shader currentShader = null; 
    private Jogl3Texture[] currentTextures; 
    private Jogl3FrameBuffer currentFbo = null; 
    private boolean depthTest = true; 
    private BlendMode srcBlend = BlendMode.ONE, dstBlend = BlendMode.ZERO; 
    private FrontFace frontFace = FrontFace.BOTH; 
    private boolean depthWrite = true; 
    
    public Jogl3GraphicsDevice(Jogl3Display display) {
        this.display = display; 
        context = new Jogl3Context(this); 
        currentTextures = new Jogl3Texture[this.getMaxTextureCount()]; 
    }
    
    public void getError(String cmd) {
        int i = gl.glGetError(); 
        if (i == 0) return; 
        
        GLU glu = new GLU(); 
        
        throw new EngineException("GLERROR: " + cmd + ": " + i + ": " + glu.gluErrorString(i));
    }
    
    public void init() {
//        gl.setSwapInterval(1);
        
        gl.glEnable(GL.GL_DEPTH_TEST);
        getError("glEnable (depth test)"); 
        gl.glEnable(GL.GL_BLEND);
        getError("glEnable (blend)"); 
        gl.glBlendEquation(GL.GL_FUNC_ADD);
        getError("glBlendEquation (func add)"); 
        gl.glBlendFunc(Jogl3Convert.getBlendMode(srcBlend), Jogl3Convert.getBlendMode(dstBlend));
        getError("glBlendFunc"); 
        
        gl.glDisable(GL.GL_CULL_FACE);
        getError("glEnable (cull face)"); 
        gl.glCullFace(GL.GL_BACK);
        getError("glCullFace"); 
        
        gl.glDepthFunc(GL.GL_LEQUAL);
        getError("glDepthFunc"); 
        
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL); 
    }
    
    @Override
    public boolean isDepthTestEnabled() {
        return depthTest; 
    }
    
    @Override
    public boolean isDepthWriteEnabled() {
        return depthWrite; 
    }
    
    @Override
    public void setDepthTestEnabled(boolean enabled) {
        if (depthTest != enabled) {
            depthTest = enabled; 
            if (enabled) {
                gl.glEnable(GL.GL_DEPTH_TEST);
            }
            else {
                gl.glDisable(GL.GL_DEPTH_TEST);
            }
        }
    }
    
    @Override
    public void setDepthWriteEnabled(boolean enabled) {
        if (depthWrite != enabled) {
            depthWrite = enabled; 
            gl.glDepthMask(depthWrite);
        }
    }
    
    @Override
    public int getScreenWidth() {
        return display.getWidth(); 
    }
    
    @Override
    public int getScreenHeight() {
        return display.getHeight(); 
    }
    
    @Override
    public int getWidth() {
        return currentFbo != null ? currentFbo.getWidth() : display.getWidth(); 
    }
    
    @Override
    public int getHeight() {
        return currentFbo != null ? currentFbo.getHeight() : display.getHeight(); 
    }
    
    @Override
    public Shader createShader(String vertex, String fragment) {
        return new Jogl3Shader(this, vertex, fragment); 
    }
    
    @Override
    public RenderTarget createRenderTarget(int width, int height) {
    	return new Jogl3FrameBuffer(this, width, height); 
    }
    
    public RenderTarget createRenderTarget(int width, int height, Texture.Format depthBuffer, Texture.Format... colorBuffers) {
        Jogl3FrameBuffer fbo = new Jogl3FrameBuffer(this, width, height); 
        if (depthBuffer != null) {
            fbo.setDepthTexture(createTexture2D(depthBuffer, width, height));
        }
        if (colorBuffers != null) {
            for (int i = 0; i < colorBuffers.length; i++) {
                if (colorBuffers[i] != null) {
                    fbo.setColorTexture(i, createTexture2D(colorBuffers[i], width, height));
                }
            }
        }
        return fbo; 
    }

    @Override
    public VertexBuffer createVertexBuffer(VertexFormat format, BufferUsage usage) {
        return new Jogl3VertexBuffer(this, format, usage); 
    }

    @Override
    public IndexBuffer createIndexBuffer(BufferUsage usage) {
        return new Jogl3IndexBuffer(this, usage); 
    }

    @Override
    public VertexArray createVertexArray() {
        return new Jogl3VertexArray(this); 
    }
    
    @Override
    public Texture2D createTexture2D(Texture.Format format, int width, int height) {
        return new Jogl3Texture2D(this, format, width, height); 
    }

    @Override
    public VertexArray getVertexArray() {
        return currentVao; 
    }

    @Override
    public Shader getShader() {
        return currentShader; 
    }

    @Override
    public void setShader(Shader shader) {
        this.currentShader = (Jogl3Shader) shader; 
    }

    @Override
    public void setVertexArray(VertexArray vao) {
        this.currentVao = (Jogl3VertexArray) vao; 
    }

    @Override
    public void clearScreen(ColorRgba color) {
    	gl.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        getError("glClearColor"); 
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
        getError("glClear"); 
    }

    @Override
    public void drawArrays(Primitive prim, int start, int count) {
        preDraw(); 
        gl.glDrawArrays(Jogl3Convert.getPrimitive(prim), start, count);
        getError("glDrawArrays"); 
        postDraw(); 
    }

    @Override
    public void drawElements(Primitive prim, int start, int count) {
        preDraw(); 
        gl.glDrawElements(Jogl3Convert.getPrimitive(prim), count, GL.GL_UNSIGNED_INT, start);
        getError("glDrawElements"); 
        postDraw(); 
    }

    @Override
    public Texture getTexture(int index) {
        return currentTextures[index]; 
    }

    @Override
    public int getMaxTextureCount() {
        // TODO per-stage ? 
        return 16; 
    }

    @Override
    public void setTexture(int index, Texture texture) {
        currentTextures[index] = (Jogl3Texture) texture; 
    } 
    
    private void preDraw() {
        context.bindProgram(currentShader.id); 
        context.bindVao(currentVao.id);
        
        for (int i = 0; i < currentTextures.length; i++) {
            Jogl3Texture t = currentTextures[i]; 
            if (t == null) {
                context.bindTexture(i, GL.GL_TEXTURE_2D, 0);
            }
            else {
                context.bindTexture(i, t.getTarget(), t.id);
            }
        }
    }
    
    private void postDraw() {
        if (currentFbo != null) {
            if (currentFbo.getDepthTexture() != null && currentFbo.getDepthTexture().getMipmaps() > 1) {
                currentFbo.getDepthTexture().generateMipmaps(); 
            }
            
            for (int i = 0; i < currentFbo.getMaxColorTextureCount(); i++) {
                if (currentFbo.getColorTexture(i) != null && currentFbo.getColorTexture(i).getMipmaps() > 1) {
                    currentFbo.getColorTexture(i).generateMipmaps(); 
                }
            }
        }
    }

	@Override
	public RenderTarget getRenderTarget() {
		return currentFbo; 
	}

	@Override
	public void setRenderTarget(RenderTarget fbo) {
		if (fbo == currentFbo) {
			return; 
		}
		
		currentFbo = (Jogl3FrameBuffer) fbo; 
		
		context.bindFbo(currentFbo == null ? 0 : currentFbo.id);
		
		if (currentFbo != null) {
			gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
		}
		else {
			gl.glViewport(0, 0, getWidth(), getHeight());
		}
	}

    @Override
    public BlendMode getSourceBlendMode() {
        return srcBlend; 
    }

    @Override
    public BlendMode getDestBlendMode() {
        return dstBlend; 
    }

    @Override
    public void setBlendMode(BlendMode src, BlendMode dst) {
        srcBlend = src; 
        dstBlend = dst; 
        gl.glBlendFunc(Jogl3Convert.getBlendMode(srcBlend), Jogl3Convert.getBlendMode(dstBlend));
    }

    @Override
    public FrontFace getFrontFace() {
        return frontFace; 
    }

    @Override
    public void setFrontFace(FrontFace face) {
//        if (cullMode == cull) {
//            return; 
//        }
        
        this.frontFace = face; 
        
        if (frontFace == null) {
            frontFace = FrontFace.BOTH; 
        }
        
        switch (frontFace) {
        case BOTH: 
            gl.glDisable(GL.GL_CULL_FACE); 
            gl.glFrontFace(GL.GL_CCW);
            break; 
        case CW: 
            gl.glEnable(GL.GL_CULL_FACE);
            gl.glCullFace(GL.GL_BACK);
            gl.glFrontFace(GL.GL_CW);
            break;
        case CCW: 
            gl.glEnable(GL.GL_CULL_FACE);
            gl.glCullFace(GL.GL_BACK);
            gl.glFrontFace(GL.GL_CCW);
            break; 
        }
    }

}
