package oasis.graphics.jogl3;

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
    
    public Jogl3Texture(Jogl3GraphicsDevice gd, TextureFormat format) {
        this.gd = gd; 
        this.format = format; 
        this.minFilter = MinFilter.NEAREST; 
        this.magFilter = MagFilter.NEAREST; 
        
        int[] ids = new int[1]; 
        gd.gl.glGenTextures(1, ids, 0);
        this.id = ids[0]; 
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
