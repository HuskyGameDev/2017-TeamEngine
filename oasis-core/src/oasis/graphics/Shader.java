package oasis.graphics;

import java.util.HashMap;

import oasis.math.Matrix4;

public class Shader extends GraphicsResource {

    private String vSource, fSource; 
    private HashMap<String, Uniform> uniformMap; 
    
    public Shader(GraphicsDevice graphics, String vertexSource, String fragmentSource) {
        this.vSource = vertexSource; 
        this.fSource = fragmentSource; 
        uniformMap = new HashMap<>(); 
        
        graphics.update(this); 
    }
    
    // getters
    
    public String getVertexSource() { 
        return vSource; 
    }
    
    public String getFragmentSource() { 
        return fSource; 
    }
    
    // setters 
    
    public void setMatrix4(String name, Matrix4 mat) { 
        // TODO
    }
    
}
