package nhamil.oasis.graphics.jogl;

import nhamil.oasis.graphics.Bitmap;
import nhamil.oasis.graphics.Texture;

public class JoglTexture implements Texture {

    private JoglGlWrapper gl;
    private int id = 0;
    private int width, height;
    
    public JoglTexture(JoglGlWrapper gl) {
        this.gl = gl;
        width = height = 0;
        id = gl.genTexture();
    }
    
    public JoglTexture(int width, int height, JoglGlWrapper gl) {
        this(gl);
        int old = gl.genTexture();
        this.width = width;
        this.height = height;
        gl.bindTexture(id);
        gl.texImage(width, height, null);
        gl.texParameterMinMagFilter(Filter.Nearest, Filter.Nearest);
        gl.bindTexture(old);
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public void dispose() {
        gl.deleteTexture(id);
        id = 0;
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
    public void setPixelData(Bitmap data) {
        
    }

    @Override
    public float getAspectRatio() {
        return (float) width / height;
    }

    public void clear() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texImage(width, height, null);
        gl.texParameterMinMagFilter(Filter.Nearest, Filter.Nearest);
        gl.bindTexture(old);
    }

}
