package oasis.graphics.ogl;

import java.util.ArrayList;
import java.util.List;

import oasis.graphics.Attribute;
import oasis.graphics.Geometry;
import oasis.graphics.IndexBuffer;
import oasis.graphics.VertexBuffer;
import oasis.graphics.VertexFormat;
import oasis.graphics.internal.InternalGeometry;

// TODO implement VAO
public class OglGeometry implements InternalGeometry {

    private Ogl ogl;
    private OglGraphics graphics; 
    
    private Geometry geom; 
    private IndexBuffer ib; 
    private List<VertexBuffer> vbs = new ArrayList<>(); 

    public OglGeometry(Ogl ogl, OglGraphics graphics, Geometry geom) {
        this.ogl = ogl; 
        this.geom = geom; 
        this.graphics = graphics; 
    }

    public void bindBuffers() {
        Attribute[] attribs = Attribute.values();
        int attribCount = attribs.length;

        int[] attribIds = new int[attribCount];
        int[] attribOffsets = new int[attribCount];

        findVertexAttribs(attribIds, attribOffsets);

        for (int i = 0; i < attribCount; i++) {
            if (attribIds[i] == -1) {
                // attribute not used
                ogl.glDisableVertexAttribArray(i);
            } else {
                // setup attribute
                ogl.glEnableVertexAttribArray(i);

                VertexBuffer vb = vbs.get(attribIds[i]); 

                // log.debug("Bind VertexBuffer: " + vb.getId());
                ogl.glBindBuffer(Ogl.GL_ARRAY_BUFFER, graphics.getVertexBufferId(vb));

                VertexFormat format = vb.getFormat();
                // log.debug("Attrib pointer: " + i + ", " +
                // attribs[i].getFloatCount() + ", " +
                // format.getFloatsPerElement() * 4 + ", " + attribOffsets[i] *
                // 4);
                ogl.glVertexAttribPointer(i, attribs[i].getFloatCount(), Ogl.GL_FLOAT, false, format.getFloatsPerElement() * 4, attribOffsets[i] * 4);
            }
        }

        if (ib != null) {
            // log.debug("Bind IndexBuffer: " + ib.getId());
            ogl.glBindBuffer(Ogl.GL_ELEMENT_ARRAY_BUFFER, graphics.getIndexBufferId(ib));
        }
    }

    private void findVertexAttribs(int[] attribIds, int[] attribOffsets) {
        for (int i = 0; i < attribIds.length; i++) {
            attribIds[i] = -1;
        }

        for (int i = 0; i < vbs.size(); i++) {
            VertexBuffer vb = vbs.get(i);
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

    @Override
    public void setBuffers() {
        this.ib = geom.getIndexBuffer(); 
        vbs.clear(); 
        for (int i = 0; i < geom.getVertexBufferCount(); i++) {
            vbs.add(geom.getVertexBuffer(i)); 
        }
    }

}
