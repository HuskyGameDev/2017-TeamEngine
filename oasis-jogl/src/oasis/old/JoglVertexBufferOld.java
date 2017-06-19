//package oasis.old;
//
//import java.nio.ByteBuffer;
//import java.nio.FloatBuffer;
//
//import com.jogamp.common.nio.Buffers;
//import com.jogamp.opengl.GL;
//
//import oasis.graphics.jogl.JoglGraphicsDevice;
//import oasis.graphics.vertex.Vertex;
//import oasis.graphics.vertex.VertexBuffer;
//import oasis.graphics.vertex.VertexElement;
//import oasis.graphics.vertex.VertexLayout;
//
//public class JoglVertexBufferOld {
//
//    public int vbo = 0; 
//    public VertexBuffer vertBuffer = null; 
//    public int curSize = 0; 
//    
//    private VertexLayout layout = null; 
//    private ByteBuffer dataBuffer = null; 
//    private int size = 0;
//    
//    public void init(JoglGraphicsDevice graphics, VertexBuffer vertBuffer) { 
//        this.vertBuffer = vertBuffer; 
//        
//        int[] id = new int[1]; 
//        graphics.gl.glGenBuffers(1, id, 0);
//        vbo = id[0]; 
//    }
//    
//    public void update(JoglGraphicsDevice graphics) { 
//        if (layout == null || !layout.equals(vertBuffer.getVertexLayout())) { 
//            layout = vertBuffer.getVertexLayout(); 
//        }
//        
//        // for now, assuming all vertex data use floats: 4 bytes
//        int sizeInBytes = 0; 
//        for (int i = 0; i < layout.getVertexElementCount(); i++) { 
//            VertexElement elem = layout.getVertexElement(i); 
//            sizeInBytes += elem.getComponentCount() * 4; 
//        }
//        
//        sizeInBytes *= vertBuffer.getVertices().length; 
//        
//        boolean subBufferData = false; 
//        
//        // determine if update or upload buffer data 
//        if (dataBuffer == null || sizeInBytes > size) { 
//            dataBuffer = Buffers.newDirectByteBuffer(sizeInBytes); 
//            size = sizeInBytes; 
//            subBufferData = true; 
//        }
//        
//        // update buffer
//        Vertex[] verts = vertBuffer.getVertices(); 
//        
//        dataBuffer.rewind(); 
//        
//        float[] tmp = new float[4]; 
//        for (int i = 0; i < verts.length; i++) { 
//            for (int j = 0; j < layout.getVertexElementCount(); j++) { 
//                VertexElement elem = layout.getVertexElement(j); 
//                verts[i].getFloats(elem, tmp);
//                for (int k = 0; k < elem.getComponentCount(); k++) { 
//                    dataBuffer.putFloat(tmp[k]); 
//                }
//            }
//        }
//        dataBuffer.flip(); 
//        
//        // upload new data
//        graphics.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
//        
//        if (subBufferData) { 
//            graphics.gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, sizeInBytes, dataBuffer);
//        }
//        else { 
//            graphics.gl.glBufferData(GL.GL_ARRAY_BUFFER, sizeInBytes, dataBuffer, JoglGraphicsDevice.BUFFER_USAGE[vertBuffer.getUsage().ordinal()]);
//        }
//        
//        curSize = sizeInBytes; 
//    }
//    
//}
