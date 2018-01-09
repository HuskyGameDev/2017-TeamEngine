package oasis.graphics.ogl;

import oasis.graphics.Attribute;
import oasis.graphics.Geometry;
import oasis.graphics.IndexBuffer;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;

// TODO implement VAO
public class OglGeometry extends Geometry {

    private Ogl ogl; 
    
    public OglGeometry(Ogl ogl, IndexBuffer ib, VertexBuffer[] vbs) {
        this.ogl = ogl; 
        setIndexBuffer(ib); 
        setVertexBuffers(vbs); 
    }
    
    public void setBuffers() {
        Attribute[] attribs = Attribute.values(); 
        int attribCount = attribs.length; 
        
        int[] attribIds = new int[attribCount]; 
        int[] attribOffsets = new int[attribCount]; 
        
        findVertexAttribs(attribIds, attribOffsets); 
        
        for (int i = 0; i < attribCount; i++) {
            if (attribIds[i] == -1) {
                // attribute not used 
                ogl.glDisableVertexAttribArray(i); 
            }
            else {
                // setup attribute 
                ogl.glEnableVertexAttribArray(i); 
                
                OglVertexBuffer vb = (OglVertexBuffer) getVertexBuffer(attribIds[i]); 
                
//                log.debug("Bind VertexBuffer: " + vb.getId()); 
                ogl.glBindBuffer(Ogl.GL_ARRAY_BUFFER, vb.getId());
                
                VertexFormat format = vb.getFormat(); 
//                log.debug("Attrib pointer: " + i + ", " + attribs[i].getFloatCount() + ", " + format.getFloatsPerElement() * 4 + ", " + attribOffsets[i] * 4);
                ogl.glVertexAttribPointer(
                        i, 
                        attribs[i].getFloatCount(), 
                        Ogl.GL_FLOAT, 
                        false, 
                        format.getFloatsPerElement() * 4, 
                        attribOffsets[i] * 4);
            }
        }
        
        if (hasIndexBuffer()) {
            OglIndexBuffer ib = (OglIndexBuffer) getIndexBuffer(); 
            
//            log.debug("Bind IndexBuffer: " + ib.getId()); 
            ogl.glBindBuffer(Ogl.GL_ELEMENT_ARRAY_BUFFER, ib.getId()); 
        }
    }
    
    private void findVertexAttribs(int[] attribIds, int[] attribOffsets) {
        for (int i = 0; i < attribIds.length; i++) {
            attribIds[i] = -1; 
        }
        
        for (int i = 0; i < getVertexBufferCount(); i++) {
            VertexBuffer vb = getVertexBuffer(i); 
            VertexFormat format = vb.getFormat(); 
            
            int floats = 0; 
            
            for (int j = 0; j < format.getAttributeCount(); j++) {
                Attribute attrib = format.getAttribute(j); 
                
                int ordinal = attrib.ordinal(); 
                
                if (attribIds[ordinal] == -1) {
                    // attribute has not been assigned yet 
                    attribIds[ordinal] = i; 
                    attribOffsets[ordinal] = floats; 
                }
                
                floats += attrib.getFloatCount(); 
            }
        }
    }
    
    @Override
    public void release() {
        // TODO Auto-generated method stub
        
    }

}
