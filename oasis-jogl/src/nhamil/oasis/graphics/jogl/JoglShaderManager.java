package nhamil.oasis.graphics.jogl;

import java.util.ArrayList;
import java.util.List;

import nhamil.oasis.core.Oasis;
import nhamil.oasis.graphics.Shader;
import nhamil.oasis.graphics.ShaderManager;

public class JoglShaderManager implements ShaderManager {

    private List<String> paths;
    
    public JoglShaderManager() {
        paths = new ArrayList<>();
        paths.add(Oasis.DEFAULT_SHADER_FOLDER);
    }
    
    @Override
    public Shader getFromFile(String vertexFile, String fragmentFile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Shader getFromSource(String vertexSrc, String fragmentSrc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Shader getDefaultShader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addSearchPath(String path) {
        if (path != null) paths.add(path);
    }
    
}
