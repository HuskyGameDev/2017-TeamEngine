package nhamil.oasis.graphics;

public interface ShaderManager {

    void addSearchPath(String path);
    
    Shader getFromFile(String vertexFile, String fragmentFile);
    
    Shader getFromSource(String vertexSrc, String fragmentSrc);
    
    Shader getDefaultShader();
    
}
