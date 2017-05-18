package nhamil.oasis.graphics.jogl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import nhamil.oasis.core.GameLogger;
import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.Mesh;
import nhamil.oasis.graphics.Texture;

public class JoglGlWrapper {

    private static final GameLogger log = new GameLogger(JoglGlWrapper.class);
    
    private JoglGraphicsContext context;
    private GL2 gl;
    
    private int boundFbo = 0;
    private int boundTex = 0;
    private int boundProg = 0;
    
    public JoglGlWrapper() {
        gl = null;
        context = null;
    }
    
    public void setContext(JoglGraphicsContext c) {
        context = c;
    }
    
    public JoglGraphicsContext getContext() {
        return context;
    }
    
    public void setGL(GL gl) {
        this.gl = gl.getGL2();
    }
    
    public GL getGL() {
        return gl;
    }
    
    public int getBoundFbo() {
        return boundFbo;
    }
    
    public int getBoundTexture() {
        return boundTex;
    }
    
    public int getBoundProgram() {
        return boundProg;
    }
    
    public void clearColor(float r, float g, float b, float a) {
        gl.glClearColor(r, g, b, a);
    }
    
    public void clearColor(ColorRgba c) {
        clearColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }
    
    public void clear(int mask) {
        gl.glClear(mask);
    }
    
    public void viewport(int x, int y, int w, int h) {
        gl.glViewport(x, y, w, h);
    }
    
    public int genFramebuffer() {
        int[] ids = new int[1];
        gl.glGenFramebuffers(1, ids, 0);
        return ids[0];
    }
    
    public void bindFramebuffer(int id) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, id);
        gl.glDrawBuffers(1, new int[] { GL.GL_COLOR_ATTACHMENT0 }, 0);
        boundFbo = id;
    }
    
    public void framebufferTextureColor(int id) {
        gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_TEXTURE_2D, id, 0);
    }
    
    public void framebufferTextureDepth(int id) {
        gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, GL.GL_TEXTURE_2D, id, 0);
    }
    
    public void framebufferRenderbufferDepth(int rbo) {
        gl.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, GL.GL_RENDERBUFFER, rbo);
    }
    
    public void framebufferRenderbufferColor(int rbo) {
        gl.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_RENDERBUFFER, rbo);
    }
    
    public void deleteFramebuffer(int id) {
        gl.glDeleteFramebuffers(1, new int[] { id }, 0);
    }
    
    public int createShaderVertex() {
        return gl.glCreateShader(GL2.GL_VERTEX_SHADER);
    }
    
    public int createShaderFragment() {
        return gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
    }
    
    public boolean setSourceAndCompile(int id, String source) {
        gl.glShaderSource(id, 1, new String[] { source }, new int[] { source.length() }, 0);
        gl.glCompileShader(id);
        int[] status = new int[1];
        gl.glGetShaderiv(id, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] == 0) {
            int[] length = new int[1];
            byte[] text = new byte[512];
            gl.glGetShaderInfoLog(id, 512, length, 0, text, 0);
            log.warning("Compile Error: " + new String(text).trim());
            return false;
        }
        return true;
    }
    
    public void deleteShader(int id) {
        gl.glDeleteShader(id);
    }
    
    public int createProgram() {
        return gl.glCreateProgram();
    }
    
    public boolean attachAndLink(int id, int vId, int fId) {
        gl.glAttachShader(id, vId);
        gl.glAttachShader(id, fId);
        gl.glLinkProgram(id);
        int[] status = new int[1];
        gl.glGetProgramiv(id, GL2.GL_LINK_STATUS, status, 0);
        if (status[0] == 0) {
            int[] length = new int[1];
            byte[] text = new byte[512];
            gl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
            log.warning("Compile Error: " + new String(text).trim());
            return false;
        }
        return true;
    }
    
    public void deleteProgram(int id) {
        gl.glDeleteProgram(id);
    }
    
    public void useProgram(int id) {
        gl.glUseProgram(id);
        boundProg = id;
    }
    
    public void drawReadBuffer(int buffers) {
        gl.glDrawBuffer(buffers);
        gl.glReadBuffer(buffers);
    }
    
    public int uniformLocation(int id, String name) {
        return gl.glGetUniformLocation(id, name);
    }
    
    public void uniformInt(int loc, int val) {
        gl.glUniform1i(loc, val);
    }
    
    public void uniformFloat(int loc, float val) {
        gl.glUniform1f(loc, val);
    }
    
    public void uniformVector2(int loc, float x, float y) {
        gl.glUniform2f(loc, x, y);
    }
    
    public void uniformVector3(int loc, float x, float y, float z) {
        gl.glUniform3f(loc, x, y, z);
    }
    
    public void uniformVector4(int loc, float x, float y, float z, float w) {
        gl.glUniform4f(loc, x, y, z, w);
    }
    
    public void uniformMatrix4(int loc, boolean transpose, float[] mat) {
        gl.glUniformMatrix4fv(loc, 1, transpose, mat, 0);
    }
    
    public int genTexture() {
        int[] id = new int[1];
        gl.glGenTextures(1, id, 0);
        return id[0];
    }
    
    public void deleteTexture(int id) {
        gl.glDeleteTextures(1, new int[] { id }, 0);
    }
    
    public void activeTexture(int unit) {
        gl.glActiveTexture(GL.GL_TEXTURE0 + unit);
    }
    
    public void enableTexture() {
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
    
    public void bindTexture(int id) {
        gl.glBindTexture(GL.GL_TEXTURE_2D, id);
        boundTex = id;
    }
    
    public void texImage(int width, int height, IntBuffer pixels) {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_INT, pixels);
    }
    
    public void texSubImage(int width, int height, IntBuffer pixels) {
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_INT, pixels);
    }
    
    public void texImageFloat(int width, int height, FloatBuffer pixels) {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA16F, width, height, 0, GL.GL_RGBA, GL.GL_FLOAT, pixels);
    }
    
    public void texSubImageFloat(int width, int height, FloatBuffer pixels) {
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA16F, width, height, 0, GL.GL_RGBA, GL.GL_FLOAT, pixels);
    }
    
    public void texImageDepth(int width, int height, FloatBuffer pixels) {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_DEPTH_COMPONENT32, width, height, 0, GL2.GL_DEPTH_COMPONENT, GL.GL_FLOAT, pixels);
    }
    
    public void texSubImageDepth(int width, int height, IntBuffer pixels) {
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_DEPTH_COMPONENT32, width, height, 0, GL2.GL_DEPTH_COMPONENT, GL.GL_FLOAT, pixels);
    }
    
    public void texParameterMinMagFilter(Texture.Filter min, Texture.Filter max) {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filter(min));
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filter(max));
    }
    
    public void texParameterWrap(Texture.Wrap uWrap, Texture.Wrap vWrap) {
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, wrap(uWrap));
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, wrap(vWrap));
    }
    
    public int genRenderbuffer() {
        int[] id = new int[1];
        gl.glGenRenderbuffers(1, id, 0);
        return id[0];
    }
    
    public void renderbufferStorageDepth(int width, int height) {
        gl.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL.GL_DEPTH_COMPONENT32, width, height);
    }
    
    public void renderbufferStorageColor(int width, int height) {
        gl.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL.GL_RGBA, width, height);
    }
    
    public void bindRenderbuffer(int rbo) {
        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, rbo);
    }
    
    public void deleteRenderbuffer(int id) {
        gl.glDeleteRenderbuffers(0, new int[] { id }, 0);
    }
    
    public int genVertexArray() {
        int[] id = new int[1];
        gl.glGenVertexArrays(1, id, 0);
        return id[0];
    }
    
    public void deleteVertexArray(int id) {
        gl.glDeleteVertexArrays(1, new int[] { id }, 0);
    }
    
    public void bindVertexArray(int id) {
        gl.glBindVertexArray(id);
    }
    
    public int genBuffer() {
        int[] id = new int[1];
        gl.glGenBuffers(1, id, 0);
        return id[0];
    }
    
    public void deleteBuffer(int id) {
        gl.glDeleteBuffers(1, new int[] { id }, 0);
    }
    
    public void bindBuffer(int bufferType, int id) {
        gl.glBindBuffer(bufferType, id);
    }
    
    public void bufferData(int bufferType, int size, Buffer data, Mesh.UsageHint hint) {
        gl.glBufferData(bufferType, size, data, usage(hint));
    }
    
    public void bufferSubData(int bufferType, int size, Buffer data) {
        gl.glBufferSubData(bufferType, 0, size, data);
    }
    
    public void enableVertexAttribArray(int unit) {
        gl.glEnableVertexAttribArray(unit);
    }
    
    public void disableVertexAttribArray(int unit) {
        gl.glDisableVertexAttribArray(unit);
    }
    
    public void vertexAttribPointer(int index, int size, int glType, int stride, int offset) {
        gl.glVertexAttribPointer(index, size, glType, false, stride, 0);
    }
    
    private int usage(Mesh.UsageHint hint) {
        switch (hint) {
        case Static:
            return GL.GL_STATIC_DRAW;
        case Dynamic:
            return GL.GL_DYNAMIC_DRAW;
        case Stream:
            return GL2.GL_STREAM_DRAW;
        default:
            log.warning("Unknown usage hint: " + hint);
            return 0;
        }
    }
    
    private int filter(Texture.Filter filter) {
        switch (filter) {
        case Nearest: 
            return GL.GL_NEAREST;
        case Linear: 
            return GL.GL_LINEAR;
        default: 
            log.warning("Unknown filter mode: " + filter);
            return 0;
        }
    }
    
    private int wrap(Texture.Wrap wrap) {
        switch (wrap) {
        case Clamp: 
            return GL.GL_CLAMP_TO_EDGE;
        case Repeat: 
            return GL.GL_REPEAT;
        case MirroredRepeat: 
            return GL.GL_MIRRORED_REPEAT;
        default:
            log.warning("Unknown warp mode: " + wrap);
            return 0;
        }
    }

    public boolean checkFramebufferStatus() {
        int status = gl.glCheckFramebufferStatus(GL.GL_FRAMEBUFFER);
        return status == GL.GL_FRAMEBUFFER_COMPLETE;
    }
    
}
