//package oasis.old;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.jogamp.opengl.GL;
//import com.jogamp.opengl.GL2;
//
//import oasis.graphics.Attribute;
//import oasis.graphics.jogl.JoglGraphicsDevice;
//import oasis.graphics.jogl.JoglInputLayout;
//import oasis.graphics.vertex.VertexArray;
//import oasis.graphics.vertex.VertexElement;
//import oasis.graphics.vertex.VertexLayout;
//
//public class JoglVertexArrayOld {
//
//    public VertexArray vertArray; 
//    public List<JoglVertexBufferOld> vboList = new ArrayList<>(); 
//    
//    public void init(JoglGraphicsDevice graphics, VertexArray vertArray) { 
//        this.vertArray = vertArray; 
//    }
//    
//    public void update(JoglGraphicsDevice graphics) { 
//        vboList.clear(); 
//        // TODO index buffer 
//        for (int i = 0; i < vertArray.getVertexBufferCount(); i++) { 
//            graphics.update(vertArray.getVertexBuffer(i)); 
//            vboList.add(graphics.vboList.get(vertArray.getVertexBuffer(i).getId())); 
//        }
//    }
//    
//    public void bind(JoglGraphicsDevice graphics, JoglShaderOld shader) { 
//        // TODO 
//        JoglInputLayout input = shader.inputLayout; 
//        
//        GL2 gl = graphics.gl; 
//        
//        for (int i = 0; i < input.attributes.size(); i++) { 
//            Attribute attr = input.attributes.get(i); 
//            int index = input.indices.get(i); 
//            int location = input.location.get(i); 
//            
//            for (JoglVertexBufferOld vbo : vboList) { 
//                VertexLayout layout = vbo.vertBuffer.getVertexLayout(); 
//                VertexElement elem;
//                
//                if ((elem = layout.getElement(attr, index)) != null) { 
//                    int offset = layout.getOffset(attr, index); 
//                    int size = layout.getTotalSizeInBytes(); 
//                    
//                    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo.vbo);
//                    gl.glEnableVertexAttribArray(location);
//                    gl.glVertexAttribPointer(location, elem.getComponentCount(), GL.GL_FLOAT, false, size, offset);
//                }
//            }
//        }
//    }
//    
//}
