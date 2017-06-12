package oasis.graphics.jogl;

import oasis.graphics.Bitmap;
import oasis.graphics.Texture;

public class JoglTexture implements Texture {

    private JoglGlWrapper gl;
    private int id = 0;
    private int width, height;
    private Filter minFilter, maxFilter;
    private Wrap uWrap, vWrap;
    private Format type;
    
    public JoglTexture(JoglGlWrapper gl) {
        this.gl = gl;
        width = height = 0;
        id = gl.genTexture();
        minFilter = Filter.Nearest;
        maxFilter = Filter.Nearest;
        uWrap = Wrap.Clamp;
        vWrap = Wrap.Clamp;
        type = Format.Rgba;
        
        gl.bindTexture(id);
        gl.texParameterMinMagFilter(minFilter, maxFilter);
        gl.texParameterWrap(uWrap, vWrap);
    }
    
    public JoglTexture(int width, int height, JoglGlWrapper gl) {
        this(gl);
        this.width = width;
        this.height = height;
        bind();
        gl.texImage(width, height, null);
    }
    
    public JoglTexture(int width, int height, Format type, JoglGlWrapper gl) {
        this(gl);
        this.width = width;
        this.height = height;
        bind();
        switch (type) {
        case Depth: 
            gl.texImageDepth(width, height, null);
            break;
        case Rgba:
            gl.texImage(width, height, null);
            break;
        case RgbaFloat:
            gl.texImageFloat(width, height, null);
            break;
        }
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
        // TODO FINISH
    }

    @Override
    public float getAspectRatio() {
        return (float) width / height;
    }

    public void clear() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        if (type == Format.Rgba) {
            gl.texSubImage(width, height, null);
        }
        else if (type == Format.Depth) {
            gl.texSubImageDepth(width, height, null);
        }
        else if (type == Format.RgbaFloat) {
            gl.texSubImageFloat(width, height, null);
        }
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
    
    private void updateFilter() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texParameterMinMagFilter(minFilter, maxFilter);
        gl.bindTexture(old);
    }
    
    private void updateWrap() {
        int old = gl.genTexture();
        gl.bindTexture(id);
        gl.texParameterWrap(uWrap, vWrap);
        gl.bindTexture(old);
    }

    @Override
    public void bind(int unit) {
        gl.activeTexture(unit);
        gl.bindTexture(id);
    }
    
    @Override
    public void bind() {
        gl.bindTexture(id);
    }

    @Override
    public void unbind() {
        gl.bindTexture(0);
    }

    @Override
    public Format getFormat() {
        return type;
    }

    @Override
    public Wrap getUWrap() {
        return uWrap;
    }

    @Override
    public Wrap getVWrap() {
        return vWrap;
    }

    @Override
    public void setUWrap(Wrap wrap) {
        uWrap = wrap;
        updateWrap();
    }

    @Override
    public void setVWrap(Wrap wrap) {
        vWrap = wrap;
        updateWrap();
    }

    @Override
    public Bitmap getPixelData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFilter(Filter min, Filter max) {
        this.minFilter = min;
        this.maxFilter = max;
        updateFilter();
    }

    @Override
    public void setWrap(Wrap u, Wrap v) {
        this.uWrap = u;
        this.vWrap = v;
        updateWrap();
    }

}
