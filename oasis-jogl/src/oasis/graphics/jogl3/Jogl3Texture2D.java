package oasis.graphics.jogl3;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import oasis.core.EngineException;
import oasis.graphics.ColorRgba;
import oasis.graphics.Texture2D;
import oasis.graphics.TextureFormat;
import oasis.graphics.WrapMode;
import oasis.util.BufferUtil;

public class Jogl3Texture2D extends Jogl3Texture implements Texture2D {

    private int width; 
    private int height; 
    private WrapMode wrapS; 
    private WrapMode wrapT; 
    
    private IntBuffer buffer; 
    
    public Jogl3Texture2D(Jogl3GraphicsDevice gd, TextureFormat format, int width, int height) {
        super(gd, format); 
        
        this.gd = gd; 
        this.width = width; 
        this.height = height; 
        this.wrapS = WrapMode.REPEAT; 
        this.wrapT = WrapMode.REPEAT; 
        
        buffer = BufferUtil.createNativeIntBuffer(width * height); 
        
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        
        gd.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, Jogl3Convert.getTextureFormat(format), width, height, 0, Jogl3Convert.getDefaultTextureInputFormat(format), Jogl3Convert.getDefaultTextureInputType(format), null);
    }
    
    @Override
    public int getTarget() {
        return GL.GL_TEXTURE_2D; 
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
    public WrapMode getWrapS() {
        return wrapS; 
    }

    @Override
    public WrapMode getWrapT() {
        return wrapT; 
    }

    @Override
    public void setWrapS(WrapMode s) {
        wrapS = s; 
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, Jogl3Convert.getWrapMode(s));
    }

    @Override
    public void setWrapT(WrapMode t) {
        wrapT = t; 
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        gd.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, Jogl3Convert.getWrapMode(t));
    }

    @Override
    public void setWrapModes(WrapMode s, WrapMode t) {
        setWrapS(s); 
        setWrapT(t); 
    }

    @Override
    public ColorRgba[] getPixels() {
        return getPixels(new ColorRgba[width * height]); 
    }

    @Override
    public ColorRgba[] getPixels(ColorRgba[] out) {
        // TODO Auto-generated method stub
        throw new EngineException("Unsupported operation"); 
    }

    @Override
    public void setPixels(ColorRgba[] data) {
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        
        buffer.clear(); 
        for (int i = 0; i < data.length; i++) {
            buffer.put(data[i].getRgba()); 
        }
        buffer.flip(); 
        
        gd.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA8, width, height, 0, GL.GL_RGBA, GL2.GL_UNSIGNED_INT_8_8_8_8, buffer);
        generateMipmaps(); 
    }
    
    @Override
    public int[] getPixelsRgba() {
        return getPixelsRgba(new int[width * height]); 
    }

    @Override
    public int[] getPixelsRgba(int[] out) {
        // TODO Auto-generated method stub
        throw new EngineException("Unsupported operation"); 
    }

    @Override
    public void setPixelsRgba(int[] rgba) {
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        
        buffer.clear(); 
        buffer.put(rgba); 
        buffer.flip(); 
        
        gd.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, Jogl3Convert.getTextureFormat(format), width, height, 0, GL.GL_RGBA, GL2.GL_UNSIGNED_INT_8_8_8_8, buffer);
        generateMipmaps(); 
    }

    @Override
    public int[] getPixelsArgb() {
        return getPixelsArgb(new int[width * height]); 
    }

    @Override
    public int[] getPixelsArgb(int[] out) {
        // TODO Auto-generated method stub
        throw new EngineException("Unsupported operation"); 
    }

    @Override
    public void setPixelsArgb(int[] argb) {
        gd.context.bindTexture(GL.GL_TEXTURE_2D, id);
        
        buffer.clear(); 
        buffer.put(argb); 
        buffer.flip(); 
        
        gd.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, Jogl3Convert.getTextureFormat(format), width, height, 0, GL.GL_BGRA, GL2.GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
        generateMipmaps(); 
    }
    
}
