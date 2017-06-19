//package oasis.old;
//
//import com.jogamp.opengl.GL;
//import com.jogamp.opengl.GL2;
//
//import oasis.core.EngineException;
//import oasis.core.GameLogger;
//import oasis.graphics.Attribute;
//import oasis.graphics.Shader;
//import oasis.graphics.jogl.JoglGraphicsDevice;
//import oasis.graphics.jogl.JoglInputLayout;
//
//public class JoglShaderOld {
//
//    private static final GameLogger log = new GameLogger(JoglShaderOld.class); 
//    
//    public JoglInputLayout inputLayout = null; 
//    public int id = 0; 
//    public Shader shader = null; 
//    
//    public void init(JoglGraphicsDevice graphics, Shader shader) { 
//        GL2 gl = graphics.gl; 
//        
//        int[] status = new int[1]; 
//        int[] length = new int[1]; 
//        byte[] text = new byte[512]; 
//        
//        int vert, frag; 
//        id = gl.glCreateProgram(); 
//        vert = gl.glCreateShader(GL2.GL_VERTEX_SHADER); 
//        frag = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER); 
//        
//        gl.glShaderSource(vert, 1, new String[] { shader.getVertexSource() }, new int[] { shader.getVertexSource().length() }, 0);
//        gl.glCompileShader(vert);
//        gl.glGetShaderiv(vert, GL2.GL_COMPILE_STATUS, status, 0);
//        if (status[0] != GL.GL_TRUE) {
//            gl.glGetShaderInfoLog(vert, 512, length, 0, text, 0);
//            log.warning("Vertex Shader Compile Error: " + new String(text).trim());
//        }
//        
//        gl.glShaderSource(frag, 1, new String[] { shader.getFragmentSource() }, new int[] { shader.getFragmentSource().length() }, 0);
//        gl.glCompileShader(frag);
//        gl.glGetShaderiv(frag, GL2.GL_COMPILE_STATUS, status, 0);
//        if (status[0] != GL.GL_TRUE) {
//            gl.glGetShaderInfoLog(frag, 512, length, 0, text, 0);
//            log.warning("Fragment Shader Compile Error: " + new String(text).trim());
//        }
//        
//        gl.glAttachShader(id, vert);
//        gl.glAttachShader(id, frag);
//        
//        // TODO bind attributes here
//        
//        gl.glLinkProgram(id);
//        gl.glGetProgramiv(id, GL2.GL_LINK_STATUS, status, 0);
//        if (status[0] != GL.GL_TRUE) {
//            gl.glGetProgramInfoLog(id, 512, length, 0, text, 0);
//            log.warning("Link Error: " + new String(text).trim());
//        }
//        
//        pollAttributes(gl); 
//        
//        gl.glDeleteShader(vert);
//        gl.glDeleteShader(frag);
//    }
//    
//    private void pollAttributes(GL2 gl) { 
//        inputLayout = new JoglInputLayout(); 
//        
//        int[] tmp = new int[3]; 
//        gl.glGetProgramiv(id, GL2.GL_ACTIVE_ATTRIBUTES, tmp, 0);
//        
//        int numAttributes = tmp[0]; 
//        
//        System.out.println("Num Attributes: " + numAttributes);
//        
//        for (int i = 0; i < numAttributes; i++) { 
//            byte[] bytes = new byte[512]; 
//            gl.glGetActiveAttrib(id, i, bytes.length, tmp, 0, tmp, 1, tmp, 2, bytes, 0);
//            
//            String name = new String(bytes).trim(); 
//            
//            int digit = 0; 
//            String attr = ""; 
//            
//            if (Character.isDigit(name.charAt(name.length() - 2))) { 
//                digit = Integer.parseInt(name.substring(name.length() - 2)); 
//                attr = name.substring(0, name.length() - 2); 
//            }
//            else { 
//                digit = Integer.parseInt(name.substring(name.length() - 1)); 
//                attr = name.substring(0, name.length() - 1); 
//            }
//            
//            Attribute attribute = JoglGraphicsDevice.ATTRIBUTE_MAP.get(attr); 
//            
//            if (attribute == null) { 
//                throw new EngineException("Unknown attribute type: " + attr); 
//            }
//            
//            inputLayout.attributes.add(attribute); 
//            inputLayout.indices.add(digit); 
//            inputLayout.location.add(gl.glGetAttribLocation(id, name)); 
//        }
//    }
//    
//    public void update(JoglGraphicsDevice joglGraphicsDevice) {
//        // TODO uniform update
//        
//    }
//    
//    public void bind(JoglGraphicsDevice graphics) { 
//        graphics.gl.glUseProgram(id);
//    }
//    
//}
