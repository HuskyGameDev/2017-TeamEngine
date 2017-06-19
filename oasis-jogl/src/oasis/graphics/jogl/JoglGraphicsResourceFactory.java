package oasis.graphics.jogl;

import oasis.graphics.GraphicsResourceFactory;
import oasis.graphics.Shader;
import oasis.graphics.vertex.VertexArray;
import oasis.graphics.vertex.VertexBuffer;
import oasis.graphics.vertex.VertexLayout;

public class JoglGraphicsResourceFactory implements GraphicsResourceFactory {

    private JoglGraphicsDevice graphics; 
    
    public JoglGraphicsResourceFactory(JoglGraphicsDevice graphics) {
        this.graphics = graphics; 
    }

    @Override
    public Shader createShaderFromSource(String vertex, String fragment) {
        return new JoglShader(graphics, vertex, fragment);
    }

    @Override
    public VertexBuffer createVertexBuffer(VertexLayout layout) {
        return new JoglVertexBuffer(graphics, layout); 
    }

    @Override
    public VertexArray createVertexArray(VertexBuffer[] vertexBuffers) {
        return new JoglVertexArray(graphics, vertexBuffers); 
    }

}
