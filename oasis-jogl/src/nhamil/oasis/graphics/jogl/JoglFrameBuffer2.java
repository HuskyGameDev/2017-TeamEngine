package nhamil.oasis.graphics.jogl;

import com.jogamp.opengl.GL;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.FrameBuffer;
import nhamil.oasis.graphics.Texture;

public class JoglFrameBuffer2 implements FrameBuffer {

    private static final GameLogger log = new GameLogger(JoglFrameBuffer2.class);
    
    private JoglGlWrapper gl;
    private int width, height;
    private int id = 0;
    private AttachmentType colorType, depthType;
    private ColorRgba clearColor;
    private JoglTexture colorTex, depthTex;
    private int colorBuffer, depthBuffer;
    
    public JoglFrameBuffer2(int width, int height, JoglGlWrapper gl) {
        this.gl = gl;
        id = gl.genFramebuffer();
        this.width = width;
        this.height = height;
        clearColor = new ColorRgba(0, 0, 0, 1);
        colorType = AttachmentType.None;
        depthType = AttachmentType.None;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public void dispose() {
        gl.deleteFramebuffer(id);
        gl.deleteRenderbuffer(colorBuffer);
        gl.deleteRenderbuffer(depthBuffer);
        id = colorBuffer = depthBuffer = 0;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isValid() {
        bind();
        return gl.checkFramebufferStatus() && (colorType != AttachmentType.None || depthType != AttachmentType.None);
    }

    @Override
    public void setClearColor(ColorRgba color) {
        clearColor = color;
    }

    @Override
    public void clear() {
        bind();
        if (colorType == AttachmentType.Texture) {
            colorTex.clear();
        }
        if (depthType == AttachmentType.Texture) {
            depthTex.clear();
        }
        gl.clearColor(clearColor);
        gl.clear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public void bind() {
        gl.bindFramebuffer(id);
        gl.viewport(0, 0, width, height);
    }

    @Override
    public void unbind() {
        gl.bindFramebuffer(0);
        gl.viewport(0, 0, gl.getContext().getWidth(), gl.getContext().getHeight());
    }

    @Override
    public void setSize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setColorAttachmentType(AttachmentType type) {
        bind();
        switch (type) {
        case Texture: 
            if (colorType == AttachmentType.RenderBuffer) setColorAttachmentType(AttachmentType.None);
            log.debug("Attaching color texture");
            colorTex = new JoglTexture(width, height, gl);
            gl.framebufferTextureColor(colorTex.getId());
            colorType = AttachmentType.Texture;
            break;
            
        case RenderBuffer: 
            if (colorType == AttachmentType.Texture) setColorAttachmentType(AttachmentType.None);
            log.debug("Attaching color render buffer");
            colorBuffer = gl.genRenderbuffer();
            gl.bindRenderbuffer(colorBuffer);
            gl.renderbufferStorageColor(width, height);
            gl.framebufferRenderbufferColor(colorBuffer);
            break;
            
        case None: 
            log.debug("Removing color attachment");
            if (colorType == AttachmentType.Texture) {
                colorTex.dispose();
                colorTex = null;
                gl.framebufferTextureColor(0);
            }
            else if (colorType == AttachmentType.RenderBuffer) {
                gl.deleteRenderbuffer(colorBuffer);
                gl.framebufferRenderbufferColor(0);
                colorBuffer = 0;
            }
            colorType = AttachmentType.None;
            break;
        default:
            log.warning("Attempted to set unknown color attachment type: " + type);
        }
    }

    @Override
    public void setDepthAttachmentType(AttachmentType type) {
        bind();
        switch (type) {
        case Texture: 
            if (depthType == AttachmentType.RenderBuffer) setDepthAttachmentType(AttachmentType.None);
            log.debug("Attaching depth texture");
            // TODO FIX DEPTH TEXTURE
            log.warning("Currently depth textures may not work!");
            depthTex = new JoglTexture(width, height, Texture.Type.Depth, gl);
            gl.framebufferTextureDepth(depthTex.getId());
            depthType = AttachmentType.Texture;
            break;
            
        case RenderBuffer: 
            if (depthType == AttachmentType.Texture) setDepthAttachmentType(AttachmentType.None);
            log.debug("Attaching depth render buffer");
            depthBuffer = gl.genRenderbuffer();
            gl.bindRenderbuffer(depthBuffer);
            gl.renderbufferStorageDepth(width, height);
            gl.framebufferRenderbufferDepth(depthBuffer);
            break;
            
        case None: 
            log.debug("Removing depth attachment");
            if (depthType == AttachmentType.Texture) {
                depthTex.dispose();
                depthTex = null;
                gl.framebufferTextureDepth(0);
            }
            else if (depthType == AttachmentType.RenderBuffer) {
                gl.deleteRenderbuffer(depthBuffer);
                gl.framebufferRenderbufferDepth(0);
                depthBuffer = 0;
            }
            depthType = AttachmentType.None;
            break;
        default:
            log.warning("Attempted to set unknown depth attachment type: " + type);
        }
    }

    @Override
    public AttachmentType getColorAttachmentType() {
        return colorType;
    }

    @Override
    public AttachmentType getDepthAttachmentType() {
        return depthType;
    }

    @Override
    public Texture getColorTexture() {
        if (colorType == AttachmentType.Texture) {
            return colorTex;
        }
        else {
            log.warning("Attempted to access color texture attachment that doesn't exist");
            return null;
        }
    }

    @Override
    public Texture getDepthTexture() {
        if (depthType == AttachmentType.Texture) {
            return depthTex;
        }
        else {
            log.warning("Attempted to access depth texture attachment that doesn't exist");
            return null;
        }
    }
}
