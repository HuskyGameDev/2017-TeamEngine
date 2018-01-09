package oasis.graphics;

import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector3;

public class RenderData {

    private Mesh mesh; 
    private int submesh; 
    private Material material; 
    private Matrix4 modelMat; 
    private Matrix3 normalMat; 
    private Vector3 position; 
    
    public RenderData(Mesh mesh, int submesh, Material mat, Matrix4 modelMat, Matrix3 normalMat) {
        this.mesh = mesh; 
        this.submesh = submesh; 
        this.material = mat; 
        this.modelMat = modelMat; 
        this.normalMat = normalMat; 
        this.position = new Vector3(modelMat.m03, modelMat.m13, modelMat.m23); 
    }
    
    public Mesh getMesh() {
        return mesh; 
    }
    
    public int getSubmesh() {
        return submesh; 
    }
    
    public Material getMaterial() {
        return material; 
    }
    
    public Matrix4 getModelMatrix() {
        return modelMat; 
    }
    
    public Matrix3 getNormalMatrix() {
        return normalMat; 
    }
    
    public Vector3 getPosition() {
        return position; 
    }
    
}
