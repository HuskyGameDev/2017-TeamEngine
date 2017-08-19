package oasis.graphics.jogl3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.graphics.MagFilter;
import oasis.graphics.MinFilter;
import oasis.graphics.Texture;
import oasis.graphics.TextureFormat;
import oasis.graphics.TextureType;

public abstract class Jogl3Texture implements Texture {

    protected int id; 
    
    protected Jogl3GraphicsDevice gd; 
    protected TextureFormat format; 
    protected MinFilter minFilter; 
    protected MagFilter magFilter; 
    protected int mipmaps; 
    
    public Jogl3Texture(Jogl3GraphicsDevice gd, TextureFormat format) {
        this.gd = gd; 
        this.format = format; 
        this.minFilter = MinFilter.NEAREST; 
        this.magFilter = MagFilter.NEAREST; 
        this.mipmaps = 1; 
        
        int[] ids = new int[1]; 
        gd.gl.glGenTextures(1, ids, 0);
        this.id = ids[0]; 
        
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_BASE_LEVEL, 0);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LEVEL, 0);
    }
    
    public abstract int getTarget(); 
    
    public void generateMipmaps() {
        gd.context.bindTexture(getTarget(), id);
        gd.gl.glGenerateMipmap(getTarget());
    }
    
    @Override
    public int getMipmaps() {
        return mipmaps; 
    }
    
    @Override
    public void setMipmaps(int levels) {
        mipmaps = levels; 
        gd.context.bindTexture(getTarget(), id);
        gd.gl.glTexParameteri(getTarget(), GL2.GL_TEXTURE_MAX_LEVEL, mipmaps - 1);
        gd.getError("glTexParameteri (GL_TEXTURE_MAX_LEVEL)");
        generateMipmaps(); 
    }
    
    @Override
    public TextureType getType() {
        return TextureType.TEXTURE_2D; 
    }

    @Override
    public TextureFormat getFormat() {
        return format; 
    }

    @Override
    public MinFilter getMinFilter() {
        return minFilter; 
    }

    @Override
    public MagFilter getMagFilter() {
        return magFilter; 
    }
    
    @Override
    public void setMinFilter(MinFilter min) {
        this.minFilter = min; 
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, Jogl3Convert.getMinFilter(min));
    }

    @Override
    public void setMagFilter(MagFilter mag) {
        this.magFilter = mag; 
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, Jogl3Convert.getMagFilter(mag));
    }

    @Override
    public void setFilters(MinFilter min, MagFilter mag) {
        setMinFilter(min); 
        setMagFilter(mag); 
    }

    @Override
    public void dispose() {
        if (id != 0) {
            gd.gl.glDeleteTextures(1, new int[] { id }, 0);
            id = 0; 
        }
    }

    @Override
    public boolean isDisposed() {
        return id == 0; 
    }

}
