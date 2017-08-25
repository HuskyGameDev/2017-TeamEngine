package oasis.graphics.jogl3;

import com.jogamp.opengl.GL;

import oasis.core.EngineException;
import oasis.graphics.RenderTarget;
import oasis.graphics.Texture;
import oasis.graphics.Texture2D;
import oasis.graphics.Texture2DArray;
import oasis.graphics.Texture3D;
import oasis.graphics.TextureCube;
import oasis.graphics.TextureCube.Face;

public class Jogl3FrameBuffer implements RenderTarget {

	public static final int MAX_COLOR_TEXTURES = 8; 
	
	protected int id; 
	
	private Jogl3GraphicsDevice gd; 
	private int width; 
	private int height; 
	
	private Jogl3Texture[] colorTextures; 
	private Jogl3Texture depthTexture; 
	
	public Jogl3FrameBuffer(Jogl3GraphicsDevice gd, int width, int height) {
		this.gd = gd; 
		this.width = width; 
		this.height = height; 
		
		this.colorTextures = new Jogl3Texture[MAX_COLOR_TEXTURES]; 
		this.depthTexture = null; 
		
		int[] ids = new int[1]; 
		gd.gl.glGenFramebuffers(1, ids, 0);
		this.id = ids[0]; 
	}
	
	@Override
	public void dispose() {
		dispose(false); 
	}
	
	@Override
	public void dispose(boolean textures) {
	    if (id != 0) {
            gd.gl.glDeleteFramebuffers(1, new int[] { id }, 0);
            id = 0; 
        }
	    
	    if (textures) {
	        if (depthTexture != null) depthTexture.dispose(); 
	        for (Jogl3Texture tex : colorTextures) {
	            if (tex != null) tex.dispose(); 
	        }
	    }
	}

	@Override
	public boolean isDisposed() {
		return id == 0; 
	}
	
	@Override
	public boolean isComplete() {
	    if (depthTexture != null) {
	        return true; 
	    }
	    for (Texture tex : colorTextures) {
	        if (tex != null) {
	            return true; 
	        }
	    }
	    return false; 
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
	public int getMaxColorTextureCount() {
		return MAX_COLOR_TEXTURES; 
	}

	@Override
	public Jogl3Texture getColorTexture(int index) {
		return colorTextures[index]; 
	}

	@Override
	public Jogl3Texture getDepthTexture() {
		return depthTexture; 
	}

	@Override
	public void setColorTexture(int index, Texture2D texture) {
		gd.context.bindFbo(id); 
		
		if (texture == null) {
			gd.gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0 + index, GL.GL_TEXTURE_2D, 0, 0);
			gd.getError("glFramebufferTexture2D");
			colorTextures[index] = null; 
			return; 
		}
		
		if (texture.getFormat().isDepthFormat()) {
			throw new EngineException("Cannot set a depth texture as a color texture: " + texture.getFormat()); 
		}
		
		if (texture instanceof Jogl3Texture) {
			colorTextures[index] = (Jogl3Texture) texture; 
		}
		else {
			throw new EngineException("Incompatible texture type: " + texture.getClass()); 
		}
		
		setTexture(colorTextures[index], GL.GL_COLOR_ATTACHMENT0 + index); 
	}

	@Override
	public void setDepthTexture(Texture2D texture) {
	    gd.context.bindFbo(id); 
	    
		if (texture == null) {
			gd.gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, GL.GL_TEXTURE_2D, 0, 0);
			gd.getError("glFramebufferTexture2D");
			depthTexture = null; 
			return; 
		}
		
		if (!texture.getFormat().isDepthFormat()) {
			throw new EngineException("Cannot set a color texture as a depth texture: " + texture.getFormat()); 
		}
		
		if (texture instanceof Jogl3Texture) {
			depthTexture = (Jogl3Texture) texture; 
		}
		else {
			throw new EngineException("Incompatible texture type: " + texture.getClass()); 
		}
		
		setTexture(depthTexture, GL.GL_DEPTH_ATTACHMENT); 
	}
	
	private void setTexture(Jogl3Texture texture, int attachment) {
		if (texture instanceof Jogl3Texture2D) {
			gd.gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, attachment, GL.GL_TEXTURE_2D, texture.id, 0);
			gd.getError("glFramebufferTexture2D");
		}
		else {
			throw new EngineException("Unknown texture type: " + texture.getClass()); 
		}
	}

    @Override
    public void setColorTexture(int index, TextureCube texture, Face face) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

    @Override
    public void setColorTexture(int index, Texture3D texture, int layer) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

    @Override
    public void setColorTexture(int index, Texture2DArray texture, int layer) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

    @Override
    public void setDepthTexture(TextureCube texture, Face face) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

    @Override
    public void setDepthTexture(Texture2D texture, int layer) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

    @Override
    public void setDepthTexture(Texture2DArray texture, int layer) {
        // TODO finish
        throw new EngineException("Not yet supported"); 
    }

}
