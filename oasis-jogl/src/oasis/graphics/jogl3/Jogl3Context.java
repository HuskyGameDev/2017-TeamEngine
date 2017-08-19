package oasis.graphics.jogl3;

import com.jogamp.opengl.GL;

public class Jogl3Context {

    private int boundProgram = 0; 
    private int boundTextureUnit = 0; 
    private int[] boundTexture = new int[16]; 
    private int[] boundTextureTarget = new int[16]; 
//    private int boundVao = 0; 
//    private int boundVbo = 0; 
    
    private Jogl3GraphicsDevice graphics; 
    
    public Jogl3Context(Jogl3GraphicsDevice graphics) { 
        this.graphics = graphics; 
    }
    
    public void bindFbo(int id) {
    	graphics.gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, id);
    	graphics.getError("glBindFramebuffer");
    }
    
    public void bindTextureUnit(int unit) { 
        if (boundTextureUnit != unit) { 
            graphics.gl.glActiveTexture(GL.GL_TEXTURE0 + unit);
            graphics.getError("glActiveTexture"); 
            
            boundTextureUnit = unit; 
        }
    }
    
    public void bindTexture(int target, int id) { 
        int lastTarget = boundTextureTarget[boundTextureUnit]; 
        if (lastTarget != target && lastTarget != 0) { 
            // unbind last texture target. 
            // this isn't necessary at this time, 
            // but only one target can be bound
            // to each unit during a shader program. 
            graphics.gl.glBindTexture(lastTarget, 0);
            graphics.getError("glBindTexture (0)"); 
        }
        // bind texture target 
        graphics.gl.glBindTexture(target, id);
        graphics.getError("glBindTexture"); 
        
        boundTextureTarget[boundTextureUnit] = target; 
        boundTexture[boundTextureUnit] = id; 
    }
    
    public void bindTexture(int unit, int target, int id) {
        // only change texture unit if [unit] needs to be changed 
        if (boundTextureUnit != unit) {
            if (target != boundTextureTarget[unit] || id != boundTexture[unit]) {
                bindTextureUnit(unit); 
            }
        }
        
        if (target != boundTextureTarget[unit] || id != boundTexture[unit]) {
            bindTexture(target, id);  
        }
    }
    
    public void bindProgram(int id) { 
        if (boundProgram != id) { 
            graphics.gl.glUseProgram(id);
            graphics.getError("glUseProgram"); 
        }
    }
    
    public void bindVao(int id) { 
//        if (boundVao != id) { 
            graphics.gl.glBindVertexArray(id); 
            graphics.getError("glBindVertexArray"); 
//            // TODO need to reset VBO id? Don't think so, but should check 
//            boundVbo = -1; 
//        }
    }
    
    public void bindVbo(int id) { 
//        if (boundVbo != id) { 
            graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
            graphics.getError("glBindBuffer (array buffer)"); 
//        }
    }
    
    public void bindIbo(int id) { 
        // this changes with each VAO, so until I make something
        // to keep track of this, just bind every time
        graphics.gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, id);
        graphics.getError("glBindBuffer (element buffer)"); 
    }
    
}
