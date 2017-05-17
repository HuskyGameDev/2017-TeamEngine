package nhamil.oasis.graphics.jogl;

import nhamil.oasis.graphics.Bitmap;
import nhamil.oasis.graphics.Texture;

public class JoglTexture implements Texture {

    private JoglGlWrapper gl;
    private int id = 0;
    private int width, height;
    private Filter minFilter, maxFilter;
    private Wrap wrap;
    
    public JoglTexture(JoglGlWrapper gl) {
        this.gl = gl;
        width = height = 0;
        id = gl.genTexture();
        minFilter = Filter.Nearest;
        maxFilter = Filter.Nearest;
        wrap = Wrap.Clamp;
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texParameterMinMagFilter(minFilter, maxFilter);
        gl.texParameterWrap(wrap);
        gl.bindTexture(old);
    }
    
    public JoglTexture(int width, int height, JoglGlWrapper gl) {
        this(gl);
        this.width = width;
        this.height = height;
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texImage(width, height, null);
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
//        gl.texParameterMinMagFilter(minFilter, maxFilter);
        gl.bindTexture(old);
    }

    @Override
    public Filter getMinFilter() {
        return minFilter;
    }

    @Override
    public void setMinFilter(Filter filter) {
        if (this.minFilter != filter) {
            minFilter = filter;
            updateFilter();
        }
    }
    
    @Override
    public Filter getMaxFilter() {
        return maxFilter;
    }

    @Override
    public void setMaxFilter(Filter filter) {
        if (this.maxFilter != filter) {
            maxFilter = filter;
            updateFilter();
        }
    }
    
    @Override
    public void setFilter(Filter min, Filter max) {
        if (this.maxFilter != max || this.minFilter != min) {
            maxFilter = max;
            minFilter = min;
            updateFilter();
        }
    }
    
    @Override
    public void setFilter(Filter both) {
        setFilter(both, both);
    }
    
    @Override
    public Wrap getWrap() {
        return wrap;
    }
    
    @Override
    public void setWrap(Wrap wrap) {
        if (this.wrap != wrap) {
            this.wrap = wrap;
            updateWrap();
        }
    }
    
    private void updateFilter() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texParameterMinMagFilter(minFilter, maxFilter);
        gl.bindTexture(old);
    }
    
    private void updateWrap() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        // TODO
        gl.bindTexture(old);
    }

}
