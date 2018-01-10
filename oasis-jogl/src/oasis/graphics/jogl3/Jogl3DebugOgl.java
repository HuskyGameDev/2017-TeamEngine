package oasis.graphics.jogl3;

import java.nio.Buffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.glu.GLU;

import oasis.core.Logger;
import oasis.graphics.ogl.Ogl;

public class Jogl3DebugOgl implements Ogl {

    private static final Logger log = new Logger(Jogl3DebugOgl.class); 
    
    private GL3 gl; 
    private GLU glu; 
    
    public void setOgl(GL3 gl) {
        this.gl = gl; 
        gl.setSwapInterval(0);
    }
    
    public void checkError(String desc) {
        int err = gl.glGetError(); 
        
        if (glu == null) glu = new GLU(); 
        
        if (err != GL.GL_NO_ERROR) {
            desc = desc == null ? "" : (" (" + desc + ")"); 
            log.warning("GL Error: " + err + desc + " : " + glu.gluErrorString(err));
        }
        else {
//            log.debug(desc); 
        }
    }

    @Override
    public void glClear(int flags) {
        gl.glClear(flags); 
        checkError("glClear"); 
    }

    @Override
    public void glClearColor(float r, float g, float b, float a) {
        gl.glClearColor(r, g, b, a); 
        checkError("glClearColor"); 
    }

    @Override
    public void glGenBuffers(int count, int[] ids, int offset) {
        gl.glGenBuffers(count, ids, offset);
        checkError("glGenBuffers"); 
    }

    @Override
    public void glDeleteBuffers(int count, int[] ids, int offset) {
        gl.glDeleteBuffers(count, ids, offset); 
        checkError("glDeleteBuffers"); 
    }

    @Override
    public void glBindBuffer(int type, int id) {
        gl.glBindBuffer(type, id); 
        checkError("glBindBuffer"); 
    }

    @Override
    public void glBufferData(int type, long size, Buffer data, int usage) {
        gl.glBufferData(type, size, data, usage); 
        checkError("glBufferData"); 
    }

    @Override
    public void glBufferSubData(int type, long offset, long size, Buffer data) {
        gl.glBufferSubData(type, offset, size, data); 
        checkError("glBufferSubData"); 
    }

    @Override
    public void glGenVertexArrays(int count, int[] ids, int offset) {
        gl.glGenVertexArrays(count, ids, offset); 
        checkError("glGenVertexArrays"); 
    }

    @Override
    public void glDeleteVertexArrays(int count, int[] ids, int offset) {
        gl.glDeleteVertexArrays(count, ids, offset); 
        checkError("glDeleteVertexArrays"); 
    }

    @Override
    public void glBindVertexArray(int id) {
        gl.glBindVertexArray(id); 
        checkError("glBindVertexArray"); 
    }

    @Override
    public void glDrawArrays(int prim, int first, int count) {
        gl.glDrawArrays(prim, first, count);
        checkError("glDrawArrays"); 
    }

    @Override
    public void glDrawElements(int prim, int count, int type, long offset) {
        gl.glDrawElements(prim, count, type, offset);
        checkError("glDrawElements"); 
    }

    @Override
    public void glVertexAttribPointer(int index, int size, int type, boolean norm, int stride, long offset) {
        gl.glVertexAttribPointer(index, size, type, norm, stride, offset); 
        checkError("glVertexAttribPointer"); 
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        gl.glEnableVertexAttribArray(index); 
        checkError("glEnableVertexAttribArray"); 
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        gl.glDisableVertexAttribArray(index); 
        checkError("glDisableVertexAttribArray"); 
    }

    @Override
    public int glCreateProgram() {
        int id = gl.glCreateProgram(); 
        checkError("glCreateProgram"); 
        return id; 
    }

    @Override
    public void glUseProgram(int id) {
        gl.glUseProgram(id);
        checkError("glUseProgram"); 
    }

    @Override
    public void glDeleteProgram(int id) {
        gl.glDeleteProgram(id); 
        checkError("glDeleteProgram"); 
    }

    @Override
    public int glCreateShader(int type) {
        int id = gl.glCreateShader(type);  
        checkError("glCreateShader"); 
        return id; 
    }

    @Override
    public void glDeleteShader(int id) {
        gl.glDeleteShader(id); 
        checkError("glDeleteShader"); 
    }

    @Override
    public void glShaderSource(int id, int lines, String[] text, int[] textLengths, int offset) {
        gl.glShaderSource(id, lines, text, textLengths, offset); 
        checkError("glShaderSource"); 
    }

    @Override
    public void glCompileShader(int id) {
        gl.glCompileShader(id); 
        checkError("glCompileShader"); 
    }

    @Override
    public void glGetShaderiv(int id, int query, int[] out, int offset) {
        gl.glGetShaderiv(id, query, out, offset); 
        checkError("glGetShaderiv"); 
    }

    @Override
    public void glGetShaderInfoLog(int id, int maxLength, int[] length, int offset, byte[] out, int outOffset) {
        gl.glGetShaderInfoLog(id, maxLength, length, offset, out, outOffset); 
        checkError("glGetShaderInfoLog"); 
    }

    @Override
    public void glAttachShader(int program, int shader) {
        gl.glAttachShader(program, shader); 
        checkError("glAttachShader"); 
    }

    @Override
    public void glLinkProgram(int id) {
        gl.glLinkProgram(id); 
        checkError("glLinkProgram"); 
    }

    @Override
    public void glGetProgramiv(int id, int query, int[] out, int offset) {
        gl.glGetProgramiv(id, query, out, offset); 
        checkError("glGetProgramiv"); 
    }

    @Override
    public void glGetProgramInfoLog(int id, int maxLength, int[] length, int offset, byte[] out, int outOffset) {
        gl.glGetProgramInfoLog(id, maxLength, length, offset, out, outOffset); 
        checkError("glGetProgramInfoLog"); 
    }

    @Override
    public void glBindAttribLocation(int id, int index, String name) {
        gl.glBindAttribLocation(id, index, name); 
        checkError("glBindAttribLocation"); 
    }

    @Override
    public void glGetActiveUniform(int id, int index, int maxLength, int[] length, int lengthOffset, int[] size, int sizeOffset, int[] type, int typeOffset, byte[] name, int nameOffset) {
        gl.glGetActiveUniform(id, index, maxLength, length, lengthOffset, size, sizeOffset, type, typeOffset, name, nameOffset); 
        checkError("glGetActiveUniform"); 
    }

    @Override
    public int glGetUniformLocation(int id, String name) {
        int res = gl.glGetUniformLocation(id, name); 
        checkError("glGetUniformLocation"); 
        return res; 
    }

    @Override
    public void glUniform1i(int location, int x) {
        gl.glUniform1i(location, x); 
        checkError("glUniform1i"); 
    }

    @Override
    public void glUniform1f(int location, float x) {
        gl.glUniform1f(location, x); 
        checkError("glUniform1f"); 
    }

    @Override
    public void glUniform2f(int location, float x, float y) {
        gl.glUniform2f(location, x, y); 
        checkError("glUniform2f"); 
    }

    @Override
    public void glUniform3f(int location, float x, float y, float z) {
        gl.glUniform3f(location, x, y, z); 
        checkError("glUniform3f"); 
    }

    @Override
    public void glUniform4f(int location, float x, float y, float z, float w) {
        gl.glUniform4f(location, x, y, z, w); 
        checkError("glUniform4f"); 
    }

    @Override
    public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
        gl.glUniformMatrix3fv(location, count, transpose, value, offset); 
        checkError("glUniformMatrix3fv"); 
    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
        gl.glUniformMatrix4fv(location, count, transpose, value, offset); 
        checkError("glUniformMatrix4fv"); 
    }

    @Override
    public void glEnable(int value) {
        gl.glEnable(value); 
        checkError("glEnable"); 
    }

    @Override
    public void glDisable(int value) {
        gl.glDisable(value); 
        checkError("glDisable"); 
    }

    @Override
    public void glBlendEquation(int eq) {
        gl.glBlendEquation(eq); 
        checkError("glBlendEquation"); 
    }

    @Override
    public void glBlendFunc(int src, int dst) {
        gl.glBlendFunc(src, dst); 
        checkError("glBlendFunc"); 
    }

    @Override
    public void glCullFace(int face) {
        gl.glCullFace(face); 
        checkError("glCullFace"); 
    }

    @Override
    public void glFrontFace(int winding) {
        gl.glFrontFace(winding); 
        checkError("glFrontFace"); 
    }

    @Override
    public void glDepthFunc(int func) {
        gl.glDepthFunc(func); 
        checkError("glDepthFunc"); 
    }

    @Override
    public void glPolygonMode(int face, int type) {
        gl.glPolygonMode(face, type); 
        checkError("glPolygonMode"); 
    }

    @Override
    public void glDepthMask(boolean mask) {
        gl.glDepthMask(mask); 
        checkError("glDepthMask"); 
    }

    @Override
    public void glGenTextures(int count, int[] ids, int offset) {
        gl.glGenTextures(1, ids, offset); 
        checkError("glGenTextures"); 
    }

    @Override
    public void glDeleteTextures(int count, int[] ids, int offset) {
        gl.glDeleteTextures(count, ids, offset); 
        checkError("glDeleteTextures"); 
    }

    @Override
    public void glBindTexture(int target, int id) {
        gl.glBindTexture(target, id); 
        checkError("glBindTexture"); 
    }

    @Override
    public void glTexParameteri(int type, int name, int value) {
        gl.glTexParameteri(type, name, value); 
        checkError("glTexParameteri"); 
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels); 
        checkError("glTexImage2D"); 
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, Buffer pixels) {
        gl.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels); 
        checkError("glTexSubImage2D"); 
    }

    @Override
    public void glActiveTexture(int unit) {
        gl.glActiveTexture(unit);
        checkError("glActiveTexture"); 
    }
    
    @Override
    public void glGenerateMipmap(int target) {
        gl.glGenerateMipmap(target);
        checkError("glGenerateMipmap"); 
    }
    
}
