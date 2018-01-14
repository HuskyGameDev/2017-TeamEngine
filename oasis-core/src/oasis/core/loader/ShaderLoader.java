package oasis.core.loader;

import oasis.core.ResourceLoader;
import oasis.file.GlslParser;
import oasis.graphics.Shader;

public class ShaderLoader implements ResourceLoader<Shader> {

    @Override
    public Shader load(String filename) {
        String vs = GlslParser.getVertexSource(filename); 
        String fs = GlslParser.getFragmentSource(filename); 
        Shader shader = Shader.create(vs, fs); 
        
        return shader; 
    }

}
