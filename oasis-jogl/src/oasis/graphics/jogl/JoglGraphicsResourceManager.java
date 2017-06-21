package oasis.graphics.jogl;

import oasis.graphics.GraphicsResourceManager;
import oasis.graphics.internal.NativeMesh;
import oasis.graphics.internal.NativeShader;

public class JoglGraphicsResourceManager implements GraphicsResourceManager {

    private JoglGraphicsDevice graphics; 
    
    public JoglGraphicsResourceManager(JoglGraphicsDevice graphics) {
        this.graphics = graphics; 
    }

    @Override
    public NativeShader createNativeShaderFromSource(String vertex, String fragment) {
        return new JoglShader(graphics, vertex, fragment);
    }
    
//    @Override
//    public NativeTexture2D createNativeTexture2D(int width, int height, Format format) {
//        return new JoglTexture2D(graphics, width, height, format);
//    }

    @Override
    public NativeMesh createNativeMesh() {
        return new JoglMesh(graphics);
    }

}
