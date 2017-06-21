package oasis.graphics.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.graphics.internal.NativeTexture2D;
import oasis.graphics.texture.Format;
import oasis.graphics.texture.MagFilter;
import oasis.graphics.texture.MinFilter;
import oasis.graphics.texture.WrapMode;

public class JoglTexture2D extends JoglGraphicsResource implements NativeTexture2D {

    private int width, height; 
    private Format format; 
    private int id; 
    
    public JoglTexture2D(JoglGraphicsDevice graphics, int width, int height, Format format) {
        super(graphics);
        this.width = width; 
        this.height = height; 
        this.format = format; 
        create(); 
    }
    
    private void create() { 
        int[] ids = new int[1]; 
        graphics.gl.glGenTextures(1, ids, 0); 
        id = ids[0]; 
        graphics.gl.glBindTexture(GL.GL_TEXTURE_2D, id);
        // default to no mipmaps 
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_BASE_LEVEL, 0);
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LEVEL, 0);
        // default nearest filters
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        // default clamp to edge
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        graphics.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        // create empty image
        graphics.gl.glTexImage2D(
                GL.GL_TEXTURE_2D, 
                0, 
                JoglConvert.getTextureFormat(format), 
                width, 
                height, 
                0, 
                JoglConvert.getDefaultTextureInputFormat(format), 
                JoglConvert.getDefaultTextureInputType(format), 
                null);
    }
    
    public void bind(int unit) { 
        graphics.gl.glActiveTexture(unit);
        graphics.gl.glEnable(GL.GL_TEXTURE_2D); 
        graphics.gl.glBindTexture(GL.GL_TEXTURE_2D, id);
    }

    @Override
    public Format getFormat() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MinFilter getMinFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MagFilter getMagFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMinFilter(MinFilter filter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMagFilter(MagFilter filter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setFilters(MinFilter min, MagFilter mag) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public WrapMode getWrapModeS() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WrapMode getWrapModeT() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setWrapModeS(WrapMode wrap) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWrapModeT(WrapMode wrap) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWrapModes(WrapMode s, WrapMode t) {
        // TODO Auto-generated method stub
        
    }

}
