package oasis.graphics.jogl;

import com.jogamp.opengl.GL;

public class JoglContext {

    private int boundProgram = 0; 
    private int boundTextureUnit = 0; 
    private int[] boundTexture = new int[16]; 
    private int[] boundTextureTarget = new int[16]; 
    private int boundVao = 0; 
    private int boundVbo = 0; 
    
    private JoglGraphicsDevice graphics; 
    
    public JoglContext(JoglGraphicsDevice graphics) { 
        this.graphics = graphics; 
    }
    
    public void bindTextureUnit(int unit) { 
        if (boundTextureUnit != unit) { 
            graphics.gl.glActiveTexture(unit);
        }
    }
    
    public void bindTexture(int target, int id) { 
        int lastTarget = boundTextureTarget[boundTextureUnit]; 
        if (lastTarget != 0) { 
            // unbind last texture target. 
            // this isn't necessary at this time, 
            // but only one target can be bound
            // to each unit during a shader program. 
            graphics.gl.glBindTexture(lastTarget, 0);
        }
        // bind texture target 
        graphics.gl.glBindTexture(target, id);
        
        boundTextureTarget[boundTextureUnit] = target; 
        boundTexture[boundTextureUnit] = id; 
    }
    
    public void bindProgram(int id) { 
        if (boundProgram != id) { 
            graphics.gl.glUseProgram(id);
        }
    }
    
    public void bindVao(int id) { 
        if (boundVao != id) { 
            graphics.gl.glBindVertexArray(id); 
            // TODO need to reset VBO id? Don't think so, but should check 
        }
    }
    
    public void bindVbo(int id) { 
        if (boundVbo != id) { 
            graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, id);
        }
    }
    
    public void bindIbo(int id) { 
        // this changes with each VAO, so until I make something
        // to keep track of this, just bind every time
        graphics.gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, id);
    }
    
}
